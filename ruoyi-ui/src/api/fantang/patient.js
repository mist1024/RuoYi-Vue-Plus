import request from '@/utils/request'

// 查询病人管理列表
export function listPatient(query) {
  return request({
    url: '/fantang/patient/list',
    method: 'get',
    params: query
  })
}

// 根据 departId 查询病人列表
export function selectPatientByDepartId(departId) {
  return request({
    url: '/fantang/patient/selectPatientByDepartId/' + departId,
    method: 'get',
  })
}

// 根据 patientId 查询病人床号
export function getBedIdById(patientId) {
  return request({
    url: '/fantang/patient/getBedIdById/' + patientId,
    method: 'get',
  })
}

// 查询病人管理详细
export function getPatient(patientId) {
  return request({
    url: '/fantang/patient/' + patientId,
    method: 'get'
  })
}

// 新增病人管理
export function addPatient(data) {
  return request({
    url: '/fantang/patient',
    method: 'post',
    data: data
  })
}

// 修改病人管理
export function updatePatient(data) {
  return request({
    url: '/fantang/patient',
    method: 'put',
    data: data
  })
}

// 删除病人管理
export function delPatient(patientId) {
  return request({
    url: '/fantang/patient/' + patientId,
    method: 'delete'
  })
}

// 导出病人管理
export function exportPatient(query) {
  return request({
    url: '/fantang/patient/export',
    method: 'get',
    params: query
  })
}
