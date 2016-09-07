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

import java.io.*;
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
                try{
                    String signal = "";
                    File killLogFile = new File(clientJob.getWorkingDir() + "/kill.log");
                    if (!killLogFile.exists()) {
                        killLogFile.createNewFile();
                    }
                    FileOutputStream out = new FileOutputStream(killLogFile);
                    try {
                        String pid = clientJob.getPid();
                        String pidKillCmd = "kill -9 " + pid;
                        this.runProcess(pidKillCmd, out, "hrm");

                        List<String> applicationIds = clientJob.getApplicationIds();
                        for (String applicationId : applicationIds) {
                            String yarnKillCmd = "yarn application -kill " + applicationId;
                            this.runProcess(yarnKillCmd, out, "yarn");
                        }

                        List<String> mapreduceIds = clientJob.getMapreduceIds();
                        for (String mapreduceId : mapreduceIds) {
                            String jobKillCmd = "hadoop job -kill " + mapreduceId;
                            this.runProcess(jobKillCmd, out, "mr");
                        }
                        signal = ClientStatus.KILLED;
                    } catch (Exception ex) {
                        signal = ClientStatus.KILL_FAIL;
                        ex.printStackTrace();
                    } finally {
                        if (out != null) {
                            out.close();
                        }
                        File signalFile = new File(clientJob.getWorkingDir() + "/SIGNAL");
                        if (signalFile.exists()) {
                            signalFile.delete();
                        }
                        FileCopyUtils.copy(signal.getBytes(), new File(clientJob.getWorkingDir() + "/SIGNAL"));
                        clientJob.setStatus(signal);
                        clientJob.setKillLog(FileCopyUtils.copyToString(new FileReader(killLogFile)));
                        clientJobService.updateById(clientJob);
                    }
                }catch (Exception ex){
                    clientJob.setStatus(ClientStatus.KILL_FAIL);
                    clientJobService.updateById(clientJob);
                }
            }

        } catch (Exception ex) {
            throw new ServiceException("Unable to run scheduled jobs", ex);
        }
    }

    private void runProcess(String cmd, OutputStream out, String type){
        Process pidProcess = null;
        String log = "";
        String closing = "";
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
            out.write(log.getBytes());

            Process p = Runtime.getRuntime().exec(cmd);
            InputStream logStream = p.getInputStream();
            InputStream errStream = p.getErrorStream();
            OutputStream outStream = p.getOutputStream();
            outStream.close();

            byte[] b = new byte[1024];
            int numBytes = 0;
            while ((numBytes = logStream.read(b)) > 0) {
                out.write(b, 0, numBytes);
            }
            logStream.close();

            byte[] c = new byte[1024];
            int errBytes = 0;
            while ((errBytes = errStream.read(b)) > 0) {
                out.write(c, 0, errBytes);
            }
            errStream.close();
            p.waitFor();

            closing += "\n" + "Stop cmd result : Stop succeed \n\n";

        } catch (Exception ex) {
            ex.printStackTrace();
            closing += "\n" + "Stop cmd result : Stop Failed \n\n";
        } finally {
            try{
                out.write(closing.getBytes());
            }catch (IOException ex){
                ex.printStackTrace();
            }
            if (pidProcess != null) {
                pidProcess.destroy();
            }
        }
    }
}
