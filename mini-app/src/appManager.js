
import store from '@/store'

class AppManager {
  saveOpenid(openid) {
    wx.setStorageSync('openid', openid)
    store.dispatch('setOpenidAction', openid)
  }

  getOpenid() {
    return wx.getStorageSync('openid')
  }

  navigateTo(url) {
    wx.navigateTo({
      url: url
    })
  }

  showToast(msg) {
    wx.showToast({title: msg, icon: 'none'})
  }
}

export default new AppManager()
