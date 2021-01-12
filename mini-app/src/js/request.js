import xiao4rBase from '../xiao4rBase'
import { uuid } from './utils/uuid'
import store from '@/store'

class Xiao4rRequest {
  // 网络请求任务对象 Map
  requestTaskMap = new Map()

  /**
   * requestPromise用于将wx.request改写成Promise方式
   * @param：{string} myUrl 接口地址
   * @return: Promise实例对象
   */
  requestPromise(req) {
    let id = uuid()
    let self = this

    if (store.state.user.token) {
      if (req.header) {
        req.header['Authorization'] = store.state.user.token
      } else {
        req.header = {
          Authorization: store.state.user.token
        }
      }
    }

    let promise = new Promise((resolve, reject) => {
      const task = wx.request({
        url: req.url,
        method: req.method,
        header: req.header,
        data: req.data,
        success: rsp => resolve(rsp.data),
        fail: error => {
          xiao4rBase.showToast('网络连接异常.')
          reject(error)
        },
        complete: () => {
          this.requestTaskMap.delete(id)
        }
      })
      self.requestTaskMap.set(id, task)
    })
    // 返回一个Promise实例对象
    promise.taskId = id
    return promise
  }

  post(req) {
    req.method = 'POST'
    return this.requestPromise(req)
  }

  get(req) {
    req.method = 'GET'
    return this.requestPromise(req)
  }

  put(req) {
    req.method = 'PUT'
    return this.requestPromise(req)
  }

  del(req) {
    req.method = 'DEL'
    return this.requestPromise(req)
  }
}

export default new Xiao4rRequest()
