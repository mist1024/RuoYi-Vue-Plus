import store from '@/store'
import eventHub from './common/eventHub'
import userApis from './apis/userApis'

class AppManager {
  login(callBack) {
    let self = this
    wx.showLoading({ title: '正在连接...', mask: true })
    wx.login({
      async success(res) {
        let req1 = await userApis.getSession(res.code)
        console.log(req1)
        if (!req1.data.openid) {
          self.showToast('登录失败！' + res.errMsg)
          wx.hideLoading()
        }
        self.saveOpenid(req1.data.openid)

        let req2 = await userApis.loginByMini({ openid: self.getOpenid() })

        if (!req1.data.openid) {
          self.showToast('登录失败！' + res.errMsg)
          wx.hideLoading()
        }

        if (req2.token) {
          store.dispatch('setTokenAction', req2.token)
          self.setCacheInfo()
          self.setRemoteUserInfo(req2.userInfo)
        }



        wx.hideLoading()

        if (callBack) {
          callBack()
        }
      },
      fail(res) {
        self.showToast('登录失败,正在重试.')
        wx.hideLoading()
        self.login()
      }
    }
    )
  }

  saveOpenid(openid) {
    // console.log('saveOpenid:' + openid)
    store.dispatch('setOpenidAction', openid)
  }

  getOpenid() {
    return wx.getStorageSync('openid')
  }

  setRemoteUserInfo(userInfo) {
    store.dispatch('setMobileAction', userInfo.mobile)
    store.dispatch('setUserInfoAction', userInfo)
  }

  setCacheInfo() {
    const mobile = wx.getStorageSync('mobile')
    const openid = wx.getStorageSync('openid')
    const userInfo = wx.getStorageSync('userInfo')
    const shoppingCar = wx.getStorageSync('shoppingCar')

    console.log('setCacheInfo:', mobile)
    console.log('setCacheInfo:', openid)
    console.log('setCacheInfo:', userInfo)
    console.log('shoppingCar:', shoppingCar)

    if (mobile) {
      store.dispatch('setMobileAction', mobile)
    }

    if (openid) {
      store.dispatch('setOpenidAction', openid)
    }

    if (userInfo) {
      store.dispatch('setUserInfoAction', userInfo)
    }
    if (shoppingCar) {
      store.dispatch('setShoppingCarAction', shoppingCar)
    }
  }

  navigateTo(path) {
    console.log('path:', path)
    if (!store.state.user.token) {
      eventHub.$emit('onShowDialogUserInfo')
      return
    }

    if (!path) {
      this.showToast('建设中')
      return
    }

    wx.navigateTo({
      url: path
    })
  }

  showToast(msg) {
    wx.showToast({ title: msg, icon: 'none' })
  }
}

export default new AppManager()
