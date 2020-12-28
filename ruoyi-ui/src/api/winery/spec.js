import request from '@/utils/request'

// 查询商品规格列表
export function listSpec(query) {
  return request({
    url: '/winery/spec/list',
    method: 'get',
    params: query
  })
}

// 查询商品规格详细
export function getSpec(id) {
  return request({
    url: '/winery/spec/' + id,
    method: 'get'
  })
}

// 新增商品规格
export function addSpec(data) {
  return request({
    url: '/winery/spec',
    method: 'post',
    data: data
  })
}

// 修改商品规格
export function updateSpec(data) {
  return request({
    url: '/winery/spec',
    method: 'put',
    data: data
  })
}

// 删除商品规格
export function delSpec(id) {
  return request({
    url: '/winery/spec/' + id,
    method: 'delete'
  })
}

// 导出商品规格
export function exportSpec(query) {
  return request({
    url: '/winery/spec/export',
    method: 'get',
    params: query
  })
}