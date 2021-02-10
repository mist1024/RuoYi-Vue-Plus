import request from '@/utils/request'

// 查询财务收费开票列表
export function listInvoice(query) {
  return request({
    url: '/fantang/invoice/list',
    method: 'get',
    params: query
  })
}

// 查询财务收费开票详细
export function getInvoice(id) {
  return request({
    url: '/fantang/invoice/' + id,
    method: 'get'
  })
}

// 新增财务收费开票
export function addInvoice(data) {
  return request({
    url: '/fantang/invoice',
    method: 'post',
    data: data
  })
}

// 修改财务收费开票
export function updateInvoice(data) {
  return request({
    url: '/fantang/invoice',
    method: 'put',
    data: data
  })
}

// 删除财务收费开票
export function delInvoice(id) {
  return request({
    url: '/fantang/invoice/' + id,
    method: 'delete'
  })
}

// 结束跟踪
export function finish(id) {
  return request({
    url: '/fantang/invoice/finish/' + id,
    method: 'put'
  })
}

// 导出财务收费开票
export function exportInvoice(query) {
  return request({
    url: '/fantang/invoice/export',
    method: 'get',
    params: query
  })
}

export function addToInvoice(data) {
  return request({
    url: '/fantang/invoice/addToInvoice',
    method: 'post',
    data: data
  })
}
