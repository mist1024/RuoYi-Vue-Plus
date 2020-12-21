import request from '@/utils/request'

// 查询补贴流水查看列表
export function listStaffSubsidy(query) {
  return request({
    url: '/fantang/staffSubsidy/list',
    method: 'get',
    params: query
  })
}

// 查询补贴流水查看详细
export function getStaffSubsidy(subsidyId) {
  return request({
    url: '/fantang/staffSubsidy/' + subsidyId,
    method: 'get'
  })
}

// 新增补贴流水查看
export function addStaffSubsidy(data) {
  return request({
    url: '/fantang/staffSubsidy',
    method: 'post',
    data: data
  })
}

// 修改补贴流水查看
export function updateStaffSubsidy(data) {
  return request({
    url: '/fantang/staffSubsidy',
    method: 'put',
    data: data
  })
}

// 发放员工补贴
export function submitGiveOutSubsidy(data) {
  return request({
    url: '/fantang/staffSubsidy/submitGiveOutSubsidy',
    method: 'post',
    data: data
  })
}

// 删除补贴流水查看
export function delStaffSubsidy(subsidyId) {
  return request({
    url: '/fantang/staffSubsidy/' + subsidyId,
    method: 'delete'
  })
}

// 导出补贴流水查看
export function exportStaffSubsidy(query) {
  return request({
    url: '/fantang/staffSubsidy/export',
    method: 'get',
    params: query
  })
}
