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

// 拷贝并新增配餐功能
export function copyAndAdd(data) {
  return request({
    url: '/fantang/catering/copyAndAdd',
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

// 作废
export function cancelCatering(id) {
  return request({
    url: '/fantang/catering/cancel/' + id,
    method: 'put'
  })
}

// 恢复指定病患的营养配餐
export function restoreCatering(id) {
  return request({
    url: '/fantang/catering/restoreCatering/' + id,
    method: 'put'
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

// 根据病人 id 查找
export function getByPatient(id) {
  return request({
    url: '/fantang/catering/getByPatient/' + id,
    method: 'get'
  })
}

// 根据病人 id 查找全部四条营养配餐记录
export function getAllByPatient(id) {
  return request({
    url: '/fantang/catering/getAllByPatient/' + id,
    method: 'get'
  })
}
