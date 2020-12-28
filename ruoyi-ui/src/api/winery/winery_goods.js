import request from '@/utils/request'

// 查询商品信息列表
export function listWinery_goods(query) {
  return request({
    url: '/winery/winery_goods/list',
    method: 'get',
    params: query
  })
}

// 查询商品信息详细
export function getWinery_goods(id) {
  return request({
    url: '/winery/winery_goods/' + id,
    method: 'get'
  })
}

// 新增商品信息
export function addWinery_goods(data) {
  return request({
    url: '/winery/winery_goods',
    method: 'post',
    data: data
  })
}

// 修改商品信息
export function updateWinery_goods(data) {
  return request({
    url: '/winery/winery_goods',
    method: 'put',
    data: data
  })
}

// 删除商品信息
export function delWinery_goods(id) {
  return request({
    url: '/winery/winery_goods/' + id,
    method: 'delete'
  })
}

// 导出商品信息
export function exportWinery_goods(query) {
  return request({
    url: '/winery/winery_goods/export',
    method: 'get',
    params: query
  })
}