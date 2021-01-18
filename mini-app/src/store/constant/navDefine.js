import { homeBanner, homeBanner1, homeBanner2, homeHeader, homeMenuList, menu1, menu2 } from './nav/home'
import { myMenuList1, myMenuList2, myMenuList3 } from './nav/my'
import { orderCheck, orderListPage, shoppingCarList, userAddressListPage } from './nav/pages'

export const navDefine = {
  HOME_MENU_LIST: homeMenuList,
  HOME_BANNER: homeBanner,
  HOME_MENU1: menu1,
  HOME_MENU2: menu2,

  HOME_HEADER: homeHeader,
  HOME_BANNER1: homeBanner1,
  HOME_BANNER2: homeBanner2,

  MY_MENU1: myMenuList1,
  MY_MENU2: myMenuList2,
  MY_MENU3: myMenuList3,

  SHOPPING_CAR_LIST: shoppingCarList,
  ORDER_LIST_PAGE: orderListPage,
  ORDER_CHECK: orderCheck,
  USER_ADDRESS_LIST_PAGE: userAddressListPage,

}
