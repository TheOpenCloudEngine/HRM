# Rest Api

HRM 로 호출할 수 있는 Rest Api 목록입니다.

## ClientJob

하둡 에코 시스템의 잡을 실행, 조회, 종료 할 수 있습니다.

#### POST /rest/v1/clientJob/{ecoSystemName}

신규 잡을 생성할 때는 Basic Parameter 와 Native Parameter 를 하나의 JSON Body 로 묶어서 호출하게 됩니다.

```
ex)

curl -X POST -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d '{

==== Basic Parameter =====

  "clientJobName" : "ggss",
  "streamingHook" : "http://localhost:8080/streamingHookUrl",
  "doAs" : "ubuntu",
  "eventHook" : "http://localhost:8080/eventHookUrl",
  
==== Native Parameter =====

  "master" : "yarn",
  "deployMode" : "cluster",
  "applicationJar" : "/usr/hdp/2.4.2.0-258/spark/lib/spark-examples*.jar",
  "applicationArguments" : [ "10" ],
  "className" : "org.apache.spark.examples.SparkPi"
}' http://52.78.88.87:8080/rest/v1/clientJob/spark
```

Basic Parameter 와 Native Parameter 는 다음을 참조하시길 바랍니다.

 - [Basic Parameters](tutorials.md#basic-parameter)
 - [Native Client Parameters](native.md)

#### POST /rest/v1/clientJob/hive

하이브 잡을 실행합니다.

#### POST /rest/v1/clientJob/pig

피그 잡을 실행합니다.

#### POST /rest/v1/clientJob/mr

mr 잡을 실행합니다.

#### POST /rest/v1/clientJob/spark

스파크 잡을 실행합니다.

#### POST /rest/v1/clientJob/python

파이썬 잡을 실행합니다.

#### POST /rest/v1/clientJob/shell

배쉬 쉘 잡을 실행합니다.

#### POST /rest/v1/clientJob/java

자바 잡을 실행합니다.

#### POST /rest/v1/clientJob/hbaseShell

Hbase Shell 잡을 실행합니다.

#### POST /rest/v1/clientJob/hbaseClass

Hbase Class 잡을 실행합니다.

#### POST /rest/v1/clientJob/phoenix

Phoenix 잡을 실행합니다.

#### GET /rest/v1/clientJob/job/{clientJobId}

주어진 clientJobId 로 잡의 정보를 얻습니다.

#### DELETE /rest/v1/clientJob/kill/{clientJobId}

주어진 clientJobId 로 잡을 강제 종료시킵니다.


## Hdfs Job

HDFS 파일 시스템을 컨트롤 할 수 있습니다.

#### GET /rest/v1/hdfs/status/list

주어진 패스의 하위 폴더 또는 파일 스테이터스를 반환합니다.
start 와 end 값이 주어지지 않을 경우 default 는 각각 1, 100 입니다.
또한 max 반환 건수는 100건 입니다.

리퀘스트 파라미터 (?key=val&key=val...) 로 구성합니다.

| 파리미터 | 타입   | 설명             |
|----------|--------|------------------|
| path     | String | Hdfs 파일 패스   |
| start    | int    | 조회 시작 인덱스 |
| end      | int    | 조회 종료 인덱스 |
| filter   | String | 파일 이름 필터   |

  
#### GET /rest/v1/hdfs/status

주어진 패스 한건의 파일 스테이터스를 반환합니다.

리퀘스트 파라미터 (?key=val&key=val...) 로 구성합니다.

| 파리미터 | 타입   | 설명             |
|----------|--------|------------------|
| path     | String | Hdfs 파일 패스   |


#### POST /rest/v1/hdfs/file

신규 파일을 생성하며, POST 로 전송된 바이너리를 파일에 기록합니다.

리퀘스트 파라미터 (?key=val&key=val...) 로 구성합니다.

| 파리미터   | 타입    | 설명               |
|------------|---------|--------------------|
| path       | String  | Hdfs 파일 패스     |
| owner      | String  | 파일 소유자        |
| group      | String  | 파일 소유자 그룹   |
| permission | String  | 파일 퍼미션(1~755) |
| overwrite  | boolean | 기존 파일 덮어쓰기 |


#### PUT /rest/v1/hdfs/file

POST 로 전송된 바이너리를 기존에 존재하는 파일에 이어서 기록합니다.

리퀘스트 파라미터 (?key=val&key=val...) 로 구성합니다.

| 파리미터   | 타입    | 설명               |
|------------|---------|--------------------|
| path       | String  | Hdfs 파일 패스     |


#### DELETE /rest/v1/hdfs/file

파일을 삭제합니다.

리퀘스트 파라미터 (?key=val&key=val...) 로 구성합니다.

| 파리미터   | 타입    | 설명               |
|------------|---------|--------------------|
| path       | String  | Hdfs 파일 패스     |


#### GET /rest/v1/hdfs/file

파일의 내용을 내려받습니다.

브라우저에서 입력될 경우 파일이 다운로드 되며, Rest api 로 호출될 경우 다음의 헤더 형태로 바이너리가 전송되게 됩니다.

```
Content-Transfer-Encoding: binary
Content-Type: application/force-download
```

리퀘스트 파라미터 (?key=val&key=val...) 로 구성합니다.

| 파리미터   | 타입    | 설명               |
|------------|---------|--------------------|
| path       | String  | Hdfs 파일 패스     |


#### GET /rest/v1/hdfs/emptyfile

빈 파일을 하나 생성합니다.

리퀘스트 파라미터 (?key=val&key=val...) 로 구성합니다.

| 파리미터   | 타입    | 설명               |
|------------|---------|--------------------|
| path       | String  | Hdfs 파일 패스     |
| owner      | String  | 파일 소유자        |
| group      | String  | 파일 소유자 그룹   |
| permission | String  | 파일 퍼미션(1~755) |
| overwrite  | boolean | 기존 파일 덮어쓰기 |

#### POST /rest/v1/hdfs/directory

디렉토리를 생성합니다. 덮어쓰기 옵션은 존재하지 않습니다.

리퀘스트 파라미터 (?key=val&key=val...) 로 구성합니다.

| 파리미터   | 타입    | 설명               |
|------------|---------|--------------------|
| path       | String  | Hdfs 파일 패스     |
| owner      | String  | 파일 소유자        |
| group      | String  | 파일 소유자 그룹   |
| permission | String  | 파일 퍼미션(1~755) |

#### PUT /rest/v1/hdfs/rename

파일 또는 폴더의 이름을 변경합니다.

리퀘스트 파라미터 (?key=val&key=val...) 로 구성합니다.

| 파리미터   | 타입    | 설명               |
|------------|---------|--------------------|
| path       | String  | Hdfs 파일 패스     |
| rename     | String  | 변경 될 이름       |

#### PUT /rest/v1/hdfs/owner

파일의 소유권자를 변경합니다.

recursive 를 true 로 설정 할 경우 하위 폴더 및 파일에 모두 적용됩니다.

리퀘스트 파라미터 (?key=val&key=val...) 로 구성합니다.

| 파리미터   | 타입    | 설명               |
|------------|---------|--------------------|
| path       | String  | Hdfs 파일 패스     |
| owner      | String  | 파일 소유자        |
| group      | String  | 파일 소유자 그룹   |
| recursive  | boolean | 하위 폴더 및 파일에 모두 적용 |

#### PUT /rest/v1/hdfs/permission

파일의 퍼미션을 변경합니다. (1~755)

recursive 를 true 로 설정 할 경우 하위 폴더 및 파일에 모두 적용됩니다.

리퀘스트 파라미터 (?key=val&key=val...) 로 구성합니다.

| 파리미터   | 타입    | 설명               |
|------------|---------|--------------------|
| path       | String  | Hdfs 파일 패스     |
| permission | String  | 파일 퍼미션(1~755)  |
| recursive  | boolean | 하위 폴더 및 파일에 모두 적용 |

