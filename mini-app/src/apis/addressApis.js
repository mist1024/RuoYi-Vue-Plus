import request from '../js/request'
import { baseUrl } from '../baseDefine'
import { jsonHeader } from './xiao4rApis'

/**
 * 地址管理相关接口
 */
class AddressApis {
  /**
   * 创建地址
   * @param data
   * @returns {Promise实例对象}
   */
  getAddressList(data) {
    return request.get({
      url: baseUrl + 'user/address/list',
      data: data
    })
  }

  getAddressById(id) {
    return request.get({
      url: baseUrl + 'user/address/' + id
    })

  }

  createAddress(data) {

    return request.post({
      url: baseUrl + 'user/address',

      data: data
    })
  }

  delAddress(data) {
    return request.del({
      url: baseUrl + 'user/address',
      data: data
    })
  }

  editAddress(data) {
    return request.put({
      url: baseUrl + 'user/address',
      data: data
    })
  }
}

export default new AddressApis()
