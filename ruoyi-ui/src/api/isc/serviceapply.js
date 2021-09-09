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

// 新增应用服务申请信息
export function addServiceapply(data) {
  return request({
    url: '/isc/serviceapply',
    method: 'post',
    data: data
  })
}

// 修改应用服务申请信息
export function updateServiceapply(data) {
  return request({
    url: '/isc/serviceapply',
    method: 'put',
    data: data
  })
}

// 删除应用服务申请信息
export function delServiceapply(applyId) {
  return request({
    url: '/isc/serviceapply/' + applyId,
    method: 'delete'
  })
}
