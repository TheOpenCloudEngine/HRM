
## Install Couchdb

### CentOS


#### install dependency

카우치 데이터베이스의 디펜던시를 인스톨합니다.

```
sudo yum install gcc gcc-c++
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
```

#### add erlang dependency

openssl 을 설치하지 않을 경우 카우치 데이터베이스 인스톨 과정 중 Could not find the Erlang crypto library 오류가 납니다.

```
sudo yum install openssl-devel.x86_64
```

No curses library functions found 에러 문구가 난다면 다음을 추가로 설치합니다.

```
sudo yum install ncurses-devel
```

#### install javac

javac 없이 open-jdk 만 설치되어 있을 경우가 있습니다.

인스톨 과정 중 javac 를 사용하게 됨으로 필요 패키지를 설치하도록 합니다.

```
$ yum list java*jdk-devel
Loaded plugins: fastestmirror Loading mirror speeds from cached hostfile Available Packages...

$ yum install java-1.7.0-openjdk-devel

$ javac -version
javac 1.7.0_09
```

#### install erlang

Erlang 을 설치합니다.


```
$ wget http://www.erlang.org/download/otp_src_R14B01.tar.gz

$ tar -xvf otp_src_R14B01.tar.gz

$ cd otp_src_R14B01

$ ./configure

$ make && make install
```

#### install Mozilla SpiderMonkey

Mozilla SpiderMonkey 를 설치합니다.

```
$ wget http://ftp.mozilla.org/pub/mozilla.org/js/mozjs17.0.0.tar.gz
$ tar -xvf mozjs17.0.0.tar.gz
$ cd mozjs17.0.0/js/src/
$ ./configure
$ make && make install
```

SpiderMonkey 빌드 이후 couchdb 인스톨과정에서 lib 을 찾지 못할 경우, 패키지 인스톨 방식을 추천합니다.

```
$ yum install js-devel
```

만일 No package js-devel available. 메시지가 나온다면, 다음의 yum 레파지토리를 추가하도록 합니다.

```
$ vi /etc/yum.repos.d/rpmforge.repo
# Name: RPMforge RPM Repository for Red Hat Enterprise 5 - dag
# URL: http://rpmforge.net/
[rpmforge]
name = Red Hat Enterprise $releasever - RPMforge.net - dag
mirrorlist=http://mirrors.fedoraproject.org/mirrorlist?repo=epel-6&arch=$basearch
enabled = 1
protect = 0
gpgcheck = 0

yum clean all
yum install js-devel
```

##### install couchdb

카우치 데이터베이스 소스를 빌드하고 설치합니다.

```
$ wget http://apache.tt.co.kr/couchdb/source/1.6.1/apache-couchdb-1.6.1.tar.gz

$ tar xvf apache-couchdb-1.6.1.tar.gz

$ cd apache-couchdb-1.6.1

$ ./configure

$ make && sudo make install

$ sudo adduser --no-create-home couchdb
$ cd /usr/local/var/run/couchdb
$ sudo chown -R couchdb:couchdb /usr/local/var/lib/couchdb /usr/local/var/log/couchdb /usr/local/var/run/couchdb
$ sudo ln -sf /usr/local/etc/rc.d/couchdb /etc/init.d/couchdb
$ sudo chkconfig --add couchdb
$ sudo chkconfig couchdb on
$ sudo service couchdb start

# Test
$ curl http://localhost:5984
```

# security couchdb

어드민 사용자와 패스워드를 등록시 http://localhost:5984/_utils 화면에서 설정할 수 있지만, config 설정을 수행하는 동안 접근권한이 없다는 오류가 나오게 됩니다.

이를 방지하기 위해 다음을 수행합니다.

```
$ chown -R couchdb:couchdb /usr/local/etc/couchdb
```

바인드 어드레스를 0.0.0.0 으로 설정을 원하는 경우 다음을 수정합니다.

```
$ vi /usr/local/etc/couchdb/local.ini

.
.
[httpd]
port = 5984
bind_address = 0.0.0.0
```


#### logging couchdb

카우치 데이터베이스의 로그파일을 설정하기 위해서 /var/log/couchdb 폴더를 생성하고, local.ini 파일의 설정을 추가합니다.

```
$ mkdir /var/log/couchdb

$ chown couchdb:couchdb /var/log/couchdb

$ vi /usr/local/etc/couchdb/local.ini
.
.
[log]
;level = debug
file = /var/log/couchdb/couch.log
.
.
```

#### install tomcat7

톰캣을 다운받아 압축을 풉니다.

```
$ wget http://apache.tt.co.kr/tomcat/tomcat-7/v7.0.72/bin/apache-tomcat-7.0.72.tar.gz

$ tar xvf apache-tomcat-7.0.72.tar.gz
```

#### Maven install

메이븐 인스톨을 수행합니다.

```
$ wget http://mirror.cc.columbia.edu/pub/software/apache/maven/maven-3/3.0.5/binaries/apache-maven-3.0.5-bin.tar.gz
$ sudo tar xzf apache-maven-3.0.5-bin.tar.gz -C /usr/local
$ cd /usr/local
$ sudo ln -s apache-maven-3.0.5 maven

$ sudo vi /etc/profile.d/maven.sh
export M2_HOME=/usr/local/maven 
export PATH=${M2_HOME}/bin:${PATH}
```

#### download hrm source

HRM 소스코드를 다운로드 받습니다.

```
$ yum install git

$ git clone https://github.com/TheOpenCloudEngine/HRM

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

system.web.terminal.host=http://server-ip:port
==> 웹 콘솔 연결용 호스트 (Default: 하둡네임노드:8081)

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

### Configuration tomcat and Launch

톰캣의 catalina.sh 파일의 첫번째 라인에 하둡 슈퍼유저를 설정하는 내용을 넣습니다. 

```
$ vi catalina.sh

export HADOOP_USER_NAME=hdfs
JAVA_OPTS="-Djava.awt.headless=true -Xmx1024m -XX:+UseConcMarkSweepGC"
```

톰캣 폴더의 접근권한을 추가합니다.

```
$ chmod -R +x tomcat
```

빌드한 소스코드를 webapps 폴더로 복사하고, *root* 권한으로 톰캣을 실행하도록 합니다.

```
$ sh bin/startup.sh
```


### 웹 콘솔 설치하기

웹 콘솔을 사용하기 원하실 경우 다음의 설치 과정을 진행하시기 바랍니다.

리모트 웹 터미널은 nodejs를 기반으로 동작하며 리모트 웹 터미널로 접속하고자 하는 서버에 nodejs를 포함한 관련 모듈을 설치해야 합니다.


리모트 웹 터미널을 설치하기 위해서 OS에 따라서 다음을 참고하여 nodejs를 설치하도록 합니다.

#### Nodejs install

Centos 의 경우 다음의 커맨드로 설치합니다.

```
# yum install nodejs
# yum install npm
```
Ubuntu의 경우 다음의 커맨드로 설치할 수 있습니다.

```
# apt-get install nodejs npm
```

Ubuntu의 경우 `/usr/bin/nodejs` 로 설치가 되지만 `/usr/bin/node` 로 링크를 생성해야 합니다.

```
# ln -s /usr/bin/nodejs /usr/bin/node
```

Ubuntu 계열은 다음의 패키지를 추가설치합니다.

```
# apt-get install nodejs-legacy
# apt-get install npm
# apt-get install g++
```

#### npm 패키지 설치하기

```
# npm install npm -g
npm@2.12.1 /usr/local/lib/node_modules/npm

# npm install async -g
async@0.9.0 /usr/local/lib/node_modules/async

# npm install term.js -g
term.js@0.0.4 /usr/local/lib/node_modules/term.js

# npm install express@3.X.X -g
express@3.20.2 /usr/local/lib/node_modules/express
├── basic-auth@1.0.0
├── merge-descriptors@1.0.0
├── utils-merge@1.0.0
├── cookie-signature@1.0.6
├── methods@1.1.1
├── cookie@0.1.2
├── fresh@0.2.4
├── escape-html@1.0.1
├── range-parser@1.0.2
├── content-type@1.0.1
├── vary@1.0.0
├── parseurl@1.3.0
├── content-disposition@0.5.0
├── commander@2.6.0
├── depd@1.0.1
├── etag@1.5.1 (crc@3.2.1)
├── mkdirp@0.5.0 (minimist@0.0.8)
├── proxy-addr@1.0.7 (forwarded@0.1.0, ipaddr.js@0.1.9)
├── debug@2.1.3 (ms@0.7.0)
├── connect@2.29.1 (pause@0.0.1, response-time@2.3.0, vhost@3.0.0, on-headers@1.0.0, basic-auth-connect@1.0.0, bytes@1.0.0, cookie-parser@1.3.4, method-override@2.3.2, serve-static@1.9.2, connect-timeout@1.6.1, qs@2.4.1, serve-favicon@2.2.0, http-errors@1.3.1, finalhandler@0.3.4, morgan@1.5.2, type-is@1.6.1, errorhandler@1.3.5, body-parser@1.12.3, compression@1.4.3, serve-index@1.6.3, express-session@1.10.4, csurf@1.7.0, multiparty@3.3.2)
└── send@0.12.2 (destroy@1.0.3, ms@0.7.0, mime@1.3.4, on-finished@2.2.1)

# npm install socket.io -g
socket.io@1.3.5 /usr/local/lib/node_modules/socket.io
├── has-binary-data@0.1.3 (isarray@0.0.1)
├── debug@2.1.0 (ms@0.6.2)
├── socket.io-parser@2.2.4 (isarray@0.0.1, debug@0.7.4, component-emitter@1.1.2, benchmark@1.0.0, json3@3.2.6)
├── socket.io-adapter@0.3.1 (object-keys@1.0.1, debug@1.0.2, socket.io-parser@2.2.2)
├── socket.io-client@1.3.5 (to-array@0.1.3, indexof@0.0.1, component-bind@1.0.0, debug@0.7.4, backo2@1.0.2, object-component@0.0.3, component-emitter@1.1.2, has-binary@0.1.6, parseuri@0.0.2, engine.io-client@1.5.1)
└── engine.io@1.5.1 (base64id@0.1.0, debug@1.0.3, engine.io-parser@1.2.1, ws@0.5.0)

# npm install pty.js -g
pty.js@0.2.7-1 /usr/local/lib/node_modules/pty.js
├── extend@1.2.1
└── nan@1.7.0

# npm install forever -g
forever@0.14.1 /usr/local/lib/node_modules/forever
├── colors@0.6.2
├── timespan@2.3.0
├── optimist@0.6.1 (wordwrap@0.0.2, minimist@0.0.10)
├── nssocket@0.5.3 (eventemitter2@0.4.14, lazy@1.0.11)
├── winston@0.8.3 (cycle@1.0.3, stack-trace@0.0.9, eyes@0.1.8, isstream@0.1.2, async@0.2.10, pkginfo@0.3.0)
├── cliff@0.1.10 (eyes@0.1.8, colors@1.0.3)
├── nconf@0.6.9 (ini@1.3.3, async@0.2.9, optimist@0.6.0)
├── forever-monitor@1.5.2 (watch@0.13.0, minimatch@1.0.0, ps-tree@0.0.3, broadway@0.3.6)
├── flatiron@0.4.3 (optimist@0.6.0, director@1.2.7, broadway@0.3.6, prompt@0.2.14)
└── utile@0.2.1 (deep-equal@1.0.0, ncp@0.4.2, async@0.2.10, i@0.3.3, mkdirp@0.5.0, rimraf@2.3.3)
```

#### 리모트 웹 터미널 설치하기

```
# git clone https://github.com/chjj/term.js/
# cd term.js/example

폴더 내의 index.html 과 index.js 를 본 패키지의 termjs-custom 폴더 안의 index.html 과 index.js 파일로 교체합니다.

# forever index.js &
```

