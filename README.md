--------------------------------------------------------------------------------------------------------

	Dopo il clone del progetto eseguire questo comando per aggiornare i sottomoduli

	git submodule update --init --recursive

--------------------------------------------------------------------------------------------------------

	INSTALLAZIONE KAFKA


	install zookeeper

	$> sudo apt-get install zookeeperd

	test it with

	$> telnet localhost 2181

	$> wget "http://it.apache.contactlab.it/kafka/0.10.0.1/kafka_2.10-0.10.0.1.tgz" -O ~/bin/kafka.tgz

	extract into ~/bin

	make link ~/bin/kafka to ~/bin/kafka_2.10-0.10.0.1

	configure kafka server

	$> echo "delete.topic.enable = true" >> ~/kafka/config/server.properties

	make startup script

	$> touch ~/bin/kafka.sh && echo "nohup ~/bin/kafka/bin/kafka-server-start.sh ~/bin/kafka/config/server.properties > ~/bin/kafka/kafka.log 2>&1 &" > ~/bin/kafka.sh

	$> chmod u+x ~/bin/kafka.sh

	start it using

	$> ~/bin/kafka.sh && tail -F ~/bin/kafka/kafka.log

--------------------------------------------------------------------------------------------------------

	INSTALLAZIONE HBASE IN LOCALE

	[see http://hbase.apache.org/book.html#quickstart for more details]

	$> cd bin

	download
	$> wget http://it.apache.contactlab.it/hbase/stable/hbase-1.2.3-bin.tar.gz

	extract
	$> tar xzf hbase-1.2.3-bin.tar.gz

	make link
	$> ln -s hbase-1.2.3 hbase

	edit ed paste
	$> nano conf/hbase-site.xml

		<?xml version="1.0"?>
		<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
		<configuration>
		  <property>
		    <name>hbase.rootdir</name>
		    <value>file:///tmp/hbase-${user.name}/hbase</value>
		  </property>
		  <property>
		    <name>hbase.zookeeper.property.dataDir</name>
		    <value>/tmp/zookeeper</value>
		  </property>
		  <property>
		    <name>hbase.zookeeper.property.clientPort</name>
		    <value>2182</value>
		    <description>Property from ZooKeeper's config zoo.cfg.
		    The port at which the clients will connect.
		    </description>
		  </property>
		  <property>
		    <name>hbase.zookeeper.quorum</name>
		    <value>localhost</value>
		  </property>
		</configuration>

	start hbase
	$> ./bin/start-hbase.sh


--------------------------------------------------------------------------------------------------------

	HOW TO RUN FROM IDE

	add this in run configuration for staging environment

    -Dakka.remote.netty.tcp.port=2600
    -Dengine.config.file="../configuration/staging/smartsend.conf"
    -Djava.security.krb5.conf="../configuration/staging/krb5.conf"
    -Dlogback.configurationFile="../configuration/staging/logback.xml"
    -Dconfig.file="../configuration/staging/application.conf"
    -Djava.security.auth.login.config="../configuration/staging/kafka-jaas.conf"

	in development without docker

    -Dakka.remote.netty.tcp.port=2600
    -Dengine.config.file="configuration/development/smartsend.conf"
    -Dlogback.configurationFile="configuration/development/logback.xml"
    -Dconfig.file="configuration/development/application.conf"


	add this in run configuration for docker environment

    -Dakka.remote.netty.tcp.port=2600
    -Dengine.config.file="configuration/development/smartsend-docker-VMTA.conf"
    -Dlogback.configurationFile="configuration/development/logback.xml"
    -Dconfig.file="configuration/development/application.conf

