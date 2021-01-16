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
      mobile: '15595184707',
      userInfo: {
        nickName: '未注册用户',
        avatar: 'https://img.yzcdn.cn/vant/cat.jpeg'
      },
      token: '',
      company: '企业名称'
    },
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
      console.log('state.user.openid:', state.user.openid)
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
      console.log('远程酒庄表单:', form)
      state.wineryForm = form
      console.log('本地酒庄表单:', state.wineryForm)
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
    }

  }
})
