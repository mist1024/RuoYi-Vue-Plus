import request from '@/utils/request'

// 查询食品安全详情列表
export function listFood_safety(query) {
  return request({
    url: '/winery/food_safety/list',
    method: 'get',
    params: query
  })
}

// 查询食品安全详情详细
export function getFood_safety(id) {
  return request({
    url: '/winery/food_safety/' + id,
    method: 'get'
  })
}

// 新增食品安全详情
export function addFood_safety(data) {
  return request({
    url: '/winery/food_safety',
    method: 'post',
    data: data
  })
}

// 修改食品安全详情
export function updateFood_safety(data) {
  return request({
    url: '/winery/food_safety',
    method: 'put',
    data: data
  })
}

// 删除食品安全详情
export function delFood_safety(id) {
  return request({
    url: '/winery/food_safety/' + id,
    method: 'delete'
  })
}

// 导出食品安全详情
export function exportFood_safety(query) {
  return request({
    url: '/winery/food_safety/export',
    method: 'get',
    params: query
  })
}