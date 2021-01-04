import Vue from 'vue'


Vue.filter('getImageForKey', val => {
  return "https://winery-1257413599.cos.ap-beijing.myqcloud.com/" + val
})

Vue.filter('getImage200', val => {
  return "https://winery-1257413599.cos.ap-beijing.myqcloud.com/" + val + "?imageMogr2/thumbnail/!200x200r/|imageMogr2/gravity/center/crop/200x200/interlace/0"
})



