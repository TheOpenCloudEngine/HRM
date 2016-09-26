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

        //단일 상태 보기
        HdfsFileStatus status = request.getStatus("/user");
        System.out.println(status.toString());

        //하위 폴더 및 파일 상태 보기
        HdfsListStatus listStatus = request.listStatus("/user", 1, 10, null);
        System.out.println(listStatus.toString());

        //신규 생성
        File file = new File("/Users/uengine/registry.sh");
        request.createFile("/user/ubuntu/mesos.zip", "ubuntu", "ubuntu", "755", true, file);

        //기존 파일에 추가하여 쓰기
        request.appnedFile("/user/ubuntu/mesos.zip", file);

        //디렉토리 생성
        request.createDir("/user/ubuntu/testDir", "ubuntu", "ubuntu", "755");

        //이름 바꾸기
        request.rename("/user/ubuntu/testDir", "testDir2");

        //소유자 바꾸기
        request.owner("/user/ubuntu/testDir2", "hdfs", "hdfs", true);

        //퍼미션 바꾸기
        request.permission("/user/ubuntu/testDir2", "777", true);

        //삭제하기
        request.deleteFile("/user/ubuntu/testDir2");


        request.deleteFile("/user/ubuntu/mesos.zip");
    }
}
