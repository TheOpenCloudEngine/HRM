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
import org.opencloudengine.garuda.model.clientJob.JobCollection;
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
public class JobCollectionServiceImpl implements JobCollectionService {

    /**
     * SLF4J Application Logging
     */
    private Logger logger = LoggerFactory.getLogger(JobCollectionServiceImpl.class);

    @Autowired
    private JobCollectionRepository jobCollectionRepository;


    @Autowired
    @Qualifier("config")
    private Properties config;

    @Override
    public JobCollection insert(JobCollection jobCollection) {
        return jobCollectionRepository.insert(jobCollection);
    }

    @Override
    public List<JobCollection> selectAll() {
        return jobCollectionRepository.selectAll();
    }

    @Override
    public JobCollection selectById(String id) {
        return jobCollectionRepository.selectById(id);
    }

    @Override
    public JobCollection selectByJobNameAndJobType(String jobName, String jobType) {
        return jobCollectionRepository.selectByJobNameAndJobType(jobName, jobType);
    }

    @Override
    public List<JobCollection> selectByJobType(String jobType) {
        return jobCollectionRepository.selectByJobType(jobType);
    }

    @Override
    public JobCollection updateById(JobCollection jobCollection) {
        return jobCollectionRepository.updateById(jobCollection);
    }

    @Override
    public void deleteById(String id) {
        jobCollectionRepository.deleteById(id);
    }
}
