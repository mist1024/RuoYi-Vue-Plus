import request from '@/utils/request'

// 查询每周菜单列表
export function listWeekMenu(query) {
  return request({
    url: '/fantang/weekMenu/list',
    method: 'get',
    params: query
  })
}

// 查询每周菜单详细
export function getWeekMenu(id) {
  return request({
    url: '/fantang/weekMenu/' + id,
    method: 'get'
  })
}

// 新增每周菜单
export function addWeekMenu(data) {
  return request({
    url: '/fantang/weekMenu',
    method: 'post',
    data: data
  })
}

// 修改每周菜单
export function updateWeekMenu(data) {
  return request({
    url: '/fantang/weekMenu',
    method: 'put',
    data: data
  })
}

// 删除每周菜单
export function delWeekMenu(id) {
  return request({
    url: '/fantang/weekMenu/' + id,
    method: 'delete'
  })
}

// 导出每周菜单
export function exportWeekMenu(query) {
  return request({
    url: '/fantang/weekMenu/export',
    method: 'get',
    params: query
  })
}