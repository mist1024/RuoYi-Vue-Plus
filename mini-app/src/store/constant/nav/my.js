import { imgbaseUrl } from '../../../baseDefine'
import { orderDetailListPage, orderListPage, userAddressListPage } from './pages'


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
  }, {
    name: '优惠券',
    icon: imgbaseUrl + '/my/tool_3.png',
    path: ''
  }
]

export const myMenuList3 = [
  {
    name: '关注',
    icon: imgbaseUrl + 'doctor.png',
    path: ''
  },
  {
    name: '在线客服',
    icon: imgbaseUrl + 'doctor.png',
    path: ''
  }, {
    name: '条款政策',
    icon: imgbaseUrl + 'doctor.png',
    path: ''
  }
]
