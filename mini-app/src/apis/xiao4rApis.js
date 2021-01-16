
import request from '../js/request'
import { baseUrl } from '../baseDefine'

export const jsonHeader = {
  'Content-Type': 'application/json'
}

export const formHeader = {
  'Content-Type': 'application/x-www-form-urlencoded'
}

/**
 * 接口
 */
class Xiao4rApis {
  postForm(data) {
    return request.post({
      url: baseUrl + 'winery/mini/postForm',
      header: jsonHeader,
      data: data
    })
  }

  getForm(data) {
    return request.get({
      url: baseUrl + 'winery/mini/getForm',
      header: formHeader,
      data: data
    })
  }
}

export default new Xiao4rApis()
