# Java Client Api

HRM 은 자바 클라이언트 API 를 제공합니다.

본 프로젝트의 hrm-java-client 를 빌드함으로써 사용 가능합니다.

hrm-java-client/src/main/java/org/opencloudengine/garuda/client/examples 폴더에서 각 에코 시스템 호출 및 Hdfs 컨트롤에 대한 example 코드를 확인하실 수 있습니다.

### Native Client Job Example

다음은 하이브 잡을 호출하고, 상태를 확인하며, 종료시키는 예제입니다.

나머지 Java,Mr,Pig,Python,Shell,Spark 는 example 폴더에서 확인하시길 바랍니다.

```
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

```

### Hdfs Job Example

Hdfs 파일시스템을 호출하는 예제코드입니다.

```
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

```