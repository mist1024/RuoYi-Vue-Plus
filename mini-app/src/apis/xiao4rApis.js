
import request from '../js/request'

export const baseUrl = 'http://127.0.0.1:18989/'
// export const baseUrl = 'http://36.1.50.18:18989/winery/'
// export const baseUrl = 'http://62.234.123.172:18989/api/'
// export const baseUrl = 'https://www.xiao4r.com/wine/winery/'

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
