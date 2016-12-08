package org.opencloudengine.garuda.client.examples;


import org.opencloudengine.garuda.client.HrmJobRequest;
import org.opencloudengine.garuda.model.clientJob.ClientJob;
import org.opencloudengine.garuda.model.request.HbaseShellRequest;
import org.opencloudengine.garuda.model.request.SparkRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uengine on 2016. 9. 8..
 */
public class HbaseShellExample {
    public static void main(String[] args) throws Exception {


        /**
         * HrmJobRequest 세팅
         */
        HrmJobRequest request = new HrmJobRequest("52.78.88.87", 8080);


        /**
         * Hbase Shell 잡 실행
         */
        HbaseShellRequest hbaseShellRequest = new HbaseShellRequest();

        hbaseShellRequest.setDoAs("ubuntu");

        String script = "disable 'test'\n" +
                "drop 'test'\n" +
                "create 'test', 'cf'\n" +
                "list 'test'\n" +
                "put 'test', 'row1', 'cf:a', 'value1'\n" +
                "put 'test', 'row2', 'cf:b', 'value2'\n" +
                "put 'test', 'row3', 'cf:c', 'value3'\n" +
                "put 'test', 'row4', 'cf:d', 'value4'\n" +
                "scan 'test'\n" +
                "get 'test', 'row1'\n" +
                "disable 'test'\n" +
                "enable 'test'";
        hbaseShellRequest.setScript(script);

        hbaseShellRequest.setNoninteractive(true);
        hbaseShellRequest.setDebug(true);

        //호출(Send)
        request.setRequest(hbaseShellRequest);
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
