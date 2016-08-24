package org.opencloudengine.garuda.web.contactus;

import com.cloudant.client.api.model.Response;
import org.opencloudengine.garuda.common.repository.PersistentRepositoryImpl;
import org.mybatis.spring.SqlSessionTemplate;
import org.opencloudengine.garuda.couchdb.CouchServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class ContactUsRepositoryImpl implements ContactUsRepository {

    private String NAMESPACE = "contact_us";

    @Autowired
    CouchServiceFactory serviceFactory;

    @Override
    public ContactUs insert(ContactUs contactUs) {
        long time = new Date().getTime();
        contactUs.setDocType(NAMESPACE);
        contactUs.setRegDate(time);
        contactUs.setUpdDate(time);

        Response response = serviceFactory.getDb().save(contactUs);
        contactUs.set_id(response.getId());
        contactUs.set_rev(response.getRev());
        return contactUs;
    }
}
