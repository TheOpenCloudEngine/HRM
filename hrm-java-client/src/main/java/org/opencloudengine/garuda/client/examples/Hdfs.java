package org.opencloudengine.garuda.client.examples;

import org.opencloudengine.garuda.client.HrmJobRequest;
import org.opencloudengine.garuda.model.request.HiveRequest;
import org.opencloudengine.garuda.util.HttpUtils;

/**
 * Created by uengine on 2016. 9. 8..
 */
public class Hdfs {
    public static void main(String[] args) throws Exception {
        HrmJobRequest request = new HrmJobRequest();
        HiveRequest hive = new HiveRequest();
        hive.setSql("bbb");
        request.setRequest(hive);
        String send = request.send();
        System.out.println(send);
    }
}
