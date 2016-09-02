package org.opencloudengine.garuda.web.eco.sysuser;

import com.cloudant.client.api.model.Response;
import com.cloudant.client.api.views.Key;
import com.cloudant.client.api.views.ViewRequestBuilder;
import com.cloudant.client.api.views.ViewResponse;
import org.opencloudengine.garuda.couchdb.CouchServiceFactory;
import org.opencloudengine.garuda.util.JsonUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.opencloudengine.garuda.model.Sysuser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class SysuserRepositoryImpl implements SysuserRepository, InitializingBean {

    private String NAMESPACE = "sysuser";

    @Autowired
    CouchServiceFactory serviceFactory;

    List<Sysuser> cash;

    @Override
    public List<Sysuser> getCash() {
        if (cash == null) {
            cash = this.selectAll();
        }
        return cash;
    }

    private void updateCash() {
        cash = this.selectAll();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.updateCash();
    }

    @Override
    public Sysuser insert(Sysuser sysuser) {
        long time = new Date().getTime();
        sysuser.setDocType(NAMESPACE);
        sysuser.setRegDate(time);
        sysuser.setUpdDate(time);

        Response response = serviceFactory.getDb().save(sysuser);
        sysuser.set_id(response.getId());
        sysuser.set_rev(response.getRev());

        this.updateCash();
        return sysuser;
    }

    @Override
    public List<Sysuser> selectAll() {
        List<Sysuser> list = new ArrayList<>();
        try {
            ViewRequestBuilder builder = serviceFactory.getDb().getViewRequestBuilder(NAMESPACE, "select");
            //Key.ComplexKey complex = new Key().complex(managementId);
            List<ViewResponse.Row<Key.ComplexKey, Sysuser>> rows = builder.newRequest(Key.Type.COMPLEX, Sysuser.class).
                    //keys(complex).
                            build().getResponse().getRows();

            for (ViewResponse.Row<Key.ComplexKey, Sysuser> row : rows) {
                list.add(row.getValue());
            }
            return list;
        } catch (Exception ex) {
            return list;
        }
    }

    @Override
    public List<Sysuser> select(int limit, Long skip) {
        List<Sysuser> list = new ArrayList<>();
        try {
            ViewRequestBuilder builder = serviceFactory.getDb().getViewRequestBuilder(NAMESPACE, "select");
            //Key.ComplexKey complex = new Key().complex(managementId);
            List<ViewResponse.Row<Key.ComplexKey, Sysuser>> rows = builder.newRequest(Key.Type.COMPLEX, Sysuser.class).
                    //keys(complex).
                            limit(limit).skip(skip).
                    build().getResponse().getRows();

            for (ViewResponse.Row<Key.ComplexKey, Sysuser> row : rows) {
                list.add(row.getValue());
            }
            return list;

        } catch (Exception ex) {
            return list;
        }
    }

    @Override
    public Sysuser selectById(String id) {
        try {
            ViewRequestBuilder builder = serviceFactory.getDb().getViewRequestBuilder(NAMESPACE, "selectById");
            Key.ComplexKey complex = new Key().complex(id);
            return builder.newRequest(Key.Type.COMPLEX, Sysuser.class).
                    keys(complex).
                    build().getResponse().getRows().get(0).getValue();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<Sysuser> selectByIds(List<String> ids) {
        List<Sysuser> list = new ArrayList<>();
        try {
            ArrayList<Key.ComplexKey> complexKeyArrayList = new ArrayList<>();
            for (String id : ids) {
                complexKeyArrayList.add(new Key().complex(id));
            }
            Key.ComplexKey[] complexKeys = complexKeyArrayList.toArray(new Key.ComplexKey[complexKeyArrayList.size()]);

            ViewRequestBuilder builder = serviceFactory.getDb().getViewRequestBuilder(NAMESPACE, "selectById");
            List<ViewResponse.Row<Key.ComplexKey, Sysuser>> rows = builder.newRequest(Key.Type.COMPLEX, Sysuser.class).
                    keys(complexKeys).build().getResponse().getRows();

            for (ViewResponse.Row<Key.ComplexKey, Sysuser> row : rows) {
                list.add(row.getValue());
            }
            return list;
        } catch (Exception ex) {
            return list;
        }
    }

    @Override
    public List<Sysuser> selectLikeName(String name, int limit, Long skip) {
        List<Sysuser> list = new ArrayList<>();
        Key.ComplexKey startKey;
        Key.ComplexKey endKey;

        try {
            ViewRequestBuilder builder = serviceFactory.getDb().getViewRequestBuilder(NAMESPACE, "selectLikeName");
            startKey = new Key().complex(name);
            endKey = new Key().complex(name + "Z");
            List<ViewResponse.Row<Key.ComplexKey, Sysuser>> rows = builder.newRequest(Key.Type.COMPLEX, Sysuser.class).
                    startKey(startKey).endKey(endKey).limit(limit).skip(skip).
                    build().getResponse().getRows();

            for (ViewResponse.Row<Key.ComplexKey, Sysuser> row : rows) {
                list.add(row.getValue());
            }
            return list;
        } catch (Exception ex) {
            return list;
        }
    }

    @Override
    public Sysuser selectByName(String name) {
        try {
            ViewRequestBuilder builder = serviceFactory.getDb().getViewRequestBuilder(NAMESPACE, "selectByName");
            Key.ComplexKey complex = new Key().complex(name);
            return builder.newRequest(Key.Type.COMPLEX, Sysuser.class).
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
    public Long countLikeName(String name) {
        Long count = null;
        Key.ComplexKey startKey;
        Key.ComplexKey endKey;

        try {
            ViewRequestBuilder builder = serviceFactory.getDb().getViewRequestBuilder(NAMESPACE, "countLikeName");
            startKey = new Key().complex(name);
            endKey = new Key().complex(name + "Z");
            count = builder.newRequest(Key.Type.COMPLEX, Long.class).
                    startKey(startKey).endKey(endKey).reduce(true).
                    build().getResponse().getRows().get(0).getValue();

        } catch (Exception ex) {
            return new Long(0);
        }
        return count == null ? new Long(0) : count;
    }

    @Override
    public Sysuser updateById(Sysuser sysuser) {
        Sysuser existPolicy = this.selectById(sysuser.get_id());

        existPolicy = (Sysuser) JsonUtils.merge(existPolicy, sysuser);
        long time = new Date().getTime();
        existPolicy.setUpdDate(time);

        Response update = serviceFactory.getDb().update(existPolicy);
        existPolicy.set_rev(update.getRev());

        this.updateCash();
        return existPolicy;
    }

    @Override
    public void deleteById(String id) {
        Sysuser sysuser = this.selectById(id);
        serviceFactory.getDb().remove(sysuser);

        this.updateCash();
    }

    @Override
    public void bulk(List<Sysuser> sysuserList) {
        serviceFactory.getDb().bulk(sysuserList);
    }
}