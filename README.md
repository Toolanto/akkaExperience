<<<<<<< HEAD
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

=======
# akkaExperience
Akka example
>>>>>>> 393207afe9dcf9dc4240eccd728d6432c271ee33
