
import request from '../js/request'
import { baseUrl, formHeader, jsonHeader } from './xiao4rApis'
import { MINI_DEPTID } from '../config'

/**
 * 用户相关接口
 */
class UserApis {
  /**
   * 注册
   * @param data
   * @returns {Promise实例对象}
   */
  registrationByMini(data) {
    data.deptId = MINI_DEPTID
    return request.post({
      url: baseUrl + 'mini/user/registrationByMini',
      data: data
    })
  }

  /**
   * 登录
   * @param data
   * @returns {Promise实例对象}
   */
  loginByMini(data) {
    data.deptId = MINI_DEPTID
    return request.post({
      url: baseUrl + 'mini/user/loginByMini',
      data: data
    })
  }

  getSession(code) {
    return request.get({
      url: baseUrl + 'mini/user/getSession',
      header: formHeader,
      data: {
        'code': code,
        'deptId': MINI_DEPTID
      }
    })
  }

  sendMobile(data) {
    data.deptId = MINI_DEPTID
    return request.post({
      url: baseUrl + 'mini/user/sendMobile',
      header: jsonHeader,
      data: data
    })
  }

  getAuthTest(data) {
    return request.get({
      url: baseUrl + 'mini/user/test',
      header: formHeader,
      data: data
    })
  }
}

export default new UserApis()
