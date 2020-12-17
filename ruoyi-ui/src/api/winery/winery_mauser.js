import request from '@/utils/request'

// 查询小程序用户列表
export function listWinery_mauser(query) {
  return request({
    url: '/winery/winery_mauser/list',
    method: 'get',
    params: query
  })
}

// 查询小程序用户详细
export function getWinery_mauser(openId) {
  return request({
    url: '/winery/winery_mauser/' + openId,
    method: 'get'
  })
}

// 新增小程序用户
export function addWinery_mauser(data) {
  return request({
    url: '/winery/winery_mauser',
    method: 'post',
    data: data
  })
}

// 修改小程序用户
export function updateWinery_mauser(data) {
  return request({
    url: '/winery/winery_mauser',
    method: 'put',
    data: data
  })
}

// 删除小程序用户
export function delWinery_mauser(openId) {
  return request({
    url: '/winery/winery_mauser/' + openId,
    method: 'delete'
  })
}

// 导出小程序用户
export function exportWinery_mauser(query) {
  return request({
    url: '/winery/winery_mauser/export',
    method: 'get',
    params: query
  })
}