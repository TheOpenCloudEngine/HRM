
## Install Couchdb

### CentOS


```
# install dependency

sudo yum install autoconf
sudo yum install autoconf-archive
sudo yum install automake
sudo yum install curl-devel
sudo yum install erlang-asn1
sudo yum install erlang-erts
sudo yum install erlang-eunit
sudo yum install erlang-os_mon
sudo yum install erlang-xmerl
sudo yum install help2man
sudo yum install js-devel
sudo yum install libicu-devel
sudo yum install libtool
sudo yum install perl-Test-Harness

# Could not find the Erlang crypto library => Fix.

sudo yum install openssl-devel.x86_64

# install javac

javac 없이 open-jdk 만 설치되어 있을 경우, 다음을 설치

```
$ yum list java*jdk-devel
Loaded plugins: fastestmirror Loading mirror speeds from cached hostfile Available Packages...

$ yum install java-1.7.0-openjdk-devel

$ javac -version
javac 1.7.0_09

# install erlang

$ wget http://www.erlang.org/download/otp_src_R14B01.tar.gz

$ tar -xvf otp_src_R14B01.tar.gz

$ cd otp_src_R14B01

$ ./configure

$ make && make install


# install couchdb

$ wget http://apache.tt.co.kr/couchdb/source/1.6.1/apache-couchdb-1.6.1.tar.gz

$ ./configure

$ make && sudo make install

$ sudo adduser --no-create-home couchdb
$ cd /usr/local/var/run/couchdb
$ sudo chown -R couchdb:couchdb /usr/local/var/lib/couchdb /usr/local/var/log/couchdb /usr/local/var/run/couchdb
$ sudo ln -sf /usr/local/etc/rc.d/couchdb /etc/init.d/couchdb
$ sudo chkconfig --add couchdb
$ sudo chkconfig couchdb on
$ sudo vi /usr/local/etc/couchdb/local.ini
$ sudo service couchdb start
$ curl http://localhost:5984

# security couchdb

$ chown -R couchdb:couchdb /usr/local/etc/couchdb

# log couchdb

$ vi /usr/local/etc/couchdb/local.ini
.
.
[log]
;level = debug
file = /var/log/couchdb/couch.log
.
.


# install tomcat7

wget http://apache.tt.co.kr/tomcat/tomcat-7/v7.0.72/bin/apache-tomcat-7.0.72.tar.gz

tar xvf apache-tomcat-7.0.72.tar.gz

# Maven install

$ wget http://mirror.cc.columbia.edu/pub/software/apache/maven/maven-3/3.0.5/binaries/apache-maven-3.0.5-bin.tar.gz
$ sudo tar xzf apache-maven-3.0.5-bin.tar.gz -C /usr/local
$ cd /usr/local
$ sudo ln -s apache-maven-3.0.5 maven

$ sudo vi /etc/profile.d/maven.sh
export M2_HOME=/usr/local/maven 
export PATH=${M2_HOME}/bin:${PATH}

# download hrm source

$ yum install git

$ git clone https://github.com/SeungpilPark/HRM

$ cd HRM

$ mvn install

```

### Ubuntu

```
$ sudo apt-get install software-properties-common -y

$ sudo add-apt-repository ppa:couchdb/stable -y

$ sudo apt-get update -y

# remove any existing couchdb binaries
$ sudo apt-get remove couchdb couchdb-bin couchdb-common -yf

$ sudo apt-get install -V couchdb

  Reading package lists...
  Done Building dependency tree
  Reading state information...
  Done
  The following extra packages will be installed:
  couchdb-bin (x.y.z0-0ubuntu2) couchdb-common (x.y.z-0ubuntu2) couchdb (x.y.z-0ubuntu2)

# manage via upstart for 14.04
$ sudo stop couchdb
  couchdb stop/waiting
  
# update /etc/couchdb/local.ini with 'bind_address=0.0.0.0' as needed
$ sudo start couchdb
  couchdb start/running, process 3541

# manage via upstart for 14.04
$ sudo stop couchdb
  couchdb stop/waiting
  
# update /etc/couchdb/local.ini with 'bind_address=0.0.0.0' as needed
$ sudo start couchdb
  couchdb start/running, process 3541

# manage via systemd for 15.10 and newer
$ sudo systemctl stop couchdb

# update /etc/couchdb/local.ini with 'bind_address=0.0.0.0' as needed
# or add 'level=debug' to the [log] section
$ sudo systemctl start couchdb

# systemd is not very chatty so lets get a status
$ sudo systemctl status couchdb
● couchdb.service - Apache CouchDB
   Loaded: loaded (/lib/systemd/system/couchdb.service; enabled; vendor preset: enabled)
   Active: active (running) since Sun 2016-01-31 23:50:50 UTC; 5s ago
 Main PID: 3106 (beam.smp)
   Memory: 20.3M
      CPU: 394ms
   CGroup: /system.slice/couchdb.service
           ├─3106 /usr/lib/erlang/erts-7.0/bin/beam.smp -Bd -K true -A 4 -- -root /usr/lib/erlang -progname erl -- -home /var/lib/couchdb -- -noshell -noin...
           └─3126 sh -s disksup

Jan 31 23:50:50 u1 systemd[1]: Started Apache CouchDB.
Jan 31 23:50:50 u1 couchdb[3106]: Apache CouchDB 1.6.1 (LogLevel=info) is starting.
Jan 31 23:50:51 u1 couchdb[3106]: Apache CouchDB has started. Time to relax.
Jan 31 23:50:51 u1 couchdb[3106]: [info] [<0.33.0>] Apache CouchDB has started on http://127.0.0.1:5984/

```

### 프로퍼티 설정하기

소스코드의 hrm-web/src/main/webapp/WEB-INF/config.properties 파일에서 프로퍼티를 설정하도록 합니다.

```
.
.
.
###########################################
## System Administrator Configuration
###########################################

system.admin.username=support@iam.co.kr
==> 로그인 화면 어드민 아이디
system.admin.password=admin
==> 로그인 화면 어드민 패스워드


###########################################
## File Upload Configuration
###########################################

fileupload.max.size=100000000
fileupload.default.encoding=UTF-8

==> hdfs 업로그 max 사이즈

###########################################
## DataSource Configuration
###########################################

couch.db.url=http://52.79.164.208:5984
==> couch 데이터베이스 url

couch.db.username=admin
couch.db.password=admin
==> couch 데이터베이스 user / password

couch.db.database=sk
==> couch 데이터베이스 이름

couch.db.autoview=true
==> couch view 자동 생성

###########################################
## System Configuration
###########################################

application.home=/root/hrm/
==> Hrm 에코 시스템 실행 로그가 임시저장될 패스 (접근 권한 필요)

system.hdfs.super.user=hdfs
==> 하둡 시스템 슈퍼유저 (하둡을 인스톨 할 때 지정된 값)

###########################################
## Command Configuration
###########################################

########## Home Properties ##########
hadoop.hadoop.home=/usr/hdp/2.4.2.0-258/hadoop
==> 하둡 홈.

########## Agent Properties ##########
mr.agent.path=/root/hrm-mr-agent-2.1.0-SNAPSHOT.jar
==> hrm-mr-agent 빌드 결과물의 패스.

```

### 톰캣에 하둡 슈퍼 유저 설정하기 및 권한

```
톰캣의 catalina.sh 파일의 첫번째 라인에...

$ vi catalina.sh

export HADOOP_USER_NAME=hdfs
JAVA_OPTS="-Djava.awt.headless=true -Xmx1024m -XX:+UseConcMarkSweepGC"


톰캣 폴더를 +x 권한 주기

$ chmod -R +x tomcat
```





