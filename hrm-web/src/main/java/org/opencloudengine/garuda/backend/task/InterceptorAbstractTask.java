/**
 * Copyright (C) 2011 Flamingo Project (http://www.cloudine.io).
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
package org.opencloudengine.garuda.backend.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.opencloudengine.garuda.model.clientJob.ClientStatus;
import org.opencloudengine.garuda.util.DateUtils;
import org.opencloudengine.garuda.util.ExceptionUtils;
import org.opencloudengine.garuda.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public abstract class InterceptorAbstractTask extends AbstractTask {

    /**
     * SLF4J Logging
     */
    private Logger logger = LoggerFactory.getLogger(InterceptorAbstractTask.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doClientExecute() {
        try {
            preRunClientJob();
            runTask();
            getData();
            if ("0".equals(clientJob.getExitCode())) {
                updateClientJobAsFinished();
            } else {
                updateClientJobAsFailed();
            }
        } catch (Exception ex) {
            getData();
            logger.warn("You can not run a client job.", ex);
            clientJob.setException(ExceptionUtils.getFullStackTrace(ex));
            if (ex.getCause() != null) clientJob.setCause(ex.getCause().getMessage());
            try {
                updateClientJobAsFailed();
            } catch (Exception e) {
                logger.warn("You can not save clientJob information.", e);
            }
        }
    }

    private void getData(){

        //종료시간 등록
        Date endDate = new Date();
        clientJob.setEndDate(endDate.getTime());

        //듀레이션 등록
        if (clientJob.getStartDate() != null) {
            long duration = endDate.getTime() - clientJob.getStartDate();
            clientJob.setDuration(duration);
        }

        //로그 및 종료코드, 실행 스크립트 등록
        if (!StringUtils.isEmpty(workingDir)) {
            File pidFile = new File(workingDir + "/PID");
            File codeFile = new File(workingDir + "/CODE");
            File logFile = new File(workingDir + "/task.log");
            File errFile = new File(workingDir + "/err.log");

            File sciptFile = new File(workingDir + "/script.sh");
            File cmdFile = new File(workingDir + "/command.sh");

            try{
                if (pidFile.exists()) {
                    clientJob.setPid(FileCopyUtils.copyToString(new FileReader(pidFile)));
                }
                if (codeFile.exists()) {
                    clientJob.setExitCode(FileCopyUtils.copyToString(new FileReader(codeFile)));
                }
                if (logFile.exists()) {
                    clientJob.setStdout(FileCopyUtils.copyToString(new FileReader(logFile)));
                }
                if (errFile.exists()) {
                    clientJob.setStderr(FileCopyUtils.copyToString(new FileReader(errFile)));
                }
                if (sciptFile.exists()) {
                    clientJob.setExecuteScript(FileCopyUtils.copyToString(new FileReader(sciptFile)));
                }
                if (cmdFile.exists()) {
                    clientJob.setExecuteCli(FileCopyUtils.copyToString(new FileReader(cmdFile)));
                }
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    private void preRunClientJob() {
        //시작 시간 등록
        Date currentDate = new Date();
        clientJob.setStartDate(currentDate.getTime());

        //워킹 디렉토리 등록
        clientJob.setWorkingDir(clientJobBasePath(config.getProperty("application.home"), clientJobId, currentDate));
        this.workingDir = clientJob.getWorkingDir();

        //스테이터스 RUNNING 등록
        clientJob.setStatus(ClientStatus.RUNNING);

        //저장소 인서트
        clientJob = clientJobService.insert(clientJob);

        //TODO 서비스 훅이 있다면 잡이 시작되었음을 통지한다.
    }

    public abstract void runTask() throws Exception;

    public static String clientJobBasePath(String logDir, String jobId, Date current) {
        return logDir + "/" + DateUtils.parseDate(current, "yyyy") + "/" + DateUtils.parseDate(current, "MM") + "/" + DateUtils.parseDate(current, "dd") + "/clientJob/" + jobId;
    }

    private void updateClientJobAsFinished() {
        clientJob.setStatus(ClientStatus.FINISHED);
        clientJob = clientJobService.updateById(clientJob);

        //TODO 서비스 훅이 있다면 잡이 성공하였음을 통지한다.
    }

    public void updateClientJobAsFailed() {
        clientJob.setStatus(ClientStatus.FAILED);
        clientJob = clientJobService.updateById(clientJob);

        //TODO 서비스 훅이 있다면 잡이 실패하였음을 통지한다.
    }
}
