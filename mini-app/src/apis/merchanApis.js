
import request from '../js/request'
import { baseUrl, MINI_DEPTID } from '../baseDefine'
import { formHeader, jsonHeader } from './xiao4rApis'

/**
 * 酒庄相关接口
 */
class MerchanApis {
  getMerchantList(data) {
    return request.get({
      url: baseUrl + 'winery/merchant/list',
      data: data
    })
  }

  getMerchantInfo(id) {
    return request.get({
      url: baseUrl + 'winery/merchant/' + id
    })
  }

  getMerchantInfoByDeptId(deptId) {
    return request.get({
      url: baseUrl + 'winery/merchant/dept/' + deptId
    })
  }
}

export default new MerchanApis()
