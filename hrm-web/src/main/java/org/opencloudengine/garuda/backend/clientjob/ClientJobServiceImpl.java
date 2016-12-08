/**
 * Copyright (C) 2011 Flamingo Project (http://www.opencloudengine.org).
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.opencloudengine.garuda.backend.clientjob;

import org.opencloudengine.garuda.backend.scheduler.JobScheduler;
import org.opencloudengine.garuda.backend.scheduler.JobVariable;
import org.opencloudengine.garuda.common.exception.ServiceException;
import org.opencloudengine.garuda.model.clientJob.ClientJob;
import org.opencloudengine.garuda.model.clientJob.ClientStatus;
import org.opencloudengine.garuda.model.request.*;
import org.opencloudengine.garuda.util.DateUtils;
import org.opencloudengine.garuda.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Seungpil, Park
 * @since 0.1
 */
@Service
public class ClientJobServiceImpl implements ClientJobService {

    /**
     * SLF4J Application Logging
     */
    private Logger logger = LoggerFactory.getLogger(ClientJobServiceImpl.class);

    @Autowired
    private ClientJobRepository clientJobRepository;

    @Autowired
    private JobScheduler jobScheduler;


    @Autowired
    @Qualifier("config")
    private Properties config;

    @Override
    public ClientJob run(BasicClientRequest clientRequest, String executeFrom) throws Exception {
        ClientJob clientJob = new ClientJob();

        //잡아이디 등록
        String clientJobId = UUID.randomUUID().toString();
        if (!StringUtils.isEmpty(clientRequest.getClientJobId())) {
            clientJobId = clientRequest.getClientJobId();
        }
        clientJob.setClientJobId(clientJobId);

        //잡타입 등록
        String clientJobType = this.getClientRequestType(clientRequest, clientJobId);
        clientJob.setClientJobType(clientJobType);

        //잡이름 등록
        String clientJobName = "Default " + clientJob.getClientJobType() + " job";
        if (!StringUtils.isEmpty(clientRequest.getClientJobName())) {
            clientJobName = clientRequest.getClientJobName();
        }
        clientJob.setClientJobName(clientJobName);

        //실행 모드 등록(rest or console)
        clientJob.setExecuteFrom(executeFrom);

        //클라이언트 리퀘스트 등록
        this.setRequestTypeToClientJob(clientRequest, clientJob);

        //시작 시간 등록
        Date currentDate = new Date();
        clientJob.setStartDate(currentDate.getTime());

        //워킹 디렉토리 등록
        clientJob.setWorkingDir(clientJobBasePath(config.getProperty("application.home"), clientJobId, currentDate));

        //스테이터스 RUNNING 등록
        clientJob.setStatus(ClientStatus.RUNNING);

        //저장소 인서트
        clientJob = this.insert(clientJob);

        //클라이언트 잡 시작
        Map params = new HashMap();
        params.put(JobVariable.CLIENT_JOB, clientJob);
        params.put(JobVariable.JOB_ID, clientJobId);
        params.put(JobVariable.JOB_GROUP, clientJobType);
        params.put(JobVariable.JOB_TYPE, executeFrom);
        params.put(JobVariable.JOB_NAME, clientJobName);

        jobScheduler.startJobImmediatly(clientJobId, clientJobType, params);
        return clientJob;
    }

    /**
     * 종료명령은 작업중인 디렉토리에 시그널 파일을 남김으로써,
     * 쿼츠잡이 타스크 수행 도중 시그널 파일로 인해 사용자에 의해 인터럽트 되었다는 것을 알 수 있다.
     * @param clientJobId
     * @return
     * @throws Exception
     */
    @Override
    public ClientJob kill(String clientJobId) throws Exception {
        ClientJob clientJob = this.selectByClientJobId(clientJobId);
        if (!ClientStatus.RUNNING.equals(clientJob.getStatus())) {
            return clientJob;
        } else {
            /**
             * STOPPING 시그널을 남기고 데이터베이스에 업데이트한다.
             */
            String signal = ClientStatus.STOPPING;
            FileCopyUtils.copy(signal.getBytes(), new File(clientJob.getWorkingDir() + "/SIGNAL"));

            clientJob.setStatus(ClientStatus.STOPPING);
            this.updateById(clientJob);
            return clientJob;
        }
    }

    @Override
    public ClientJob getDataFromFileSystem(ClientJob clientJob) {

        String workingDir = clientJob.getWorkingDir();

        //로그 및 종료코드, 실행 스크립트 등록
        if (!StringUtils.isEmpty(workingDir)) {
            File pidFile = new File(workingDir + "/PID");
            File codeFile = new File(workingDir + "/CODE");
            File logFile = new File(workingDir + "/task.log");

            File sciptFile = new File(workingDir + "/script.sh");
            File cmdFile = new File(workingDir + "/command.sh");

            File signalFile = new File(workingDir + "/SIGNAL");
            File killLogFile = new File(workingDir + "/kill.log");

            try {
                if (pidFile.exists()) {
                    clientJob.setPid(FileCopyUtils.copyToString(new FileReader(pidFile)));
                }
                if (codeFile.exists()) {
                    clientJob.setExitCode(FileCopyUtils.copyToString(new FileReader(codeFile)));
                }
                if (logFile.exists()) {
                    clientJob.setStdout(FileCopyUtils.copyToString(new FileReader(logFile)));
                }
                if (sciptFile.exists()) {
                    clientJob.setExecuteScript(FileCopyUtils.copyToString(new FileReader(sciptFile)));
                }
                if (cmdFile.exists()) {
                    clientJob.setExecuteCli(FileCopyUtils.copyToString(new FileReader(cmdFile)));
                }
                if (signalFile.exists()) {
                    clientJob.setSignal(FileCopyUtils.copyToString(new FileReader(signalFile)));
                }
                if (killLogFile.exists()) {
                    clientJob.setKillLog(FileCopyUtils.copyToString(new FileReader(killLogFile)));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            //얀 applicationId 와 맵리듀스 아이디를 등록한다.
            List<String> applicationIds = new ArrayList<>();
            List<String> mapreduceIds = new ArrayList<>();
            File working = new File(workingDir);
            if (working.exists()) {
                File[] files = working.listFiles();
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    if (file.getName().startsWith("app.")) {
                        String appId = file.getName().replace("app.", "");
                        applicationIds.add(appId);
                    }
                    if (file.getName().startsWith("hadoop.")) {
                        String mrId = file.getName().replace("hadoop.", "");
                        mapreduceIds.add(mrId);
                    }
                }
            }
            clientJob.setApplicationIds(applicationIds);
            clientJob.setMapreduceIds(mapreduceIds);
        }
        return clientJob;
    }

    private String getClientRequestType(BasicClientRequest clientRequest, String clientJobId) {
        if (clientRequest instanceof HiveRequest) {
            return ClientStatus.JOB_TYPE_HIVE;
        }
        if (clientRequest instanceof MrRequest) {
            return ClientStatus.JOB_TYPE_MR;
        }
        if (clientRequest instanceof PigRequest) {
            return ClientStatus.JOB_TYPE_PIG;
        }
        if (clientRequest instanceof SparkRequest) {
            return ClientStatus.JOB_TYPE_SPARK;
        }
        if (clientRequest instanceof JavaRequest) {
            return ClientStatus.JOB_TYPE_JAVA;
        }
        if (clientRequest instanceof PythonRequest) {
            return ClientStatus.JOB_TYPE_PYTHON;
        }
        if (clientRequest instanceof ShellRequest) {
            return ClientStatus.JOB_TYPE_SHELL;
        }
        if (clientRequest instanceof HbaseShellRequest) {
            return ClientStatus.JOB_TYPE_HBASE_SHELL;
        }
        if (clientRequest instanceof HbaseClassRequest) {
            return ClientStatus.JOB_TYPE_HBASE_CLASS;
        }
        if (clientRequest instanceof PhoenixRequest) {
            return ClientStatus.JOB_TYPE_PHOENIX;
        }

        logger.warn("Failed to parse jobType : {}", clientJobId);
        throw new ServiceException("Failed to parse jobType : " + clientJobId);
    }

    private void setRequestTypeToClientJob(BasicClientRequest clientRequest, ClientJob clientJob) {
        if (clientRequest instanceof HiveRequest) {
            clientJob.setHiveRequest((HiveRequest) clientRequest);
        }
        if (clientRequest instanceof MrRequest) {
            clientJob.setMrRequest((MrRequest) clientRequest);
        }
        if (clientRequest instanceof PigRequest) {
            clientJob.setPigRequest((PigRequest) clientRequest);
        }
        if (clientRequest instanceof SparkRequest) {
            clientJob.setSparkRequest((SparkRequest) clientRequest);
        }
        if (clientRequest instanceof JavaRequest) {
            clientJob.setJavaRequest((JavaRequest) clientRequest);
        }
        if (clientRequest instanceof PythonRequest) {
            clientJob.setPythonRequest((PythonRequest) clientRequest);
        }
        if (clientRequest instanceof ShellRequest) {
            clientJob.setShellRequest((ShellRequest) clientRequest);
        }
        if (clientRequest instanceof HbaseShellRequest) {
            clientJob.setHbaseShellRequest((HbaseShellRequest) clientRequest);
        }
        if (clientRequest instanceof HbaseClassRequest) {
            clientJob.setHbaseClassRequest((HbaseClassRequest) clientRequest);
        }
        if (clientRequest instanceof PhoenixRequest) {
            clientJob.setPhoenixRequest((PhoenixRequest) clientRequest);
        }
    }

    private void convertHumanReadable(ClientJob clientJob) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Long startDate = clientJob.getStartDate();
        Long endDate = clientJob.getEndDate();
        Long duration = clientJob.getDuration();
        if (startDate != null) {
            clientJob.setHumanStartDate(sdf.format(new Date(startDate)));
        } else {
            clientJob.setHumanStartDate("");
        }
        if (endDate != null) {
            clientJob.setHumanEndDate(sdf.format(new Date(endDate)));
        } else {
            clientJob.setHumanEndDate("");
        }
        if (duration != null) {
            long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
            long mis = clientJob.getDuration() - (minutes * 60 * 1000);
            double seconds = mis / 1000.0;
            clientJob.setHumanDuration(minutes + " min, " + seconds + " sec");
        } else {
            clientJob.setHumanDuration("");
        }
    }

    public static String clientJobBasePath(String logDir, String jobId, Date current) {
        return logDir + "/" + DateUtils.parseDate(current, "yyyy") + "/" + DateUtils.parseDate(current, "MM") + "/" + DateUtils.parseDate(current, "dd") + "/clientJob/" + jobId;
    }

    @Override
    public ClientJob insert(ClientJob clientJob) {
        return clientJobRepository.insert(clientJob);
    }

    @Override
    public List<ClientJob> selectAll() {
        return this.setFileBaseTaskData(clientJobRepository.selectAll());
    }

    @Override
    public List<ClientJob> select(int limit, Long skip) {
        return this.setFileBaseTaskData(clientJobRepository.select(limit, skip));
    }

    @Override
    public List<ClientJob> selectByClientJobTypeAndExecuteFrom(int limit, Long skip, String clientJobType, String executeFrom) {
        return this.setFileBaseTaskData(clientJobRepository.selectByClientJobTypeAndExecuteFrom(limit, skip, clientJobType, executeFrom));
    }

    @Override
    public List<ClientJob> selectRunning() {
        return this.setFileBaseTaskData(clientJobRepository.selectRunning());
    }

    @Override
    public List<ClientJob> selectStopping() {
        return setFileBaseTaskData(clientJobRepository.selectStopping());
    }

    @Override
    public ClientJob selectById(String id) {
        return this.setFileBaseTaskData(clientJobRepository.selectById(id));
    }

    @Override
    public ClientJob selectByClientJobId(String clientJobId) {
        return this.setFileBaseTaskData(clientJobRepository.selectByClientJobId(clientJobId));
    }

    @Override
    public Long count() {
        return clientJobRepository.count();
    }

    @Override
    public ClientJob updateById(ClientJob clientJob) {
        return clientJobRepository.updateById(clientJob);
    }

    @Override
    public void deleteById(String id) {
        clientJobRepository.deleteById(id);
    }

    @Override
    public void bulk(List<ClientJob> clientJobList) {
        clientJobRepository.bulk(clientJobList);
    }

    private List<ClientJob> setFileBaseTaskData(List<ClientJob> clientJobList) {
        for (int i = 0; i < clientJobList.size(); i++) {
            clientJobList.set(i, this.setFileBaseTaskData(clientJobList.get(i)));
        }
        return clientJobList;
    }

    private ClientJob setFileBaseTaskData(ClientJob clientJob) {
        if (clientJob != null) {
            clientJob = this.getDataFromFileSystem(clientJob);
            this.convertHumanReadable(clientJob);
        }
        return clientJob;
    }
}
