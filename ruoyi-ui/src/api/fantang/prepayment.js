import request from '@/utils/request'

// 查询所有未缴预付费病人列表
export function listNoPrepayment() {
  return request({
    url: '/fantang/prepayment/listNoPrepay',
    method: 'get',
  })
}
// 查询所有已缴预付费病人列表
export function listPrepay() {
  return request({
    url: '/fantang/prepayment/listPrepay',
    method: 'get',
  })
}

// 查询所有已结算
export function listAllPrepay() {
  return request({
    url: '/fantang/prepayment/listAllPrepay',
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
