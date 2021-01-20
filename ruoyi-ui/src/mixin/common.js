import Vue from 'vue'
import {imageBaseUrl} from "@/settings";


Vue.filter('getImageForKey', val => {
  return imageBaseUrl + val
})

Vue.filter('getImage200', val => {
  return imageBaseUrl + val + "?imageMogr2/thumbnail/!200x200r/|imageMogr2/gravity/center/crop/200x200/interlace/0"
})



