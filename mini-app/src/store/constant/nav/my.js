import { imgbaseUrl } from '../../../baseDefine'

export const myMenuList1 = [
  {
    name: '全部订单',
    icon: imgbaseUrl + 'doctor.png',
    path: '/pages/mail/order/orderList'
  },
  {
    name: '待付款',
    icon: imgbaseUrl + 'doctor.png',
    path: '/pages/mail/order/orderList?orderStatus=1'
  }, {
    name: '待收货',
    icon: imgbaseUrl + 'doctor.png',
    path: '/pages/mail/order/orderList?orderStatus=2'
  }
]
export const myMenuList2 = [
  {
    name: '地址管理',
    icon: imgbaseUrl + 'doctor.png',
    path: '/pages/mall/user/user-address-list'
  },
  {
    name: '发票管理',
    icon: imgbaseUrl + 'doctor.png',
    path: ''
  }, {
    name: '优惠券',
    icon: imgbaseUrl + 'doctor.png',
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
