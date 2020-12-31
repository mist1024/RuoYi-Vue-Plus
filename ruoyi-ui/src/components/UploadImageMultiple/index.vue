<template>
  <div class="component-upload-image">

    <el-upload
      class="upload-demo"
      accept="image/*"
      :action="uploadImgUrl"
      auto-upload
      :headers="headers"
      :on-success="handleUploadSuccess"
      :before-upload="handleBeforeUpload"
      :on-error="handleUploadError"
      :on-preview="handlePreview"
      :on-remove="handleRemove"
      name="file"
      multiple
      :limit="5"
      :file-list="fileList"
      :on-exceed="handleExceed"
      list-type="picture">

      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，最大限制5张</div>
    </el-upload>
  </div>
</template>

<script>
import {getToken} from "@/utils/auth";
import {Message} from "element-ui";

export default {
  components: {},
  data() {
    return {
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传的图片服务器地址
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      fileList: [],
    };
  },
  props: {
    value: {
      type: String,
      default: "",
    },
  },
  filters: {
    image(value) {
      return process.env.VUE_APP_BASE_API + "/common/file?fileName=" + value
    }
  },
  computed: {



  },
  methods: {
    handleUploadSuccess(res, file, fileList) {


      fileList = fileList.filter(x => x.response)

      let temp = fileList.map(x => {
        return  x.response.fileName
      })

      let result = temp.join(',')

      console.log(result)

      this.$emit("input", result);
      this.loading.close();
    },
    handleBeforeUpload() {
      this.loading = this.$loading({
        lock: true,
        text: "上传中",
        background: "rgba(0, 0, 0, 0.7)",
      });
    },
    handleUploadError() {
      this.$message({
        type: "error",
        message: "上传失败",
      });
      this.loading.close();
    },
    handleRemove(file, fileList) {
      let temp = fileList.map(x => {
        return  x.response.fileName
      })

      let result = temp.join(',')
      this.$emit("input",result);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(file,fileList) {
      Message({
        message: "超出上传数量限制",
        type: 'error'
      })


    }
  },
  watch: {},
};
</script>

<style scoped lang="scss">
.avatar {
  width: 100%;
  height: 100%;
}
</style>
