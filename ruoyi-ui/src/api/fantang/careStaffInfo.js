import request from '@/utils/request'

// 查询护工信息列表
export function listCareStaffInfo(query) {
  return request({
    url: '/fantang/careStaffInfo/list',
    method: 'get',
    params: query
  })
}

// 查询护工信息详细
export function getCareStaffInfo(careStaffId) {
  return request({
    url: '/fantang/careStaffInfo/' + careStaffId,
    method: 'get'
  })
}

// 新增护工信息
export function addCareStaffInfo(data) {
  return request({
    url: '/fantang/careStaffInfo',
    method: 'post',
    data: data
  })
}

// 修改护工信息
export function updateCareStaffInfo(data) {
  return request({
    url: '/fantang/careStaffInfo',
    method: 'put',
    data: data
  })
}

// 删除护工信息
export function delCareStaffInfo(careStaffId) {
  return request({
    url: '/fantang/careStaffInfo/' + careStaffId,
    method: 'delete'
  })
}

// 导出护工信息
export function exportCareStaffInfo(query) {
  return request({
    url: '/fantang/careStaffInfo/export',
    method: 'get',
    params: query
  })
}