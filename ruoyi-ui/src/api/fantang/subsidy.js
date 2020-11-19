import request from '@/utils/request'

// 查询补贴管理列表
export function listSubsidy(query) {
  return request({
    url: '/fantang/subsidy/list',
    method: 'get',
    params: query
  })
}

// 查询补贴管理详细
export function getSubsidy(subsidyId) {
  return request({
    url: '/fantang/subsidy/' + subsidyId,
    method: 'get'
  })
}

// 新增补贴管理
export function addSubsidy(data) {
  return request({
    url: '/fantang/subsidy',
    method: 'post',
    data: data
  })
}

// 修改补贴管理
export function updateSubsidy(data) {
  return request({
    url: '/fantang/subsidy',
    method: 'put',
    data: data
  })
}

// 删除补贴管理
export function delSubsidy(subsidyId) {
  return request({
    url: '/fantang/subsidy/' + subsidyId,
    method: 'delete'
  })
}

// 导出补贴管理
export function exportSubsidy(query) {
  return request({
    url: '/fantang/subsidy/export',
    method: 'get',
    params: query
  })
}