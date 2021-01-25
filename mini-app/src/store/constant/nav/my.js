import { imgbaseUrl } from '../../../baseDefine'
import { newsDetailPage, orderDetailListPage, orderListPage, userAddressListPage } from './pages'


export const myMenuList1 = [
  {
    name: '全部订单',
    icon: imgbaseUrl + '/my/order_1.png',
    path: orderListPage
  },
  {
    name: '待付款',
    icon: imgbaseUrl + '/my/order_2.png',
    path: orderListPage + '?orderStatus=1'
  }, {
    name: '待收货',
    icon: imgbaseUrl + '/my/order_3.png',
    path: orderListPage + '?orderStatus=2'
  }
]
export const myMenuList2 = [
  {
    name: '地址管理',
    icon: imgbaseUrl + '/my/tool_1.png',
    path: userAddressListPage
  },
  {
    name: '退款管理',
    icon: imgbaseUrl + '/my/tool_2.png',
    path: orderDetailListPage
  }
]

export const myMenuList3 = [
  {
    name: '客服热线',
    icon: imgbaseUrl + '/my/more_1.png',
    path: 'customer'
  },
  {
    name: '隐私政策',
    icon: imgbaseUrl + '/my/more_2.png',
    path: newsDetailPage + '?id=' + 'a1e5ec18ae13036d14c94bf0e5d11756'
  },
  {
    name: '服务条款',
    icon: imgbaseUrl + '/my/more_3.png',
    path: newsDetailPage + '?id=' + 'fb4109a4020b2a2a9d1172f66d043897'
  }
]
