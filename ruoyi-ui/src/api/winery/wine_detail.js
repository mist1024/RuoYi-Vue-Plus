import request from '@/utils/request'

// 查询葡萄酒规格详情列表
export function listWine_detail(query) {
  return request({
    url: '/winery/wine_detail/list',
    method: 'get',
    params: query
  })
}

// 查询葡萄酒规格详情详细
export function getWine_detail(id) {
  return request({
    url: '/winery/wine_detail/' + id,
    method: 'get'
  })
}

// 新增葡萄酒规格详情
export function addWine_detail(data) {
  return request({
    url: '/winery/wine_detail',
    method: 'post',
    data: data
  })
}

// 修改葡萄酒规格详情
export function updateWine_detail(data) {
  return request({
    url: '/winery/wine_detail',
    method: 'put',
    data: data
  })
}

// 删除葡萄酒规格详情
export function delWine_detail(id) {
  return request({
    url: '/winery/wine_detail/' + id,
    method: 'delete'
  })
}

// 导出葡萄酒规格详情
export function exportWine_detail(query) {
  return request({
    url: '/winery/wine_detail/export',
    method: 'get',
    params: query
  })
}