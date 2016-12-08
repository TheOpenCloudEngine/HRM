package org.opencloudengine.garuda.client.examples;


import org.opencloudengine.garuda.client.HrmJobRequest;
import org.opencloudengine.garuda.model.clientJob.ClientJob;
import org.opencloudengine.garuda.model.request.HbaseShellRequest;
import org.opencloudengine.garuda.model.request.PhoenixRequest;
import org.opencloudengine.garuda.model.request.PhoenixSource;

import java.util.ArrayList;

/**
 * Created by uengine on 2016. 9. 8..
 */
public class PhoenixExample {
    public static void main(String[] args) throws Exception {


        /**
         * HrmJobRequest 세팅
         */
        HrmJobRequest request = new HrmJobRequest("ambari.essencia.live", 80);


        /**
         * 피닉스 잡 실행
         */
        PhoenixRequest phoenixRequest = new PhoenixRequest();

        phoenixRequest.setDoAs("ubuntu");

        PhoenixSource source1 = new PhoenixSource();
        source1.setType(PhoenixSource.SQL);
        source1.setValue("DROP TABLE us_population;\n" +
                "CREATE TABLE IF NOT EXISTS us_population (\n" +
                "      state CHAR(2) NOT NULL,\n" +
                "      city VARCHAR NOT NULL,\n" +
                "      population BIGINT\n" +
                "      CONSTRAINT my_pk PRIMARY KEY (state, city));");


        PhoenixSource source2 = new PhoenixSource();
        source2.setType(PhoenixSource.CSV);
        source2.setValue("NY,New York,8143197\n" +
                "CA,Los Angeles,3844829\n" +
                "IL,Chicago,2842518\n" +
                "TX,Houston,2016582\n" +
                "PA,Philadelphia,1463281\n" +
                "AZ,Phoenix,1461575\n" +
                "TX,San Antonio,1256509\n" +
                "CA,San Diego,1255540\n" +
                "TX,Dallas,1213825\n" +
                "CA,San Jose,912332");

        PhoenixSource source3 = new PhoenixSource();
        source3.setType(PhoenixSource.SQL);
        source3.setValue("SELECT state as \"State\",count(city) as \"City Count\",sum(population) as \"Population Sum\"\n" +
                "FROM us_population\n" +
                "GROUP BY state\n" +
                "ORDER BY sum(population) DESC;");

        ArrayList<PhoenixSource> sources = new ArrayList<>();
        sources.add(source1);
        sources.add(source2);
        sources.add(source3);

        phoenixRequest.setSources(sources);
        phoenixRequest.setDelimiter(",");


        //호출(Send)
        request.setRequest(phoenixRequest);
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
