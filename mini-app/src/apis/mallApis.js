
import request from '../js/request'
import { baseUrl } from '../baseDefine'

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
  getGoodsById(id) {
    return request.get({
      url: baseUrl + 'goods/goods_main/' + id
    })
  }

  getGoodsSpecByIds(ids) {
    return request.get({
      url: baseUrl + 'goods/goods_spec/listByIds/' + ids
    })
  }

  getActivityList() {
    return request.get({
      url: baseUrl + 'winery/activity/open/list'
    })
  }
}

export default new MallApis()
