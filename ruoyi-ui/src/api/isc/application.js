import request from '@/utils/request'

// 查询应用信息列表
export function listApplication(query) {
  return request({
    url: '/isc/application/list',
    method: 'get',
    params: query
  })
}

// 查询应用信息详细
export function getApplication(applicationId) {
  return request({
    url: '/isc/application/' + applicationId,
    method: 'get'
  })
}

// 新增应用信息
export function addApplication(data) {
  return request({
    url: '/isc/application',
    method: 'post',
    data: data
  })
}

// 修改应用信息
export function updateApplication(data) {
  return request({
    url: '/isc/application',
    method: 'put',
    data: data
  })
}

// 删除应用信息
export function delApplication(applicationId) {
  return request({
    url: '/isc/application/' + applicationId,
    method: 'delete'
  })
}
