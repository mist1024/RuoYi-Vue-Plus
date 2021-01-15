
import request from '../js/request'
import { baseUrl, formHeader, jsonHeader } from './xiao4rApis'
import { MINI_DEPTID } from '../config'

/**
 * 商城相关接口
 */
class MallApis {
  getGoodsList(data) {
    return request.get({
      url: baseUrl + 'goods/goods_main/list',
      data: data
    })
  }
}

export default new MallApis()
