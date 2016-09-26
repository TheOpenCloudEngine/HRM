package org.opencloudengine.garuda.client.examples;


import org.opencloudengine.garuda.client.HrmJobRequest;
import org.opencloudengine.garuda.model.clientJob.ClientJob;
import org.opencloudengine.garuda.model.request.MrRequest;
import org.opencloudengine.garuda.model.request.SparkRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uengine on 2016. 9. 8..
 */
public class SparkExample {
    public static void main(String[] args) throws Exception {


        /**
         * HrmJobRequest 세팅
         */
        HrmJobRequest request = new HrmJobRequest("52.78.88.87", 8080);


        /**
         * 스파크 잡 실행
         */
        SparkRequest sparkRequest = new SparkRequest();

        sparkRequest.setDoAs("ubuntu");
        sparkRequest.setClassName("org.apache.spark.examples.SparkPi");
        sparkRequest.setApplicationJar("/usr/hdp/2.4.2.0-258/spark/lib/spark-examples*.jar");

        List<String> argsList = new ArrayList<>();
        argsList.add("10");
        sparkRequest.setApplicationArguments(argsList);

        sparkRequest.setMaster("yarn");
        sparkRequest.setDeployMode("cluster");

        //호출(Send)
        request.setRequest(sparkRequest);
        ClientJob job = request.createJob();

        //잡 아이디
        String clientJobId = job.getClientJobId();


        /**
         * 잡 진행상태 보기
         */
        ClientJob runningJob = request.getJob(clientJobId);

        //진행상태
        runningJob.getStatus();

        //얀 어플리케이션 아이디 목록
        runningJob.getApplicationIds();

        //맵리듀스 아이디 목록
        runningJob.getMapreduceIds();

        //잡 시작시간
        runningJob.getStartDate();

        //실행 스크립트
        runningJob.getExecuteScript();

        //실행 커맨드
        runningJob.getExecuteCli();

        //로그
        runningJob.getStdout();

        /**
         * 잡 Kill
         */
//        ClientJob killJob = request.killJob(clientJobId);
//
//        //종료 로그
//        String killLog = killJob.getKillLog();
//        System.out.println(killLog);
    }
}
