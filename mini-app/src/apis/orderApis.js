
import request from '../js/request'
import { baseUrl } from '../baseDefine'

/**
 * 订单相关接口
 */
class OrderApis {


  /**
   * 创建订单
   * @param data
   * @returns {Promise实例对象}
   */
  createOrders(data) {
    return request.post({
      url: baseUrl + 'winery/user_orders',
      data: data
    })
  }

}

export default new OrderApis()
