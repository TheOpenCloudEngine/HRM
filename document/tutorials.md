# Tutorial

## 로그인

초기 로그인 아이디와 패스워드는 support@iam.co.kr / admin 입니다.

![](images/login.png)

## Eco System - Configuration

Configuration 메뉴에서 하둡 에코 시스템의 홈 디렉토리와 HDFS 슈퍼 유저를 입력합니다.

 - Hdfs Super User : 하둡 슈퍼 유저
 - Hadoop Home : 하둡 홈
 - Hive Home : 하이브 홈
 - Pig Home : 피드 홈
 - Spark Home : 스파크 홈
 - Hdfs Home : Hdfs 홈
 - Mapreduce Home : 맵리듀스 홈
 - Yarn Home : 얀 홈
 - Hbase Home : Hbase 홈
 - Phoenix Home : 피닉스 홈
 - Java Home : 자바 홈

![](images/configuration.png)

## Eco System - System Users

하둡 에코시스템에서 시스템 유저를 추가하고 삭제할 수 있는 메뉴입니다.

![](images/sysuser.png)

## Eco System - Eco systems

에코 시스템 메뉴에서 호출 할 수 있는 항목은 다음과 같습니다.

 - Hive
 - Spark
 - Map Reduce
 - Pig
 - Java
 - Python
 - Shell
 - Hbase Shell
 - Hbase Class
 - Phoenix
 
각 콘솔 메뉴 클릭시, 다음과 같은 화면이 나타납니다.

![](images/ecos.png)

### Parameters

콘솔 메뉴의 파라미터는 Basic Parameter 와 Native Client Parameter 메뉴로 나뉩니다.

### Basic Parameter

Basic Parameter 는 공통 파라미터로서, Job 의 이벤트 훅 및 디폴트 유저를 설정할 수 있습니다. 

 - clientJobId (optional) : 클라이언트 잡의 PK 입니다. 빈 값일 경우 랜덤한 스트링을 부여하게 됩니다. 
 - clientJobName (optional): 클라이언트 잡의 네임.
 - streamingHook (optional): 잡이 실행되는 동안 발생하는 로그를 지정된 url 로 송출합니다.
 - doAs (optional) : 잡이 실행되는 동안 적용될 리눅스 시스템의 하둡 사용자 입니다. ex)ubuntu 
 - eventHook (optional) : 잡이 실행되는 동안 status 변화가 생길 때 지정된 url 로 송출합니다.
 
### Native Client Parameter

Native Client Parameter 는 각 에코 시스템에 특화된 파라미터들입니다.

Native Client Parameter 는 HRM 이 설치된 Native Client 의 CLI(Command Line Interface) 의 파라미터들을 웹으로 구현한 것입니다.

[Native Client Parameters](native.md)

### Toolbar

 - Send : 잡을 실행합니다.
 - Save : Collections 잡일 경우 현재 파라미터를 선택된 Collection 에 덮어씁니다.
 - Save as : 새로운 Collection 잡으로 저장합니다.
 - Curl : 현재 파라미터들을 Curl 커맨드로 생성해 줍니다.
 
### Collections

콜렌션 메뉴는 작성한 파라미터 내용을 저장하여 재 호출 할 수 있도록 하는 기능입니다.

 - Edit : 콜렉션 이름을 변경합니다.
 - Duplicate : 콜렉션을 복사합니다.
 - Delete : 콜렉션을 삭제합니다.
 
## Hdfs Browser

Hdfs 브라우저 메뉴는 Hdfs 슈퍼유저로 관리되는 하둡 파일시트템 매니지먼트 콘솔입니다.

 - New Folder : 새로운 폴더를 생성합니다.
 - Upload : 파일을 업로드 합니다.
 - Download : 파일을 다운로드 합니다.
 - Rename : 이름을 변경합니다.
 - Owner : 파일의 소유권자 (Owner,Group) 을 변경합니다. Recursive 를 체크할 경우 하위 폴더 및 파일들도 함께 변경합니다.
 - Permission : 파일의 퍼미션을 변경합니다.(1~755) Recursive 를 체크할 경우 하위 폴더 및 파일들도 함께 변경합니다.
 





