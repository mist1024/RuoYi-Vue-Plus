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
  createOrder(data) {
    return request.post({
      url: baseUrl + 'winery/order',
      data: data
    })
  }

  getOrderList(data) {
    return request.get({
      url: baseUrl + 'winery/order/list',
      data: data
    })
  }

  requestRefund(data) {
    return request.post({
      url: baseUrl + 'winery/detail/requestRefund',
      data: data
    })
  }

  editOrder(data) {
    return request.put({
      url: baseUrl + 'winery/order',
      data: data
    })
  }

  getOrderDetailList(data) {
    return request.get({
      url: baseUrl + 'winery/detail/list',
      data: data
    })
  }
}

export default new OrderApis()
