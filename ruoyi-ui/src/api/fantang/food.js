import request from '@/utils/request'

// 查询食品管理列表
export function listFood(query) {
  return request({
    url: '/fantang/food/list',
    method: 'get',
    params: query
  })
}

// 查询食品管理详细
export function getFood(foodId) {
  return request({
    url: '/fantang/food/' + foodId,
    method: 'get'
  })
}

// 新增食品管理
export function addFood(data) {
  return request({
    url: '/fantang/food',
    method: 'post',
    data: data
  })
}

// 修改食品管理
export function updateFood(data) {
  return request({
    url: '/fantang/food',
    method: 'put',
    data: data
  })
}

// 删除食品管理
export function delFood(foodId) {
  return request({
    url: '/fantang/food/' + foodId,
    method: 'delete'
  })
}

// 导出食品管理
export function exportFood(query) {
  return request({
    url: '/fantang/food/export',
    method: 'get',
    params: query
  })
}