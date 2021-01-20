#!/bin/bash

imageName="mall"

echo "==========打包========="
mvn clean package -Dmaven.test.skip=true
echo "==========上传服务器========="
scp ruoyi-admin/target/ruoyi-admin.jar root@192.144.217.65:/root/mall/
scp Dockerfile root@192.144.217.65:/root/mall/
echo "==========远程执行========="
ssh root@192.144.217.65 > /dev/null 2>&1 << eeooff
cd /root/mall
docker build -t $imageName .
docker stop $imageName
docker rm $imageName
docker run -d --name $imageName -p 18989:18989 $imageName
exit
eeooff
echo "==========部署完成========="
