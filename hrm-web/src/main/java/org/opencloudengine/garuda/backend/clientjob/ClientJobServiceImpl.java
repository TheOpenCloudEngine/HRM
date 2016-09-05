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

import javax.servlet.http.HttpServletResponse;
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
        clientJob.setClientRequest(clientRequest);

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

    private String getClientRequestType(BasicClientRequest clientRequest, String clientJobId) {
        if (clientRequest instanceof HiveRequest) {
            return "hive";
        }
        if (clientRequest instanceof MrRequest) {
            return "mr";
        }
        if (clientRequest instanceof PigRequest) {
            return "pig";
        }
        if (clientRequest instanceof SparkRequest) {
            return "spark";
        }
        logger.warn("Failed to parse jobType : {}", clientJobId);
        throw new ServiceException("Failed to parse jobType : " + clientJobId);
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

//    @Override
//    public ClientJob insertCash(ClientJob clientJob) {
//        return clientJobRepository.insertCash(clientJob);
//    }
//
//    @Override
//    public ClientJob selectByClientJobIdCash(String clientJobId) {
//        return clientJobRepository.selectByClientJobIdCash(clientJobId);
//    }
//
//    @Override
//    public Map<String, ClientJob> selectAllCash() {
//        return clientJobRepository.selectAllCash();
//    }
//
//    @Override
//    public ClientJob updateByClientJobIdCash(ClientJob clientJob) {
//        return clientJobRepository.updateByClientJobIdCash(clientJob);
//    }
//
//    @Override
//    public void deleteByClientJobIdCash(String clientJobId) {
//        clientJobRepository.deleteByClientJobIdCash(clientJobId);
//    }

    @Override
    public ClientJob insert(ClientJob clientJob) {
        return clientJobRepository.insert(clientJob);
    }

    @Override
    public List<ClientJob> selectAll() {
        return clientJobRepository.selectAll();
    }

    @Override
    public List<ClientJob> select(int limit, Long skip) {
        return clientJobRepository.select(limit, skip);
    }

    @Override
    public List<ClientJob> selectRunning() {
        return clientJobRepository.selectRunning();
    }

    @Override
    public ClientJob selectById(String id) {
        return clientJobRepository.selectById(id);
    }

    @Override
    public ClientJob selectByClientJobId(String clientJobId) {
        return clientJobRepository.selectByClientJobId(clientJobId);
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
}
