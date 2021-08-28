<template>
  <div class="background">
    <img :src="imgSrc" width="100%" height="100%" alt=""/>
  </div>
</template>

<script>
import config from '../../package.json'

export default {
  name: "index",
  data() {
    return {
      // 版本号
      version: config.version,
      imgSrc: require('../../src/assets/images/background1.jpg')
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
  },
};
</script>

<style scoped lang="scss">
.background {
  width: 100%;
  height: 100%; /**宽高100%是为了图片铺满屏幕 */
  z-index: -1;
  position: absolute;
}
</style>

