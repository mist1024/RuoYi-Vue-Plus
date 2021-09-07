import request from '@/utils/request'

// 查询服务信息列表
export function listService(query) {
  return request({
    url: '/isc/service/list',
    method: 'get',
    params: query
  })
}

// 查询服务信息详细
export function getService(serviceId) {
  return request({
    url: '/isc/service/' + serviceId,
    method: 'get'
  })
}

// 新增服务信息
export function addService(data) {
  return request({
    url: '/isc/service',
    method: 'post',
    data: data
  })
}

// 修改服务信息
export function updateService(data) {
  return request({
    url: '/isc/service',
    method: 'put',
    data: data
  })
}

// 删除服务信息
export function delService(serviceId) {
  return request({
    url: '/isc/service/' + serviceId,
    method: 'delete'
  })
}
