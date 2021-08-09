import request from '@/utils/request'

// 查询节点维护列表
export function listNode(query) {
  return request({
    url: '/system/node/list',
    method: 'get',
    params: query
  })
}

// 查询节点维护详细
export function getNode(id) {
  return request({
    url: '/system/node/' + id,
    method: 'get'
  })
}

// 新增节点维护
export function addNode(data) {
  return request({
    url: '/system/node',
    method: 'post',
    data: data
  })
}

// 修改节点维护
export function updateNode(data) {
  return request({
    url: '/system/node',
    method: 'put',
    data: data
  })
}

// 删除节点维护
export function delNode(id) {
  return request({
    url: '/system/node/' + id,
    method: 'delete'
  })
}

// 导出节点维护
export function exportNode(query) {
  return request({
    url: '/system/node/export',
    method: 'get',
    params: query
  })
}