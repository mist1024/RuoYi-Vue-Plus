FROM java:8-alpine
WORKDIR /app
COPY ruoyi-admin.jar /app/app.jar
ENTRYPOINT ["java","-Duser.timezone=Asia/Shanghai -Xms512M -Xmx512M -XX:PermSize=256M -XX:MaxPermSize=512M -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDateStamps  -XX:+PrintGCDetails -XX:NewRatio=1 -XX:SurvivorRatio=30 -XX:+UseParallelGC -XX:+UseParallelOldGC","-jar","/app/app.jar"]

