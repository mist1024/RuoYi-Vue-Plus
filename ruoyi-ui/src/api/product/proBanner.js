import request from '@/utils/request'

// 查询banner管理列表
export function listBanner(query) {
  return request({
    url: '/product/proBanner/list',
    method: 'get',
    params: query
  })
}

// 查询banner管理详细
export function getBanner(id) {
  return request({
    url: '/product/proBanner/' + id,
    method: 'get'
  })
}

// 新增banner管理
export function addBanner(data) {
  return request({
    url: '/product/proBanner',
    method: 'post',
    data: data
  })
}

// 修改banner管理
export function updateBanner(data) {
  return request({
    url: '/product/proBanner',
    method: 'put',
    data: data
  })
}

// 删除banner管理
export function delBanner(id) {
  return request({
    url: '/product/proBanner' + id,
    method: 'delete'
  })
}

// 导出banner管理
export function exportBanner(query) {
  return request({
    url: '/product/proBanner/export',
    method: 'get',
    params: query
  })
}
