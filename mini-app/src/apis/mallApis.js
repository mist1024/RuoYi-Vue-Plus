
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

  getHotSwitch() {
    return request.get({
      url: baseUrl + 'winery/activity/open/hotSwitch'
    })
  }

  getNotice() {
    return request.get({
      url: baseUrl + 'winery/activity/open/notice'
    })
  }

  getNewsContent(id) {
    return request.get({
      url: baseUrl + 'news/news_content/open/' + id
    })
  }
}

export default new MallApis()
