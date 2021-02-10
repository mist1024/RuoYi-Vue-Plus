import request from '@/utils/request'

// 查询所有未缴预付费病人列表
export function listNoPrepayment(query) {
  return request({
    url: '/fantang/prepayment/listNoPrepay',
    method: 'get',
    params: query
  })
}

// 查询所有已缴预付费病人列表
export function listPrepay(query) {
  return request({
    url: '/fantang/prepayment/listPrepay',
    method: 'get',
    params: query
  })
}

// 查询所有已结算
export function listAllPrepay() {
  return request({
    url: '/fantang/prepayment/listAllPrepay',
    method: 'get',
  })
}

/**
 * 查询该用户是否已经收取预付伙食费
 * 作者： 陈智兴
 * 日期： 2020年12月2日
 * 功能： 前端调用查询
 * @param patientId
 */
//
export function getCountById(patientId) {
  return request({
    url: '/fantang/prepayment/getCountById/' + patientId,
    method: 'get',
  })
}

// 查询收费管理列表
export function listPrepayment(query) {
  return request({
    url: '/fantang/prepayment/list',
    method: 'get',
    params: query
  })
}


// 查询收费管理详细
export function getPrepayment(prepaymentId) {
  return request({
    url: '/fantang/prepayment/' + prepaymentId,
    method: 'get'
  })
}

// 通过病人id查询收费管理详细
export function getPrepaymentByPatientId(patientId) {
  return request({
    url: '/fantang/prepayment/getPrepaymentByPatientId/' + patientId,
    method: 'get'
  })
}

// 新增收费管理
export function addPrepayment(data) {
  return request({
    url: '/fantang/prepayment',
    method: 'post',
    data: data
  })
}

// 修改收费管理
export function updatePrepayment(data) {
  return request({
    url: '/fantang/prepayment',
    method: 'put',
    data: data
  })
}

// 删除收费管理
export function delPrepayment(prepaymentId) {
  return request({
    url: '/fantang/prepayment/' + prepaymentId,
    method: 'delete'
  })
}

// 导出收费管理
export function exportPrepayment(query) {
  return request({
    url: '/fantang/prepayment/export',
    method: 'get',
    params: query
  })
}

export function generateReceiptPdf(data) {
  return request({
    url: '/fantang/prepayment/generateReceiptPdf',
    method: 'post',
    data: data
  })
}

export function leaveSettlePrepayment(prepaymentId) {
  return request({
    url: '/fantang/prepayment/leaveSettlePrepayment/' + prepaymentId,
    method: 'put'
  })
}
