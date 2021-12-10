FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER Lion Li

RUN mkdir -p /wild/server
RUN mkdir -p /wild/server/logs
RUN mkdir -p /wild/server/temp

WORKDIR /wild/server

EXPOSE 8080

ADD ./target/wild-admin.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
