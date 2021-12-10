<template>
  <div>
    <svg-icon icon-class="question" @click="goto" />
  </div>
</template>

<script>
export default {
  name: 'wildDoc',
  data() {
    return {
      url: 'https://gitee.com/JavaLionLi/wild-vue-plus/wikis/pages'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
