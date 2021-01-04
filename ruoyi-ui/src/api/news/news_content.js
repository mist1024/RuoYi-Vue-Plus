import request from '@/utils/request'

// 查询新闻资讯列表
export function listNews_content(query) {
  return request({
    url: '/news/news_content/list',
    method: 'get',
    params: query
  })
}

// 查询新闻资讯详细
export function getNews_content(id) {
  return request({
    url: '/news/news_content/' + id,
    method: 'get'
  })
}

// 新增新闻资讯
export function addNews_content(data) {
  return request({
    url: '/news/news_content',
    method: 'post',
    data: data
  })
}

// 修改新闻资讯
export function updateNews_content(data) {
  return request({
    url: '/news/news_content',
    method: 'put',
    data: data
  })
}

// 删除新闻资讯
export function delNews_content(id) {
  return request({
    url: '/news/news_content/' + id,
    method: 'delete'
  })
}

// 导出新闻资讯
export function exportNews_content(query) {
  return request({
    url: '/news/news_content/export',
    method: 'get',
    params: query
  })
}