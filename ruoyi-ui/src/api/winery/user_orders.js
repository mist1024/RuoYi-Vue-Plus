import request from '@/utils/request'

// 查询客户订单列表
export function listUser_orders(query) {
  return request({
    url: '/winery/user_orders/list',
    method: 'get',
    params: query
  })
}

// 查询客户订单详细
export function getUser_orders(id) {
  return request({
    url: '/winery/user_orders/' + id,
    method: 'get'
  })
}

// 新增客户订单
export function addUser_orders(data) {
  return request({
    url: '/winery/user_orders',
    method: 'post',
    data: data
  })
}

// 修改客户订单
export function updateUser_orders(data) {
  return request({
    url: '/winery/user_orders',
    method: 'put',
    data: data
  })
}

// 删除客户订单
export function delUser_orders(id) {
  return request({
    url: '/winery/user_orders/' + id,
    method: 'delete'
  })
}

// 导出客户订单
export function exportUser_orders(query) {
  return request({
    url: '/winery/user_orders/export',
    method: 'get',
    params: query
  })
}