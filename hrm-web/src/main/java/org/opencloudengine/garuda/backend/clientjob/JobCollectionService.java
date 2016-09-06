package org.opencloudengine.garuda.backend.clientjob;

import org.opencloudengine.garuda.model.clientJob.JobCollection;

import java.util.List;

public interface JobCollectionService {

    JobCollection insert(JobCollection jobCollection);

    List<JobCollection> selectAll();

    JobCollection selectById(String id);

    List<JobCollection> selectByJobType(String jobType);

    JobCollection updateById(JobCollection jobCollection);

    void deleteById(String id);
}
