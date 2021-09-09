import request from '@/utils/request'

// 查询应用服务申请信息列表
export function listServiceapply(query) {
  return request({
    url: '/isc/serviceapply/list',
    method: 'get',
    params: query
  })
}

// 查询应用服务申请信息详细
export function getServiceapply(applyId) {
  return request({
    url: '/isc/serviceapply/' + applyId,
    method: 'get'
  })
}

// 应用服务申请信息审核
export function auditServiceapply(data) {
  return request({
    url: '/isc/serviceapply/audit',
    method: 'put',
    data: data
  })
}
