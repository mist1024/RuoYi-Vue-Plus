FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER Lion Li

RUN mkdir -p /wild/monitor
RUN mkdir -p /wild/monitor/logs

WORKDIR /wild/monitor

EXPOSE 9090

ADD ./target/wild-monitor-admin.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
