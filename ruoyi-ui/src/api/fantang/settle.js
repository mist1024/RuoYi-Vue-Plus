import request from '@/utils/request'

// 查询结算报列表
export function listSettle(query) {
  return request({
    url: '/fantang/settle/list',
    method: 'get',
    params: query
  })
}

// 查询结算报详细
export function getSettle(settleId) {
  return request({
    url: '/fantang/settle/' + settleId,
    method: 'get'
  })
}

// 新增结算报
export function addSettle(data) {
  return request({
    url: '/fantang/settle',
    method: 'post',
    data: data
  })
}

// 修改结算报
export function updateSettle(data) {
  return request({
    url: '/fantang/settle',
    method: 'put',
    data: data
  })
}

// 删除结算报
export function delSettle(settleId) {
  return request({
    url: '/fantang/settle/' + settleId,
    method: 'delete'
  })
}

// 导出结算报
export function exportSettle(query) {
  return request({
    url: '/fantang/settle/export',
    method: 'get',
    params: query
  })
}