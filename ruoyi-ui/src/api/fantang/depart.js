import request from '@/utils/request'

// 查询科室管理列表
export function listDepart(query) {
  return request({
    url: '/fantang/depart/list',
    method: 'get',
    params: query
  })
}

// 查询科室管理详细
export function getDepart(departId) {
  return request({
    url: '/fantang/depart/' + departId,
    method: 'get'
  })
}

// 新增科室管理
export function addDepart(data) {
  return request({
    url: '/fantang/depart',
    method: 'post',
    data: data
  })
}

// 修改科室管理
export function updateDepart(data) {
  return request({
    url: '/fantang/depart',
    method: 'put',
    data: data
  })
}

// 删除科室管理
export function delDepart(departId) {
  return request({
    url: '/fantang/depart/' + departId,
    method: 'delete'
  })
}

// 导出科室管理
export function exportDepart(query) {
  return request({
    url: '/fantang/depart/export',
    method: 'get',
    params: query
  })
}