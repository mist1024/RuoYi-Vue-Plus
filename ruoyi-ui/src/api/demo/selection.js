import request from '@/utils/request'

// 查询图片选择参数列表
export function listSelection(query) {
  return request({
    url: '/demo/selection/list',
    method: 'get',
    params: query
  })
}

// 查询图片选择参数详细
export function getSelection(sid) {
  return request({
    url: '/demo/selection/' + sid,
    method: 'get'
  })
}

// 新增图片选择参数
export function addSelection(data) {
  return request({
    url: '/demo/selection',
    method: 'post',
    data: data
  })
}

// 修改图片选择参数
export function updateSelection(data) {
  return request({
    url: '/demo/selection',
    method: 'put',
    data: data
  })
}

// 删除图片选择参数
export function delSelection(sid) {
  return request({
    url: '/demo/selection/' + sid,
    method: 'delete'
  })
}

// 导出图片选择参数
export function exportSelection(query) {
  return request({
    url: '/demo/selection/export',
    method: 'get',
    params: query
  })
}