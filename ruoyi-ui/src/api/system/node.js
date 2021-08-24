import request from '@/utils/request'


// 查询根节点名字
export function listRootName() {
  return request({
    url: '/system/node/listRootName',
    method: 'get'
  })
}

// 查询节点维护列表
export function listNode(query) {
  return request({
    url: '/system/node/list',
    method: 'get',
    params: query
  })
}

// 根据节点名称查询列表以children形式展示
export function listNodeWithChildren(query) {
  return request({
    url: '/system/node/listWithChildren',
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

// 新增多个节点维护
export function addMultipleNode(data) {
  return request({
    url: '/system/node/addMultiple',
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
