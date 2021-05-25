import request from '@/utils/request'

// 查询学生考试信息列表
export function listScore(query) {
  return request({
    url: '/edu/score/list',
    method: 'get',
    params: query
  })
}

// 查询学生考试信息详细
export function getScore(id) {
  return request({
    url: '/edu/score/' + id,
    method: 'get'
  })
}

// 新增学生考试信息
export function addScore(data) {
  return request({
    url: '/edu/score',
    method: 'post',
    data: data
  })
}

// 修改学生考试信息
export function updateScore(data) {
  return request({
    url: '/edu/score',
    method: 'put',
    data: data
  })
}

// 删除学生考试信息
export function delScore(id) {
  return request({
    url: '/edu/score/' + id,
    method: 'delete'
  })
}

// 导出学生考试信息
export function exportScore(query) {
  return request({
    url: '/edu/score/export',
    method: 'get',
    params: query
  })
}