#!/bin/bash

path="/data/mall/"

echo "==========打包========="
cd ruoyi-ui
npm run build:prod
echo "==========删除旧静态页面目录========="
ssh -t root@62.234.115.161 rm -rf $path
echo "==========创建目录========="
ssh -t root@62.234.115.161 mkdir $path
echo "==========上传服务器========="
scp -r dist/* root@62.234.115.161:$path
echo "==========部署完成========="
