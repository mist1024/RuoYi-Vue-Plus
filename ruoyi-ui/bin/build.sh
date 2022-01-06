#!/bin/bash
cd .. || exit
workdir=$(cd $(dirname $0) || exit; pwd)
echo "current dir:$workdir"

npm install --registry=https://registry.npm.taobao.org
npm run build:prod

if [ ! -d "dist/" ];then
  echo "npm build failed"; exit 1;
else
  echo "npm build success"
fi

cd /docker/nginx || exit

if [ ! -d "html/" ];then
  mkdir html
else
  current_time=$(date "+%Y%m%d%H%M%S")
  dir="html_bak_${current_time}"
  mkdir "$dir"
  mv /docker/nginx/html/* "$dir"
  echo "old file backup to:$dir"
fi

cd "$workdir" || exit
cp -r dist/* /docker/nginx/html/
echo "DONE"
