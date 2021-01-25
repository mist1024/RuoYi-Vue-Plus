import request from '@/utils/request'

// 查询app评论列表
export function listDiscuss(query) {
  return request({
    url: '/app/discuss/list',
    method: 'get',
    params: query
  })
}

// 查询app评论详细
export function getDiscuss(id) {
  return request({
    url: '/app/discuss/' + id,
    method: 'get'
  })
}

// 新增app评论
export function addDiscuss(data) {
  return request({
    url: '/app/discuss',
    method: 'post',
    data: data
  })
}

// 修改app评论
export function updateDiscuss(data) {
  return request({
    url: '/app/discuss',
    method: 'put',
    data: data
  })
}

// 删除app评论
export function delDiscuss(id) {
  return request({
    url: '/app/discuss/' + id,
    method: 'delete'
  })
}

// 导出app评论
export function exportDiscuss(query) {
  return request({
    url: '/app/discuss/export',
    method: 'get',
    params: query
  })
}

// 批量通过
export function passDiscuss(id,state) {
  return request({
    url: `/app/discuss/edit_state/${id}`,
    method: 'put',
    data:state
  })
}
// 评论回复功能
export function replyDiscuss() {
  return request({
    url: '/app/discuss/reply',
    method: 'get',
  })
}

