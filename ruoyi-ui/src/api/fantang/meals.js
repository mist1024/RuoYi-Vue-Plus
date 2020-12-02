import request from '@/utils/request'

// 查询所有报餐管理列表
export function listAll(query) {
  return request({
    url: '/fantang/meals/listAll',
    method: 'get',
    params: query
  })
}
// 查询未结算报餐记录
export function listNoPay(query) {
  return request({
    url: '/fantang/meals/listNoPay',
    method: 'get',
    params: query
  })
}

// 查询已结算报餐记录
export function listPayoff(query) {
  return request({
    url: '/fantang/meals/listPayoff',
    method: 'get',
    params: query
  })
}


// 查询报餐管理列表
export function listMeals(query) {
  return request({
    url: '/fantang/meals/list',
    method: 'get',
    params: query
  })
}

// 查询报餐管理详细
export function getMeals(id) {
  return request({
    url: '/fantang/meals/' + id,
    method: 'get'
  })
}

// 新增报餐管理
export function addMeals(data) {
  return request({
    url: '/fantang/meals',
    method: 'post',
    data: data
  })
}

// 修改报餐管理
export function updateMeals(data) {
  return request({
    url: '/fantang/meals',
    method: 'put',
    data: data
  })
}

// 删除报餐管理
export function delMeals(id) {
  return request({
    url: '/fantang/meals/' + id,
    method: 'delete'
  })
}

// 导出报餐管理
export function exportMeals(query) {
  return request({
    url: '/fantang/meals/export',
    method: 'get',
    params: query
  })
}


// 获取最后结算日期
export function getLastSettlementDate(patientId) {
  return request({
    url: '/fantang/meals/getLastSettlementDate/' + patientId,
    method: 'get',
  })
}


// 计算两个日期之间的结算数据
export function countBillingBetween(query) {
  return request({
    url: '/fantang/meals/countBillingBetween',
    method: 'get',
    params: query
  })
}
