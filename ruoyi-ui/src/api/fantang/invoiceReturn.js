import request from '@/utils/request'

// 查询回款登记列表
export function listInvoiceReturn(query) {
  return request({
    url: '/fantang/invoiceReturn/list',
    method: 'get',
    params: query
  })
}

// 查询回款登记详细
export function getInvoiceReturn(id) {
  return request({
    url: '/fantang/invoiceReturn/' + id,
    method: 'get'
  })
}

// 新增回款登记
export function addInvoiceReturn(data) {
  return request({
    url: '/fantang/invoiceReturn',
    method: 'post',
    data: data
  })
}

// 修改回款登记
export function updateInvoiceReturn(data) {
  return request({
    url: '/fantang/invoiceReturn',
    method: 'put',
    data: data
  })
}

// 删除回款登记
export function delInvoiceReturn(id) {
  return request({
    url: '/fantang/invoiceReturn/' + id,
    method: 'delete'
  })
}

// 导出回款登记
export function exportInvoiceReturn(query) {
  return request({
    url: '/fantang/invoiceReturn/export',
    method: 'get',
    params: query
  })
}