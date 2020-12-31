<template>
  <div class="component-upload-image">
    <!--    <el-upload-->
    <!--      :action="uploadImgUrl"-->
    <!--      list-type="picture-card"-->
    <!--      :on-success="handleUploadSuccess"-->
    <!--      :before-upload="handleBeforeUpload"-->
    <!--      :on-error="handleUploadError"-->
    <!--      name="file"-->
    <!--      :show-file-list="false"-->
    <!--      :headers="headers"-->
    <!--      style="display: inline-block; vertical-align: top"-->
    <!--    >-->
    <!--      <img v-if="value" :src="value| image" class="avatar"/>-->
    <!--      <i v-else class="el-icon-plus avatar-uploader-icon"></i>-->
    <!--    </el-upload>-->

    <el-upload
      class="upload-demo"
      accept="image/*"
      :action="uploadImgUrl"
      :headers="headers"
      :on-success="handleUploadSuccess"
      :before-upload="handleBeforeUpload"
      :on-error="handleUploadError"
      :on-preview="handlePreview"
      :on-remove="handleRemove"
      name="file"
      :file-list="fileList"
      list-type="picture">
      <el-button size="small" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
    </el-upload>
  </div>
</template>

<script>
import {getToken} from "@/utils/auth";

export default {
  components: {},
  data() {
    return {
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload", // 上传的图片服务器地址
      headers: {
        Authorization: "Bearer " + getToken(),
      },
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
    fileList() {

      return this.value.split(',').map(x => {

        return process.env.VUE_APP_BASE_API + "/common/file?fileName=" + x
      })
    }
  },
  methods: {
    handleUploadSuccess(res) {
      this.$emit("input", res.fileName);
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
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
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
