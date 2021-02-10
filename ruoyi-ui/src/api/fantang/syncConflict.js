import request from '@/utils/request'

// 查询同步冲突列表
export function listSyncConflict(query) {
  return request({
    url: '/fantang/syncConflict/list',
    method: 'get',
    params: query
  })
}

// 查询同步冲突详细
export function getSyncConflict(id) {
  return request({
    url: '/fantang/syncConflict/' + id,
    method: 'get'
  })
}

// 新增同步冲突
export function addSyncConflict(data) {
  return request({
    url: '/fantang/syncConflict',
    method: 'post',
    data: data
  })
}

// 修改同步冲突
export function updateSyncConflict(data) {
  return request({
    url: '/fantang/syncConflict',
    method: 'put',
    data: data
  })
}

// 删除同步冲突
export function delSyncConflict(id) {
  return request({
    url: '/fantang/syncConflict/' + id,
    method: 'delete'
  })
}

// 导出同步冲突
export function exportSyncConflict(query) {
  return request({
    url: '/fantang/syncConflict/export',
    method: 'get',
    params: query
  })
}