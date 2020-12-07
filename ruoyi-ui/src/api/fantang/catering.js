import request from '@/utils/request'

// 查询配餐功能列表
export function listCatering(query) {
  return request({
    url: '/fantang/catering/list',
    method: 'get',
    params: query
  })
}

// 查询配餐功能详细
export function getCatering(id) {
  return request({
    url: '/fantang/catering/' + id,
    method: 'get'
  })
}

// 新增配餐功能
export function addCatering(data) {
  return request({
    url: '/fantang/catering',
    method: 'post',
    data: data
  })
}

// 修改配餐功能
export function updateCatering(data) {
  return request({
    url: '/fantang/catering',
    method: 'put',
    data: data
  })
}

// 删除配餐功能
export function delCatering(id) {
  return request({
    url: '/fantang/catering/' + id,
    method: 'delete'
  })
}

// 导出配餐功能
export function exportCatering(query) {
  return request({
    url: '/fantang/catering/export',
    method: 'get',
    params: query
  })
}