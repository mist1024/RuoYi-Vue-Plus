import request from '@/utils/request'

// 查询订单管理列表
export function listOrder(query) {
  return request({
    url: '/fantang/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单管理详细
export function getOrder(orderId) {
  return request({
    url: '/fantang/order/' + orderId,
    method: 'get'
  })
}

// 新增订单管理
export function addOrder(data) {
  return request({
    url: '/fantang/order',
    method: 'post',
    data: data
  })
}

// 修改订单管理
export function updateOrder(data) {
  return request({
    url: '/fantang/order',
    method: 'put',
    data: data
  })
}

// 删除订单管理
export function delOrder(orderId) {
  return request({
    url: '/fantang/order/' + orderId,
    method: 'delete'
  })
}

// 导出订单管理
export function exportOrder(query) {
  return request({
    url: '/fantang/order/export',
    method: 'get',
    params: query
  })
}

// 日报餐信息
export function getStatisGetOrderOfDay() {
  return request({
    url: '/fantang/order/getStatisGetOrderOfDay',
    method: 'get',
    params: query
  })
}

// 周报餐信息
export function getStatisGetOrderOfWeek() {
  return request({
    url: '/fantang/order/getStatisGetOrderOfWeek',
    method: 'get',
    params: query
  })
}

// 月报餐信息
export function getStatisGetOrderOfMonth() {
  return request({
    url: '/fantang/order/getStatisGetOrderOfMonth',
    method: 'get',
    params: query
  })
}
