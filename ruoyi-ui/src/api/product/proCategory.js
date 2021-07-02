import request from '@/utils/request'

// 查询商品服务类别列表
export function listProCategory(query) {
  return request({
    url: '/product/proCategory/list',
    method: 'get',
    params: query
  })
}

// 查询商品服务类别详细
export function getProCategory(id) {
  return request({
    url: '/product/proCategory/' + id,
    method: 'get'
  })
}

// 新增商品服务类别
export function addProCategory(data) {
  return request({
    url: '/product/proCategory',
    method: 'post',
    data: data
  })
}

// 修改商品服务类别
export function updateProCategory(data) {
  return request({
    url: '/product/proCategory',
    method: 'put',
    data: data
  })
}

// 删除商品服务类别
export function delProCategory(id) {
  return request({
    url: '/product/proCategory/' + id,
    method: 'delete'
  })
}

// 导出商品服务类别
export function exportProCategory(query) {
  return request({
    url: '/product/proCategory/export',
    method: 'get',
    params: query
  })
}

export function listCategoriesWithoutPage(query) {
  return request({
    url: '/product/proCategory/listCategoriesWithoutPage',
    method: 'get',
    params: query
  })
}
