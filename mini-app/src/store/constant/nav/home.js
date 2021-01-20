import { imgbaseUrl } from '../../../baseDefine'
import { wineryListPage } from './pages'

const homeImg = imgbaseUrl + 'home/'

export const homeHeader = {
  image: homeImg + 'home_header.png',
  path: ''
}
export const homeBanner1 = {
  image: homeImg + 'home_banner1.png',
  path: ''
}
export const homeBanner2 = {
  image: homeImg + 'home_banner2.png',
  path: ''
}

export const homeMenuList = [
  {
    name: '产品追溯',
    icon: homeImg + 'home_grid1.png',
    path: ''
  },
  {
    name: '新品推荐',
    icon: homeImg + 'home_grid2.png',
    path: ''
  }, {
    name: '视频直播',
    icon: homeImg + 'home_grid3.png',
    path: ''
  }
]
export const homeBanner = {
  image: homeImg + 'doctor.png',
  path: ''
}

export const menu1 = {
  image: homeImg + 'home_menu1.png',
  path: wineryListPage
}
export const menu2 = {
  image: homeImg + 'home_menu2.png',
  path: ''
}

export const userAddressPage = '/pages/mall/user/user-address'
