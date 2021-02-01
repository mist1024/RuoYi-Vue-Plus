<template>
  <div>
    <svg-icon icon-class="documentation" @click="goto"/>
  </div>
</template>

<script>
export default {
  name: 'RuoYiGit',
  data() {
    return {
      url: 'https://www.yuque.com/wbyzhg/manual/mgrtz2'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
