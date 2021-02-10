<template>
  <div>
    <svg-icon icon-class="question" @click="goto"/>
  </div>
</template>

<script>
export default {
  name: 'RuoYiDoc',
  data() {
    return {
      url: 'https://www.yuque.com/wbyzhg/manual/gniqvo'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
