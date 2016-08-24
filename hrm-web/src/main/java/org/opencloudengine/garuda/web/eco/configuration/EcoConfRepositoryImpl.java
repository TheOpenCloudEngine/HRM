package org.opencloudengine.garuda.web.eco.configuration;

import com.cloudant.client.api.model.Response;
import com.cloudant.client.api.views.Key;
import com.cloudant.client.api.views.ViewRequestBuilder;
import org.opencloudengine.garuda.couchdb.CouchServiceFactory;
import org.opencloudengine.garuda.util.JsonUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class EcoConfRepositoryImpl implements EcoConfRepository, InitializingBean {

    private String NAMESPACE = "ecoConf";

    @Autowired
    CouchServiceFactory serviceFactory;

    EcoConf cash;

    @Override
    public EcoConf getCash() {
        if (cash == null) {
            cash = this.select();
        }
        return cash;
    }

    private void updateCash() {
        cash = this.select();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.updateCash();
    }

    @Override
    public EcoConf insert(EcoConf ecoConf) {
        long time = new Date().getTime();
        ecoConf.setDocType(NAMESPACE);
        ecoConf.setRegDate(time);
        ecoConf.setUpdDate(time);

        Response response = serviceFactory.getDb().save(ecoConf);
        ecoConf.set_id(response.getId());
        ecoConf.set_rev(response.getRev());

        this.updateCash();
        return ecoConf;
    }

    @Override
    public EcoConf select() {
        try {
            ViewRequestBuilder builder = serviceFactory.getDb().getViewRequestBuilder(NAMESPACE, "select");
            //Key.ComplexKey complex = new Key().complex(managementId).add(name);
            return builder.newRequest(Key.Type.COMPLEX, EcoConf.class).
                    //      keys(complex).
                            build().getResponse().getRows().get(0).getValue();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public EcoConf update(EcoConf ecoConf) {
        EcoConf existIam = this.select();

        existIam = (EcoConf) JsonUtils.merge(existIam, ecoConf);
        long time = new Date().getTime();
        existIam.setUpdDate(time);

        Response update = serviceFactory.getDb().update(existIam);
        existIam.set_rev(update.getRev());

        this.updateCash();
        return existIam;
    }
}