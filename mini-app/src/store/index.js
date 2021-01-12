import Vuex from '@wepy/x'
import { wineryForm } from './wineryForm'

export default new Vuex.Store({
  state: {
    user: {
      openid: '',
      mobile: '',
      userInfo: {},
      token: ''
    },
    wineryForm
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
      commit('setOpenid', openid)
    },
    setMobileAction({ commit }, user) {
      commit('setMobile', user)
    },
    setUserInfoAction({ commit }, userInfo) {
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
