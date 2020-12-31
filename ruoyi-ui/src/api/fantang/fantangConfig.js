import request from '@/utils/request'

// 查询饭堂参数列表
export function listFantangConfig(query) {
  return request({
    url: '/fantang/fantangConfig/list',
    method: 'get',
    params: query
  })
}

// 查询饭堂参数详细
export function getFantangConfig(id) {
  return request({
    url: '/fantang/fantangConfig/' + id,
    method: 'get'
  })
}

// 新增饭堂参数
export function addFantangConfig(data) {
  return request({
    url: '/fantang/fantangConfig',
    method: 'post',
    data: data
  })
}

export function updateDinnerTime(data) {
  return request({
    url: '/fantang/fantangConfig/updateDinnerTime',
    method: 'post',
    data: data
  })
}

// 修改饭堂参数
export function updateFantangConfig(data) {
  return request({
    url: '/fantang/fantangConfig',
    method: 'put',
    data: data
  })
}

// 删除饭堂参数
export function delFantangConfig(id) {
  return request({
    url: '/fantang/fantangConfig/' + id,
    method: 'delete'
  })
}

// 导出饭堂参数
export function exportFantangConfig(query) {
  return request({
    url: '/fantang/fantangConfig/export',
    method: 'get',
    params: query
  })
}
