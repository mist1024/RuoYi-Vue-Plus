import request from '@/utils/request'

// 查询应用服务列表
export function listAppservice(query) {
  return request({
    url: '/isc/appservice/list',
    method: 'get',
    params: query
  })
}

// 查询应用服务详细
export function getAppservice(serviceAppId) {
  return request({
    url: '/isc/appservice/' + serviceAppId,
    method: 'get'
  })
}

// 新增应用服务
export function addAppservice(data) {
  return request({
    url: '/isc/appservice',
    method: 'post',
    data: data
  })
}

// 修改应用服务
export function updateAppservice(data) {
  return request({
    url: '/isc/appservice',
    method: 'put',
    data: data
  })
}

// 删除应用服务
export function delAppservice(serviceAppId) {
  return request({
    url: '/isc/appservice/' + serviceAppId,
    method: 'delete'
  })
}
