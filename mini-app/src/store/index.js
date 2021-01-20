import Vuex from '@wepy/x'
import { wineryForm } from './wineryForm'
import { imageDefine } from './constant/imageDefine'
import { navDefine } from './constant/navDefine'
import { wineryDefine } from './constant/wineryDefine'
import { userAddress } from './constant/userAddress'

export default new Vuex.Store({
  state: {
    user: {
      openid: '',
      mobile: '',
      userInfo: {
        nickName: '未注册用户',
        avatarUrl: 'https://img.yzcdn.cn/vant/cat.jpeg'
      },
      token: '',
      company: '企业名称',
      currWebUrl: ''
    },
    // 订单
    order: {},
    // 购物车
    shoppingCar: [],
    wineryForm,
    imageDefine,
    navDefine,
    wineryDefine,
    userAddress
  },
  mutations: {

    setUser(state, user) {
      state.user = user
    },
    setOpenid(state, openid) {
      state.user.openid = openid
    },
    setMobile(state, mobile) {
      state.user.mobile = mobile
    },
    setUserInfo(state, userInfo) {
      state.user.userInfo = userInfo
    },
    setToken(state, token) {
      state.user.token = token
    },
    setWineryForm(state, form) {
      state.wineryForm = form
    },
    setOrder(state, order) {
      state.order = order
    },
    setShoppingCar(state, shoppingCar) {

      state.shoppingCar = shoppingCar

    }
  },

  actions: {
    setUserAction({ commit }, user) {
      commit('setUser', user)
    },
    setOpenidAction({ commit }, openid) {
      wx.setStorageSync('openid', openid)
      commit('setOpenid', openid)
    },
    setMobileAction({ commit }, mobile) {
      wx.setStorageSync('mobile', mobile)
      commit('setMobile', mobile)
    },
    setUserInfoAction({ commit }, userInfo) {
      wx.setStorageSync('userInfo', userInfo)
      commit('setUserInfo', userInfo)
    },
    setTokenAction({ commit }, token) {
      commit('setToken', token)
    },
    setWineryFormAction({ commit }, form) {
      commit('setWineryForm', form)
    },
    setOrderAction({commit}, order) {
      commit('setOrder', order)
    },
    setShoppingCarAction({commit}, shoppingCar) {
      wx.setStorageSync('shoppingCar', shoppingCar)
      commit('setShoppingCar', shoppingCar)
    }

  }
})
