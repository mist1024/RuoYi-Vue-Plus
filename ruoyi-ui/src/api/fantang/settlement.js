import request from '@/utils/request'

// 查询结算管理列表
export function listSettlement(query) {
  return request({
    url: '/fantang/settlement/list',
    method: 'get',
    params: query
  })
}

// 查询结算管理详细
export function getSettlement(settleId) {
  return request({
    url: '/fantang/settlement/' + settleId,
    method: 'get'
  })
}

// 新增结算管理
export function addSettlement(data) {
  return request({
    url: '/fantang/settlement',
    method: 'post',
    data: data
  })
}

// 修改结算管理
export function updateSettlement(data) {
  return request({
    url: '/fantang/settlement',
    method: 'put',
    data: data
  })
}

// 删除结算管理
export function delSettlement(settleId) {
  return request({
    url: '/fantang/settlement/' + settleId,
    method: 'delete'
  })
}

// 导出结算管理
export function exportSettlement(query) {
  return request({
    url: '/fantang/settlement/export',
    method: 'get',
    params: query
  })
}