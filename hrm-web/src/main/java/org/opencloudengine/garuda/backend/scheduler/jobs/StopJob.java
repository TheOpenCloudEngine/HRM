package org.opencloudengine.garuda.backend.scheduler.jobs;

import net.sf.expectit.Expect;
import net.sf.expectit.ExpectBuilder;
import org.opencloudengine.garuda.backend.clientjob.ClientJobService;
import org.opencloudengine.garuda.common.exception.ServiceException;
import org.opencloudengine.garuda.model.clientJob.ClientJob;
import org.opencloudengine.garuda.model.clientJob.ClientStatus;
import org.opencloudengine.garuda.util.ApplicationContextRegistry;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.FileCopyUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

/**
 * ClientJob 의 Stopping Job 을 확인해서 종료시키는 작업.
 *
 * @author Seungpil, Park
 * @since 2.0
 */
public class StopJob extends QuartzJobBean {
    /**
     * SLF4J Logging
     */
    private Logger logger = LoggerFactory.getLogger(StopJob.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            logger.debug("Now check the stopping jobs of HRM.");
            ApplicationContext applicationContext = ApplicationContextRegistry.getApplicationContext();
            ClientJobService clientJobService = applicationContext.getBean(ClientJobService.class);
            List<ClientJob> clientJobs = clientJobService.selectStopping();

            for (ClientJob clientJob : clientJobs) {
                String killLog = "";
                try {
                    /**
                     * 킬 시그널을 남겨 AbstractTask 에서 타스크 실행 후 실패처리를 하지 않도록 한다.
                     */
                    String signal = ClientStatus.KILLED;
                    FileCopyUtils.copy(signal.getBytes(), new File(clientJob.getWorkingDir() + "/SIGNAL"));

                    String pid = clientJob.getPid();
                    String pidKillCmd = "kill -9 " + pid;
                    killLog += this.runProcess(pidKillCmd, "hrm");

                    List<String> applicationIds = clientJob.getApplicationIds();
                    for (String applicationId : applicationIds) {
                        String yarnKillCmd = "yarn application -kill " + applicationId;
                        killLog += this.runProcess(yarnKillCmd, "yarn");
                    }

                    List<String> mapreduceIds = clientJob.getMapreduceIds();
                    for (String mapreduceId : mapreduceIds) {
                        String jobKillCmd = "hadoop job -kill " + mapreduceId;
                        killLog += this.runProcess(jobKillCmd, "mr");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    clientJob.setStatus(ClientStatus.KILLED);
                    clientJob.setKilllog(killLog);
                    clientJobService.updateById(clientJob);
                }
            }

        } catch (Exception ex) {
            throw new ServiceException("Unable to run scheduled jobs", ex);
        }
    }

    private String runProcess(String cmd, String type) {
        Process pidProcess = null;
        String log = "";
        String line;
        switch (type) {
            case "hrm":
                log += "============================================================" + "\n";
                log += "====================Stopping HRM process====================" + "\n";
                log += "============================================================" + "\n";
                break;
            case "yarn":
                log += "============================================================" + "\n";
                log += "====================Stopping Yarn process===================" + "\n";
                log += "============================================================" + "\n";
                break;
            case "mr":
                log += "============================================================" + "\n";
                log += "====================Stopping Mr process=====================" + "\n";
                log += "============================================================" + "\n";
                break;
            default:
                log += "============================================================" + "\n";
                log += "====================Stopping process========================" + "\n";
                log += "============================================================" + "\n";
                break;
        }

        log += "\n Stop cmd execute : " + cmd + "\n";
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((line = bri.readLine()) != null) {
                log += line + "\n";
            }
            bri.close();
            while ((line = bre.readLine()) != null) {
                log += line + "\n";
            }
            bre.close();
            p.waitFor();
            log += "\n" + "Stop cmd result : Stop succeed \n\n";

        } catch (Exception ex) {
            ex.printStackTrace();
            log += "\n" + "Stop cmd result : Stop Failed \n\n";
        } finally {
            if (pidProcess != null) {
                pidProcess.destroy();
            }
            return log;
        }
    }
}
