#!/bin/bash

imageName="wine"

echo "==========打包========="
mvn clean package -Dmaven.test.skip=true
echo "==========上传服务器========="
scp ruoyi-admin/target/ruoyi-admin.jar root@62.234.123.172:/root/wine/
scp Dockerfile root@62.234.123.172:/root/wine/
echo "==========远程执行========="
ssh root@62.234.123.172 > /dev/null 2>&1 << eeooff
cd /root/wine
docker build -t $imageName .
docker stop $imageName
docker rm $imageName
docker run -d --name $imageName -p 18989:18989 $imageName
exit
eeooff
echo "==========部署完成========="
