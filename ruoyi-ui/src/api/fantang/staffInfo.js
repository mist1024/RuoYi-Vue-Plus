import request from '@/utils/request'

// 查询员工管理列表
export function listStaffInfo(query) {
  return request({
    url: '/fantang/staffInfo/staffList',
    method: 'get',
    params: query
  })
}

export function staffListWithDepart(query) {
  return request({
    url: '/fantang/staffInfo/staffListWithDepart',
    method: 'get',
    params : query
  })
}

// 查询护工管理列表
export function careStaffList(query) {
  return request({
    url: '/fantang/staffInfo/careStaffList',
    method: 'get',
    params: query
  })
}

// 查询员工管理详细
export function getStaffInfo(staffId) {
  return request({
    url: '/fantang/staffInfo/' + staffId,
    method: 'get'
  })
}

export function getNursingInfo(nursingId) {
  return request({
    url: '/fantang/staffInfo/nursing/' + nursingId,
    method: 'get'
  })
}


// 新增员工管理
export function addStaffInfo(data) {
  return request({
    url: '/fantang/staffInfo',
    method: 'post',
    data: data
  })
}

// 修改员工管理
export function updateStaffInfo(data) {
  return request({
    url: '/fantang/staffInfo',
    method: 'put',
    data: data
  })
}

// 删除员工管理
export function delStaffInfo(staffId) {
  return request({
    url: '/fantang/staffInfo/' + staffId,
    method: 'delete'
  })
}

// 导出员工管理
export function exportStaffInfo(query) {
  return request({
    url: '/fantang/staffInfo/export',
    method: 'get',
    params: query
  })
}

export function updateNursingInfo(data) {
  return request({
    url: '/fantang/staffInfo/nursing',
    method: 'put',
    data: data
  })
}
