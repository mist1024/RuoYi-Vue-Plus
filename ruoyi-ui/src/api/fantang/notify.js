import request from '@/utils/request'

// 查询系统信息列表
export function listNotify(query) {
  return request({
    url: '/fantang/notify/list',
    method: 'get',
    params: query
  })
}

// 查询系统信息详细
export function getNotify(id) {
  return request({
    url: '/fantang/notify/' + id,
    method: 'get'
  })
}

// 新增系统信息
export function addNotify(data) {
  return request({
    url: '/fantang/notify',
    method: 'post',
    data: data
  })
}

// 修改系统信息
export function updateNotify(data) {
  return request({
    url: '/fantang/notify',
    method: 'put',
    data: data
  })
}

// 删除系统信息
export function delNotify(id) {
  return request({
    url: '/fantang/notify/' + id,
    method: 'delete'
  })
}

// 导出系统信息
export function exportNotify(query) {
  return request({
    url: '/fantang/notify/export',
    method: 'get',
    params: query
  })
}