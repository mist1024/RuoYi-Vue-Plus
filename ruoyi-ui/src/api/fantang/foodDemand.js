import request from '@/utils/request'

// 查询病人报餐列表
export function listFoodDemand(query) {
  return request({
    url: '/fantang/foodDemand/list',
    method: 'get',
    params: query
  })
}

// 查询病人报餐详细
export function getFoodDemand(id) {
  return request({
    url: '/fantang/foodDemand/' + id,
    method: 'get'
  })
}

// 新增病人报餐
export function addFoodDemand(data) {
  return request({
    url: '/fantang/foodDemand',
    method: 'post',
    data: data
  })
}

// 修改病人报餐
export function updateFoodDemand(data) {
  return request({
    url: '/fantang/foodDemand',
    method: 'put',
    data: data
  })
}

// 删除病人报餐
export function delFoodDemand(id) {
  return request({
    url: '/fantang/foodDemand/' + id,
    method: 'delete'
  })
}

// 导出病人报餐
export function exportFoodDemand(query) {
  return request({
    url: '/fantang/foodDemand/export',
    method: 'get',
    params: query
  })
}