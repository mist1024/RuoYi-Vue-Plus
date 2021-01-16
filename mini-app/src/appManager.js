import store from '@/store'
import eventHub from './common/eventHub'

class AppManager {
  saveOpenid(openid) {
    store.dispatch('setOpenidAction', openid)
  }

  getOpenid() {
    return wx.getStorageSync('openid')
  }

  setCacheInfo() {
    const mobile = wx.getStorageSync('mobile')
    const openid = wx.getStorageSync('openid')
    const userInfo = wx.getStorageSync('userInfo')

    console.log('setCacheInfo:', mobile)
    console.log('setCacheInfo:', openid)
    console.log('setCacheInfo:', userInfo)

    if (mobile) {
      store.dispatch('setMobileAction', mobile)
    }

    if (openid) {
      store.dispatch('setOpenidAction', openid)
    }

    if (userInfo) {
      store.dispatch('setUserInfoAction', userInfo)
    }
  }

  navigateTo(path) {
    console.log('path:', path)
    if (!store.state.user.token) {
      eventHub.$emit('onShowDialogUserInfo')
      return
    }

    console.log('path:', path)

    if (!path) {
      this.showToast('建设中')
      return
    }

    console.log('path:', path)
    wx.navigateTo({
      url: path
    })
  }

  showToast(msg) {
    wx.showToast({ title: msg, icon: 'none' })
  }
}

export default new AppManager()
