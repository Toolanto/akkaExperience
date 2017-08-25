FROM ubuntu:16.04
RUN apt-get update 
RUN apt-get install -y dos2unix nano sudo bash zookeeperd bash software-properties-common net-tools iputils-ping  telnet
# Replace the system() source because inside Docker we can't access /proc/kmsg.
# https://groups.google.com/forum/#!topic/docker-user/446yoB0Vx6w
#RUN     sed -i -E 's/^(\s*)system\(\);/\1unix-stream("\/dev\/log");/' /etc/syslog-ng/syslog-ng.conf
# Uncomment 'SYSLOGNG_OPTS="--no-caps"' to avoid the following warning:
# syslog-ng: Error setting capabilities, capability management disabled; error='Operation not permitted'
# http://serverfault.com/questions/524518/error-setting-capabilities-capability-management-disabled#
#RUN     sed -i 's/^#\(SYSLOGNG_OPTS="--no-caps"\)/\1/g' /etc/default/syslog-ng



RUN  echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | debconf-set-selections 
RUN  add-apt-repository -y ppa:webupd8team/java 
RUN  apt-get update 
RUN  apt-get install -y oracle-java8-installer 
RUN  rm -rf /var/lib/apt/lists/* 
RUN  rm -rf /var/cache/oracle-jdk8-installer

RUN mkdir /root/bin  
  
WORKDIR /root/bin
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle

ADD  http://it.apache.contactlab.it/kafka/0.10.1.1/kafka_2.10-0.10.1.1.tgz /root/bin/kafka.tgz
RUN  tar xf /root/bin/kafka.tgz -C /root/bin
RUN  ln -s /root/bin/kafka_2.10-0.10.1.1 /root/bin/kafka
RUN  echo "delete.topic.enable = true" >>  /root/bin/kafka/config/server.properties
RUN  touch /root/bin/kafka.sh
RUN  mkdir -p /root/log/kafka
RUN  echo "nohup kafka/bin/kafka-server-start.sh /root/bin/kafka/config/server.properties > /root/log/kafka/kafka.log 2>&1 &" > /root/bin/kafka.sh
RUN  chmod u+x /root/bin/kafka.sh
RUN  /root/bin/kafka.sh &

ADD  http://archive.apache.org/dist/hbase/1.1.2/hbase-1.1.2-bin.tar.gz /root/bin/hbase.tgz
RUN  tar xf /root/bin/hbase.tgz -C /root/bin
RUN  ln -s /root/bin/hbase-1.1.2 /root/bin/hbase
COPY  hbase-site.xml /root/bin/hbase/conf/hbase-site.xml
RUN  /root/bin/hbase/bin/start-hbase.sh

CMD service zookeeper start && /root/bin/kafka.sh && /root/bin/hbase/bin/hbase-daemon.sh --config "${HBASE_CONF_DIR}" start master && bash
