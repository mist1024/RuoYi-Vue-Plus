import request from '@/utils/request'

// 查询服务分类列表
export function listCate(query) {
  return request({
    url: '/isc/cate/list',
    method: 'get',
    params: query
  })
}

// 查询服务分类详细
export function getCate(cateId) {
  return request({
    url: '/isc/cate/' + cateId,
    method: 'get'
  })
}

// 新增服务分类
export function addCate(data) {
  return request({
    url: '/isc/cate',
    method: 'post',
    data: data
  })
}

// 修改服务分类
export function updateCate(data) {
  return request({
    url: '/isc/cate',
    method: 'put',
    data: data
  })
}

// 删除服务分类
export function delCate(cateId) {
  return request({
    url: '/isc/cate/' + cateId,
    method: 'delete'
  })
}

// 查询服务分类下拉树结构
export function treeselect() {
  return request({
    url: '/isc/cate/treeselect',
    method: 'get'
  })
}
