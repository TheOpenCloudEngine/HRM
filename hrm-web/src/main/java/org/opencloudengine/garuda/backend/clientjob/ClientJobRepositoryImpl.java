package org.opencloudengine.garuda.backend.clientjob;

import com.cloudant.client.api.model.Response;
import com.cloudant.client.api.views.Key;
import com.cloudant.client.api.views.ViewRequestBuilder;
import com.cloudant.client.api.views.ViewResponse;
import org.opencloudengine.garuda.couchdb.CouchServiceFactory;
import org.opencloudengine.garuda.model.clientJob.ClientJob;
import org.opencloudengine.garuda.util.JsonUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ClientJobRepositoryImpl implements ClientJobRepository, InitializingBean {

    private String NAMESPACE = "clientJob";

    @Autowired
    CouchServiceFactory serviceFactory;

    Map<String, ClientJob> cash;

    @Override
    public void afterPropertiesSet() throws Exception {
        cash = new HashMap<>();
    }

//    @Override
//    public ClientJob insertCash(ClientJob clientJob) {
//        ClientJob insert = this.insert(clientJob);
//        return this.updateByClientJobIdCash(insert);
//    }
//
//    @Override
//    public ClientJob selectByClientJobIdCash(String clientJobId) {
//        return cash.get(clientJobId);
//    }
//
//    @Override
//    public Map<String, ClientJob> selectAllCash() {
//        return cash;
//    }
//
//    @Override
//    public ClientJob updateByClientJobIdCash(ClientJob clientJob) {
//        String clientJobId = clientJob.getClientJobId();
//        cash.put(clientJobId, clientJob);
//        return clientJob;
//    }
//
//    @Override
//    public void deleteByClientJobIdCash(String clientJobId) {
//        cash.remove(clientJobId);
//    }

    @Override
    public ClientJob insert(ClientJob clientJob) {
        long time = new Date().getTime();
        clientJob.setDocType(NAMESPACE);
        clientJob.setRegDate(time);
        clientJob.setUpdDate(time);

        Response response = serviceFactory.getDb().save(clientJob);
        clientJob.set_id(response.getId());
        clientJob.set_rev(response.getRev());

        return clientJob;
    }

    @Override
    public List<ClientJob> selectAll() {
        List<ClientJob> list = new ArrayList<>();
        try {
            ViewRequestBuilder builder = serviceFactory.getDb().getViewRequestBuilder(NAMESPACE, "select");
            List<ViewResponse.Row<Key.ComplexKey, ClientJob>> rows = builder.newRequest(Key.Type.COMPLEX, ClientJob.class).
                    build().getResponse().getRows();

            for (ViewResponse.Row<Key.ComplexKey, ClientJob> row : rows) {
                list.add(row.getValue());
            }
            return list;
        } catch (Exception ex) {
            return list;
        }
    }

    @Override
    public List<ClientJob> select(int limit, Long skip) {
        List<ClientJob> list = new ArrayList<>();
        try {
            ViewRequestBuilder builder = serviceFactory.getDb().getViewRequestBuilder(NAMESPACE, "select");
            List<ViewResponse.Row<Key.ComplexKey, ClientJob>> rows = builder.newRequest(Key.Type.COMPLEX, ClientJob.class).
                    limit(limit).skip(skip).
                    build().getResponse().getRows();

            for (ViewResponse.Row<Key.ComplexKey, ClientJob> row : rows) {
                list.add(row.getValue());
            }
            return list;

        } catch (Exception ex) {
            return list;
        }
    }

    @Override
    public List<ClientJob> selectRunning() {
        List<ClientJob> list = new ArrayList<>();
        try {
            ViewRequestBuilder builder = serviceFactory.getDb().getViewRequestBuilder(NAMESPACE, "selectRunning");
            List<ViewResponse.Row<Key.ComplexKey, ClientJob>> rows = builder.newRequest(Key.Type.COMPLEX, ClientJob.class).
                    build().getResponse().getRows();

            for (ViewResponse.Row<Key.ComplexKey, ClientJob> row : rows) {
                list.add(row.getValue());
            }
            return list;
        } catch (Exception ex) {
            return list;
        }
    }

    @Override
    public ClientJob selectById(String id) {
        try {
            ViewRequestBuilder builder = serviceFactory.getDb().getViewRequestBuilder(NAMESPACE, "selectById");
            Key.ComplexKey complex = new Key().complex(id);
            return builder.newRequest(Key.Type.COMPLEX, ClientJob.class).
                    keys(complex).
                    build().getResponse().getRows().get(0).getValue();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public ClientJob selectByClientJobId(String clientJobId) {
        try {
            ViewRequestBuilder builder = serviceFactory.getDb().getViewRequestBuilder(NAMESPACE, "selectByClientJobId");
            Key.ComplexKey complex = new Key().complex(clientJobId);
            return builder.newRequest(Key.Type.COMPLEX, ClientJob.class).
                    keys(complex).
                    build().getResponse().getRows().get(0).getValue();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Long count() {
        Long count = null;
        Key.ComplexKey complex;

        try {
            ViewRequestBuilder builder = serviceFactory.getDb().getViewRequestBuilder(NAMESPACE, "count");

            count = builder.newRequest(Key.Type.COMPLEX, Long.class).
                    reduce(true).
                    build().getResponse().getRows().get(0).getValue();

        } catch (Exception ex) {
            return new Long(0);
        }
        return count == null ? new Long(0) : count;
    }

    @Override
    public ClientJob updateById(ClientJob clientJob) {
        ClientJob existClientJob = this.selectById(clientJob.get_id());

        existClientJob = (ClientJob) JsonUtils.merge(existClientJob, clientJob);
        long time = new Date().getTime();
        existClientJob.setUpdDate(time);

        Response update = serviceFactory.getDb().update(existClientJob);
        existClientJob.set_rev(update.getRev());

        return existClientJob;
    }

    @Override
    public void deleteById(String id) {
        ClientJob clientJob = this.selectById(id);
        serviceFactory.getDb().remove(clientJob);
    }

    @Override
    public void bulk(List<ClientJob> clientJobList) {
        serviceFactory.getDb().bulk(clientJobList);
    }
}