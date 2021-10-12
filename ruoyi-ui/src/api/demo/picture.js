import request from '@/utils/request'

// 查询图片展示列表
export function listPicture(query) {
  return request({
    url: '/demo/picture/list',
    method: 'get',
    params: query
  })
}

// 查询没有被选中的图片，一次显示十条
export function listPictureWithCondition(query) {
  return request({
    url: '/demo/picture/picList',
    method: 'get',
    params: query
  })
}

// 提交到后端
export function commitPic(data) {
  return request({
    url: '/demo/picture/commitPic',
    method: 'post',
    data: data
  })
}

// 查询图片展示详细
export function getPicture(picId) {
  return request({
    url: '/demo/picture/' + picId,
    method: 'get'
  })
}

// 新增图片展示
export function addPicture(data) {
  return request({
    url: '/demo/picture',
    method: 'post',
    data: data
  })
}

// 修改图片展示
export function updatePicture(data) {
  return request({
    url: '/demo/picture',
    method: 'put',
    data: data
  })
}

// 删除图片展示
export function delPicture(picId) {
  return request({
    url: '/demo/picture/' + picId,
    method: 'delete'
  })
}

// 导出图片展示
export function exportPicture(query) {
  return request({
    url: '/demo/picture/export',
    method: 'get',
    params: query
  })
}
