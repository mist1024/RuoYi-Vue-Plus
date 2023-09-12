FROM mysql/mysql-server:8.0.32

MAINTAINER insist

ENV TZ=Asia/Shanghai

RUN ln -sf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY 1schema.sql /docker-entrypoint-initdb.d

COPY 2ry_vue_5.X.sql /docker-entrypoint-initdb.d

COPY 3powerjob.sql /docker-entrypoint-initdb.d

COPY 4test.sql /docker-entrypoint-initdb.d
