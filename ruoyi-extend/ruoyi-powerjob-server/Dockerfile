#FROM findepi/graalvm:java17-native
FROM ghcr.io/graalvm/graalvm-community:17.0.7

MAINTAINER Lion Li

RUN mkdir -p /ruoyi/powerjob/logs

WORKDIR /ruoyi/powerjob

EXPOSE 7700

ADD ./target/ruoyi-powerjob-server.jar ./app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
