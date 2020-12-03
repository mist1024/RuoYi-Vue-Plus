import request from '@/utils/request'

// 查询病患营养配餐列表
export function listNutritionFood(query) {
  return request({
    url: '/fantang/nutritionFood/list',
    method: 'get',
    params: query
  })
}

// 查询病患营养配餐详细
export function getNutritionFood(id) {
  return request({
    url: '/fantang/nutritionFood/' + id,
    method: 'get'
  })
}

// 新增病患营养配餐
export function addNutritionFood(data) {
  return request({
    url: '/fantang/nutritionFood',
    method: 'post',
    data: data
  })
}

// 修改病患营养配餐
export function updateNutritionFood(data) {
  return request({
    url: '/fantang/nutritionFood',
    method: 'put',
    data: data
  })
}

// 停用病患营养配餐
export function deactivate(id) {
  return request({
    url: '/fantang/nutritionFood/deactivate/' + id,
    method: 'put',
    params: id
  })
}

// 删除病患营养配餐
export function delNutritionFood(id) {
  return request({
    url: '/fantang/nutritionFood/' + id,
    method: 'delete'
  })
}

// 导出病患营养配餐
export function exportNutritionFood(query) {
  return request({
    url: '/fantang/nutritionFood/export',
    method: 'get',
    params: query
  })
}
