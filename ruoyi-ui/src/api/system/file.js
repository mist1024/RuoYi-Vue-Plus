import request from "@/utils/request";

export function uploadFile(file) {

  let data = new FormData();
  // 创建一个表单数据
  data.append("file",file);

  // 把图片或文件添加到data
  return request({
    url: '/common/upload',
    method: 'post',
    data
  })
}
