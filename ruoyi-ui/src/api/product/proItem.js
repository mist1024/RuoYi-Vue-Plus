import request from '@/utils/request'

// 查询商品详情列表
export function listProItem(query) {
  return request({
    url: '/product/proItem/list',
    method: 'get',
    params: query
  })
}

// 查询商品详情详细
export function getProItem(id) {
  return request({
    url: '/product/proItem/' + id,
    method: 'get'
  })
}

// 新增商品详情
export function addProItem(data) {
  return request({
    url: '/product/proItem',
    method: 'post',
    data: data
  })
}

// 修改商品详情
export function updateProItem(data) {
  return request({
    url: '/product/proItem',
    method: 'put',
    data: data
  })
}

// 删除商品详情
export function delProItem(id) {
  return request({
    url: '/product/proItem/' + id,
    method: 'delete'
  })
}

// 导出商品详情
export function exportProItem(query) {
  return request({
    url: '/product/proItem/export',
    method: 'get',
    params: query
  })
}

// 上传图片通用方法
export function uploadFile(data) {
  return request({
    url: '/common/upload',
    method: 'post',
    data: data
  })
}
