package org.opencloudengine.garuda.client.examples;


import org.opencloudengine.garuda.client.HdfsRequest;
import org.opencloudengine.garuda.model.HdfsFileStatus;
import org.opencloudengine.garuda.model.HdfsListStatus;
import org.springframework.http.HttpMethod;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by uengine on 2016. 9. 8..
 */
public class HdfsExample {
    public static void main(String[] args) throws Exception {

        /**
         * HrmJobRequest 세팅
         */
        HdfsRequest request = new HdfsRequest("52.78.88.87", 8080);

        HdfsFileStatus status = request.getStatus("/user");
        System.out.println(status.toString());

        HdfsListStatus listStatus = request.listStatus("/user", 1, 10, null);
        System.out.println(listStatus.toString());

        File file = new File("/Users/uengine/registry.sh");
        request.createFile("/user/ubuntu/mesos.zip", "ubuntu", "ubuntu", "755", true, file);

        request.appnedFile("/user/ubuntu/mesos.zip", file);

        request.createDir("/user/ubuntu/testDir", "ubuntu", "ubuntu", "755");

        request.rename("/user/ubuntu/testDir", "testDir2");

        request.owner("/user/ubuntu/testDir2", "hdfs", "hdfs", true);

        request.permission("/user/ubuntu/testDir2", "777", true);

        request.deleteFile("/user/ubuntu/testDir2");

        request.deleteFile("/user/ubuntu/mesos.zip");
    }
}
