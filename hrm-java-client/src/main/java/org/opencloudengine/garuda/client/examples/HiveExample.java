package org.opencloudengine.garuda.client.examples;


import org.opencloudengine.garuda.client.HrmJobRequest;
import org.opencloudengine.garuda.model.clientJob.ClientJob;
import org.opencloudengine.garuda.model.clientJob.ClientResult;
import org.opencloudengine.garuda.model.request.HiveRequest;
import org.opencloudengine.garuda.model.request.ShellRequest;
import org.opencloudengine.garuda.model.request.SparkRequest;

/**
 * Created by uengine on 2016. 9. 8..
 */
public class HiveExample {
    public static void main(String[] args) throws Exception {


        /**
         * HrmJobRequest 세팅
         */
        HrmJobRequest request = new HrmJobRequest("52.78.88.87", 8080);


        /**
         * 하이브 잡 실행
         */
        HiveRequest hiveRequest = new HiveRequest();
        hiveRequest.setDoAs("ubuntu");
        hiveRequest.setSql("select count(*) from invites;");

        request.setRequest(hiveRequest);
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

        //(하이브 only) 결과받기
        ClientResult clientResult = runningJob.getClientResult();
        if (clientResult != null) {
            String csv = clientResult.getCsv();
            System.out.println(csv);
        }

        /**
         * 잡 Kill
         */
        ClientJob killJob = request.killJob(clientJobId);

        //종료 로그
        String killLog = killJob.getKillLog();
        System.out.println(killLog);
    }
}
