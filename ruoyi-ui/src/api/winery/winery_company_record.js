import request from '@/utils/request'

// 查询酒庄厂家登记记录列表
export function listWinery_company_record(query) {
  return request({
    url: '/winery/winery_company_record/list',
    method: 'get',
    params: query
  })
}

// 查询酒庄厂家登记记录详细
export function getWinery_company_record(id) {
  return request({
    url: '/winery/winery_company_record/' + id,
    method: 'get'
  })
}

// 新增酒庄厂家登记记录
export function addWinery_company_record(data) {
  return request({
    url: '/winery/winery_company_record',
    method: 'post',
    data: data
  })
}

// 修改酒庄厂家登记记录
export function updateWinery_company_record(data) {
  return request({
    url: '/winery/winery_company_record',
    method: 'put',
    data: data
  })
}

// 删除酒庄厂家登记记录
export function delWinery_company_record(id) {
  return request({
    url: '/winery/winery_company_record/' + id,
    method: 'delete'
  })
}

// 导出酒庄厂家登记记录
export function exportWinery_company_record(query) {
  return request({
    url: '/winery/winery_company_record/export',
    method: 'get',
    params: query
  })
}