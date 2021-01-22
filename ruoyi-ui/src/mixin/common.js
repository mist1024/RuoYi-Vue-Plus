import Vue from 'vue'
import {imageBaseUrl} from "@/settings";
import {listDept} from "@/api/system/dept";


Vue.filter('getImageForKey', val => {
  return imageBaseUrl + val
})

Vue.filter('getImage200', val => {
  return imageBaseUrl + val + "?imageMogr2/thumbnail/!200x200r/|imageMogr2/gravity/center/crop/200x200/interlace/0"
})


// 定义一个混入对象
export const CommonMixin = {
  data() {
    return {

      deptOptions: []
    }
  },
  created: function () {
    listDept().then(r => {
      this.deptOptions = r.data.filter(x => x.parentId === 300)
    })
  },
  methods: {

    // 商户类型字典翻译
    deptFormat(row, column) {

      if (this.deptOptions.length < 1) {
        return ''
      }
      const dept = this.deptOptions.filter(x => x.deptId === row.deptId)[0]
      return dept.deptName
    }

  }
}

