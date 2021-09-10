<template>
  <div>
    <template v-if="data">
      <span :title="data" v-text="handleData()"/>
      <i class="el-icon-copy-document" @click="handleCopy()" title="复制"/>
    </template>
  </div>
</template>

<script>
export default {
  name: "CopyTag",
  props: {
    data: {
      required: true,
      type: String
    },
    maxLength: {
      type: Number,
      default: 40,
    }
  },
  methods: {
    handleData() {
      return this.data && this.data.length > this.maxLength ? this.data.substring(0, this.maxLength - 3) + '...' : this.data;
    },
    handleCopy() {
      let oInput = document.createElement('input')
      oInput.value = this.data;
      document.body.appendChild(oInput)
      oInput.select()
      document.execCommand('copy')
      this.$message({
        message: '复制成功',
        type: 'success'
      })
      oInput.remove()
    },
  },
};
</script>
<style lang="scss" scoped>
  .el-icon-copy-document {
    color: #4A8DFF; 
    cursor:pointer;
    margin-left: 5px;
  }
</style>