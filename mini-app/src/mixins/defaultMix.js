import appManager from '../appManager'

export default {
  data: {

  },
  methods: {

    onNavItem (item) {
      appManager.navigateTo(item.path)
    },
    navBack() {
      wx.navigateBack()
    },
    parseImage(imageKey) {
      return 'https://winery-1257413599.cos.ap-beijing.myqcloud.com/' + imageKey
    }
  },
  created () {

    let pages = getCurrentPages()
    // console.log(pages)
    let currPage = null
    if (pages.length) {
      currPage = pages[pages.length - 1]
    }
    console.log("currPage:", currPage)
  }
}
