import request from '@/utils/request'

// 查询特殊用餐管理列表
export function listDemand(query) {
  return request({
    url: '/fantang/demand/list',
    method: 'get',
    params: query
  })
}

// 查询特殊用餐管理详细
export function getDemand(id) {
  return request({
    url: '/fantang/demand/' + id,
    method: 'get'
  })
}

// 新增特殊用餐管理
export function addDemand(data) {
  return request({
    url: '/fantang/demand',
    method: 'post',
    data: data
  })
}

// 修改特殊用餐管理
export function updateDemand(data) {
  return request({
    url: '/fantang/demand',
    method: 'put',
    data: data
  })
}

// 删除特殊用餐管理
export function delDemand(id) {
  return request({
    url: '/fantang/demand/' + id,
    method: 'delete'
  })
}

// 导出特殊用餐管理
export function exportDemand(query) {
  return request({
    url: '/fantang/demand/export',
    method: 'get',
    params: query
  })
}