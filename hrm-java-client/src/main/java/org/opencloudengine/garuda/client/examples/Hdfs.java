package org.opencloudengine.garuda.client.examples;

import org.opencloudengine.garuda.client.HrmJobRequest;
import org.opencloudengine.garuda.model.clientJob.ClientJob;
import org.opencloudengine.garuda.model.request.HiveRequest;
import org.opencloudengine.garuda.util.HttpUtils;

/**
 * Created by uengine on 2016. 9. 8..
 */
public class Hdfs {
    public static void main(String[] args) throws Exception {
        HrmJobRequest request = new HrmJobRequest("localhost", 8080);
        HiveRequest hive = new HiveRequest();
        hive.setSql("Show tables;");
        request.setRequest(hive);
        ClientJob job = request.createJob();
        System.out.println(job);
    }
}
