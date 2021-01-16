
import store from '@/store'
import eventHub from './common/eventHub'

class AppManager {
  saveOpenid(openid) {

    store.dispatch('setOpenidAction', openid)
  }

  getOpenid() {
    return wx.getStorageSync('openid')
  }

  navigateTo(url) {
    if (!store.state.user.token) {
      eventHub.$emit('onShowDialogUserInfo')
      return
    }

    if (!url) {
      this.showToast('建设中')
      return
    }

    wx.navigateTo({
      url: url
    })
  }

  showToast(msg) {
    wx.showToast({title: msg, icon: 'none'})
  }
}

export default new AppManager()
