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
import org.opencloudengine.garuda.util.ExceptionUtils;
import org.opencloudengine.garuda.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            /**
             * 시그널 데이터가 있을 경우 처리(프로세스가 사용자에 의해 중단된 경우)
             */
            if (ClientStatus.STOPPING.equals(clientJob.getSignal())) {

                updateClientJobAs(ClientStatus.STOPPING);
            } else if (ClientStatus.KILLED.equals(clientJob.getSignal())) {

                updateClientJobAs(ClientStatus.KILLED);
            } else if (ClientStatus.KILL_FAIL.equals(clientJob.getSignal())) {

                updateClientJobAs(ClientStatus.KILL_FAIL);
            }
            /**
             * 시그널이 없을 경우 처리(프로세스가 사용자에 의해 중단되지 않은 경우)
             */
            else if ("0".equals(clientJob.getExitCode())) {

                updateClientJobAs(ClientStatus.FINISHED);
            } else {

                updateClientJobAs(ClientStatus.FAILED);
            }
        } catch (Exception ex) {
            getData();
            logger.warn("You can not run a client job.", ex);
            clientJob.setException(ExceptionUtils.getFullStackTrace(ex));
            if (ex.getCause() != null) clientJob.setCause(ex.getCause().getMessage());
            try {
                updateClientJobAs(ClientStatus.FAILED);
            } catch (Exception e) {
                logger.warn("You can not save clientJob information.", e);
            }
        }
    }

    private void getData() {

        //종료시간 등록
        Date endDate = new Date();
        clientJob.setEndDate(endDate.getTime());

        //듀레이션 등록
        if (clientJob.getStartDate() != null) {
            long duration = endDate.getTime() - clientJob.getStartDate();
            clientJob.setDuration(duration);
        }

        //로그디렉토리로부터 로그 및 종료코드, 실행 스크립트를 등록한다.
        clientJob = clientJobService.getDataFromFileSystem(clientJob);
    }

    private void preRunClientJob() {

        //TODO 서비스 훅이 있다면 잡이 시작되었음을 통지한다.
    }

    public abstract void runTask() throws Exception;

    public void updateClientJobAs(String status) {
        clientJob.setStatus(status);
        clientJob = clientJobService.updateById(clientJob);

        //TODO 서비스 훅이 있다면 통지한다.
    }
}
