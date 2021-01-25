import request from '@/utils/request'

// 查询回款登记列表
export function listReturnManage(query) {
  return request({
    url: '/fantang/returnManage/list',
    method: 'get',
    params: query
  })
}

// 查询回款登记详细
export function getReturnManage(id) {
  return request({
    url: '/fantang/returnManage/' + id,
    method: 'get'
  })
}

// 根据发票 id 查询回款记录
export function getReturnByInvoice(id) {
  return request({
    url: '/fantang/returnManage/getReturnByInvoice/' + id,
    method: 'get'
  })
}

// 新增回款登记
export function addReturnManage(data) {
  return request({
    url: '/fantang/returnManage',
    method: 'post',
    data: data
  })
}

// 新增回款登记
export function addToReturn(data) {
  return request({
    url: '/fantang/returnManage/addToReturn',
    method: 'post',
    data: data
  })
}

// 修改回款登记
export function updateReturnManage(data) {
  return request({
    url: '/fantang/returnManage',
    method: 'put',
    data: data
  })
}

// 删除回款登记
export function delReturnManage(id) {
  return request({
    url: '/fantang/returnManage/' + id,
    method: 'delete'
  })
}

// 导出回款登记
export function exportReturnManage(query) {
  return request({
    url: '/fantang/returnManage/export',
    method: 'get',
    params: query
  })
}
