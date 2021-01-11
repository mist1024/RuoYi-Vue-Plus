import request from '@/utils/request'

// 查询人脸信息列表
export function listFaceInfo(query) {
  return request({
    url: '/fantang/faceInfo/list',
    method: 'get',
    params: query
  })
}

// 查询人脸信息详细
export function getFaceInfo(id) {
  return request({
    url: '/fantang/faceInfo/' + id,
    method: 'get'
  })
}

// 新增人脸信息
export function addFaceInfo(data) {
  return request({
    url: '/fantang/faceInfo',
    method: 'post',
    data: data
  })
}

// 修改人脸信息
export function updateFaceInfo(data) {
  return request({
    url: '/fantang/faceInfo',
    method: 'put',
    data: data
  })
}

// 删除人脸信息
export function delFaceInfo(id) {
  return request({
    url: '/fantang/faceInfo/' + id,
    method: 'delete'
  })
}

// 导出人脸信息
export function exportFaceInfo(query) {
  return request({
    url: '/fantang/faceInfo/export',
    method: 'get',
    params: query
  })
}