import request from '@/utils/request'

/**
 * 获取指定用户最后一次结算日期
 * 作者： czx
 * 日期：2020年12月2日
 * 功能：向后台获取指定用户最后一次结算日期，显示在收费弹出层
 * @param patientId
 */
export function getLastBillingDateByPatientId(patientId) {
  return request({
    url: '/fantang/settle/getLastBillingDateByPatientId/' + patientId,
    method: 'get'
  })
}



// 查询结算报列表
export function listSettle(query) {
  return request({
    url: '/fantang/settle/list',
    method: 'get',
    params: query
  })
}

// 查询结算报详细
export function getSettle(settleId) {
  return request({
    url: '/fantang/settle/' + settleId,
    method: 'get'
  })
}

// 新增结算报
export function addSettle(data) {
  return request({
    url: '/fantang/settle/addSettle',
    method: 'post',
    data: data
  })
}

// 显示正餐记录
export function showMealsWithSelect(data) {
  return request({
    url: '/fantang/settle/showMealsWithSelect',
    method: 'post',
    data: data
  })
}

// 修改结算报
export function updateSettle(data) {
  return request({
    url: '/fantang/settle',
    method: 'put',
    data: data
  })
}

// 删除结算报
export function delSettle(settleId) {
  return request({
    url: '/fantang/settle/' + settleId,
    method: 'delete'
  })
}

// 导出结算报
export function exportSettle(query) {
  return request({
    url: '/fantang/settle/export',
    method: 'get',
    params: query
  })
}
