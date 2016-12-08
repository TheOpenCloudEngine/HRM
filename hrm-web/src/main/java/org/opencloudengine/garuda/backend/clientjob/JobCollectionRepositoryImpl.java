package org.opencloudengine.garuda.backend.clientjob;

import com.cloudant.client.api.model.Response;
import com.cloudant.client.api.views.Key;
import com.cloudant.client.api.views.ViewRequestBuilder;
import com.cloudant.client.api.views.ViewResponse;
import org.opencloudengine.garuda.couchdb.CouchServiceFactory;
import org.opencloudengine.garuda.model.clientJob.JobCollection;
import org.opencloudengine.garuda.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JobCollectionRepositoryImpl implements JobCollectionRepository {

    private String NAMESPACE = "jobCollection";

    @Autowired
    CouchServiceFactory serviceFactory;


    @Override
    public JobCollection insert(JobCollection jobCollection) {
        long time = new Date().getTime();
        jobCollection.setDocType(NAMESPACE);
        jobCollection.setRegDate(time);
        jobCollection.setUpdDate(time);

        Response response = serviceFactory.getDb().save(jobCollection);
        jobCollection.set_id(response.getId());
        jobCollection.set_rev(response.getRev());

        return jobCollection;
    }

    @Override
    public List<JobCollection> selectAll() {
        List<JobCollection> list = new ArrayList<>();
        try {
            ViewRequestBuilder builder = serviceFactory.getDb().getViewRequestBuilder(NAMESPACE, "select");
            List<ViewResponse.Row<Key.ComplexKey, JobCollection>> rows = builder.newRequest(Key.Type.COMPLEX, JobCollection.class).
                    build().getResponse().getRows();

            for (ViewResponse.Row<Key.ComplexKey, JobCollection> row : rows) {
                list.add(row.getValue());
            }
            return list;
        } catch (Exception ex) {
            return list;
        }
    }

    @Override
    public JobCollection selectByJobNameAndJobType(String jobName, String jobType) {
        try {
            ViewRequestBuilder builder = serviceFactory.getDb().getViewRequestBuilder(NAMESPACE, "selectByJobNameAndJobType");
            Key.ComplexKey complex = new Key().complex(jobName).add(jobType);
            return builder.newRequest(Key.Type.COMPLEX, JobCollection.class).
                    keys(complex).
                    build().getResponse().getRows().get(0).getValue();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<JobCollection> selectByJobType(String jobType) {
        List<JobCollection> list = new ArrayList<>();
        try {
            ViewRequestBuilder builder = serviceFactory.getDb().getViewRequestBuilder(NAMESPACE, "selectByJobType");
            Key.ComplexKey complex = new Key().complex(jobType);
            List<ViewResponse.Row<Key.ComplexKey, JobCollection>> rows = builder.newRequest(Key.Type.COMPLEX, JobCollection.class).
                    keys(complex).
                    build().getResponse().getRows();

            for (ViewResponse.Row<Key.ComplexKey, JobCollection> row : rows) {
                try {
                    list.add(row.getValue());
                } catch (Exception ex) {

                }
            }
            return list;
        } catch (Exception ex) {
            return list;
        }
    }

    @Override
    public JobCollection selectById(String id) {
        try {
            ViewRequestBuilder builder = serviceFactory.getDb().getViewRequestBuilder(NAMESPACE, "selectById");
            Key.ComplexKey complex = new Key().complex(id);
            return builder.newRequest(Key.Type.COMPLEX, JobCollection.class).
                    keys(complex).
                    build().getResponse().getRows().get(0).getValue();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public JobCollection updateById(JobCollection jobCollection) {
        JobCollection existCollection = this.selectById(jobCollection.get_id());

        existCollection = (JobCollection) JsonUtils.merge(existCollection, jobCollection);
        long time = new Date().getTime();
        existCollection.setUpdDate(time);

        Response update = serviceFactory.getDb().update(existCollection);
        existCollection.set_rev(update.getRev());

        return existCollection;
    }

    @Override
    public void deleteById(String id) {
        JobCollection jobCollection = this.selectById(id);
        serviceFactory.getDb().remove(jobCollection);
    }
}