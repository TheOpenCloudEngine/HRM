package org.opencloudengine.garuda.client.examples;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.opencloudengine.garuda.client.HrmJobRequest;
import org.opencloudengine.garuda.model.clientJob.ClientJob;
import org.opencloudengine.garuda.model.request.HiveRequest;
import org.opencloudengine.garuda.model.request.ShellRequest;
import org.opencloudengine.garuda.util.HttpUtils;
import org.opencloudengine.garuda.util.JsonUtils;

/**
 * Created by uengine on 2016. 9. 8..
 */
public class Hdfs {
    public static void main(String[] args) throws Exception {
//        HrmJobRequest request = new HrmJobRequest("localhost", 8080);
//        ShellRequest shellRequest = new ShellRequest();
//        shellRequest.setScript("pwd");
//        request.setRequest(shellRequest);
//        ClientJob job = request.createJob();
//        System.out.println(job);

        ShellRequest shellRequest = new ShellRequest();
        shellRequest.setScript("pwd");
        String marshal = JsonUtils.marshal(shellRequest);
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://52.78.88.87:8080/rest/v1/clientJob/shell");

        HttpGet get = new HttpGet("http://52.78.88.87:8080/rest/v1/clientJob/job/a86b97b7-ec32-47ae-b5ee-cf24223eebb1");
        HttpResponse response = client.execute(get);
//        HttpEntity entity = new StringEntity(marshal);
//        post.setEntity(entity);
        //HttpResponse response = client.execute(post);
        String result = EntityUtils.toString(response.getEntity());
        System.out.println("result : " + result);
    }
}
