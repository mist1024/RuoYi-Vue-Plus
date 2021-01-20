FROM openjdk:8-jre-alpine
#RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories
##安装字体
#RUN apk --no-cache add ttf-dejavu
COPY ruoyi-admin.jar /app/app.jar
ENTRYPOINT ["java","-Duser.timezone=GMT+08","-jar","/app/app.jar"]
