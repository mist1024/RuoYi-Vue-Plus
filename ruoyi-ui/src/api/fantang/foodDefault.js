import request from '@/utils/request'

// 查询默认报餐管理列表
export function listFoodDefault(query) {
  return request({
    url: '/fantang/foodDefault/list',
    method: 'get',
    params: query
  })
}

// 查询默认报餐管理详细
export function getFoodDefault(id) {
  return request({
    url: '/fantang/foodDefault/' + id,
    method: 'get'
  })
}

// 新增默认报餐管理
export function addFoodDefault(data) {
  return request({
    url: '/fantang/foodDefault',
    method: 'post',
    data: data
  })
}

// 修改默认报餐管理
export function updateFoodDefault(data) {
  return request({
    url: '/fantang/foodDefault',
    method: 'put',
    data: data
  })
}

// 删除默认报餐管理
export function delFoodDefault(id) {
  return request({
    url: '/fantang/foodDefault/' + id,
    method: 'delete'
  })
}

// 导出默认报餐管理
export function exportFoodDefault(query) {
  return request({
    url: '/fantang/foodDefault/export',
    method: 'get',
    params: query
  })
}