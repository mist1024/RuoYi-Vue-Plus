#!/bin/bash

echo "==========打包========="
cd ruoyi-ui
#npm run build:prod
echo "==========上传服务器========="
scp -r dist/* root@62.234.115.161:/data/mall/
echo "==========部署完成========="
