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
package org.opencloudengine.garuda.backend.scheduler;

import org.opencloudengine.garuda.backend.task.HiveTask;
import org.opencloudengine.garuda.model.clientJob.ClientJob;
import org.opencloudengine.garuda.model.request.HiveRequest;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DefaultClientJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 클라이언트 잡 실행시 필요한 정보를 가져온다.
        JobDataMap mergedJobDataMap = jobExecutionContext.getMergedJobDataMap();

        ClientJob clientJob = (ClientJob) mergedJobDataMap.get(JobVariable.CLIENT_JOB);
//        String executeFrom = clientJob.getExecuteFrom();
//
//        String jobType = clientJob.getClientJobType();

        //TODO 클라이언트 잡을 상속받아 돌아가는 클래스들을 생성할것.
        if (clientJob.getClientRequest() instanceof HiveRequest) {
            new HiveTask().executeClientJob(clientJob);
        }
    }
}
