import request from '@/utils/request'

// 查询商品规格列表
export function listProItemSpecification(query) {
  return request({
    url: '/product/proItemSpecification/list',
    method: 'get',
    params: query
  })
}

// 查询商品规格详细
export function getProItemSpecification(id) {
  return request({
    url: '/product/proItemSpecification/' + id,
    method: 'get'
  })
}

// 新增商品规格
export function addProItemSpecification(data) {
  return request({
    url: '/product/proItemSpecification',
    method: 'post',
    data: data
  })
}

// 修改商品规格
export function updateProItemSpecification(data) {
  return request({
    url: '/product/proItemSpecification',
    method: 'put',
    data: data
  })
}

// 删除商品规格
export function delProItemSpecification(id) {
  return request({
    url: '/product/proItemSpecification/' + id,
    method: 'delete'
  })
}

// 导出商品规格
export function exportProItemSpecification(query) {
  return request({
    url: '/product/proItemSpecification/export',
    method: 'get',
    params: query
  })
}