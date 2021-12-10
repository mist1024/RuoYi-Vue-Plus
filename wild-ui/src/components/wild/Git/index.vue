<template>
  <div>
    <svg-icon icon-class="github" @click="goto" />
  </div>
</template>

<script>
export default {
  name: 'wildGit',
  data() {
    return {
      url: 'https://gitee.com/JavaLionLi/wild-vue-plus'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
