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
      url: 'https://www.wolai.com/qianlan/q5N7mQ3Z5JVp58VL8KEWbD?theme=light'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
