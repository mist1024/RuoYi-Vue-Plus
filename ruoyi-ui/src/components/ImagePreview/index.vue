<template>
  <el-image
    :preview-src-list="realSrcList"
    :src="`${realSrc}`"
    :style="`width:${realWidth};height:${realHeight};`"
    fit="cover"
  >
    <div slot="error" class="image-slot">
      <i class="el-icon-picture-outline"></i>
    </div>
  </el-image>
</template>

<script>
import {listByIds} from "@/api/system/oss";

export default {
  name: "ImagePreview",
  props: {
    src: {
      type: String,
      default: ""
    },
    width: {
      type: [Number, String],
      default: ""
    },
    height: {
      type: [Number, String],
      default: ""
    }
  },
  data() {
    return {
      fileList: []
    }
  },
  watch: {
    src: {
      async handler(val) {
        if (val) {
          await listByIds(this.src).then(res => {
            res.data.forEach(item => {
              this.fileList.push(item.url);
            });
          })
        }
      },
      deep: true,
      immediate: true
    }
  },
  computed: {
    realSrc() {
      if (!this.fileList) {
        return;
      }
      return this.fileList[0];
    },
    realSrcList() {
      if (!this.fileList) {
        return;
      }
      return this.fileList;
    },
    realWidth() {
      return typeof this.width == "string" ? this.width : `${this.width}px`;
    },
    realHeight() {
      return typeof this.height == "string" ? this.height : `${this.height}px`;
    }
  },
};
</script>

<style lang="scss" scoped>
.el-image {
  border-radius: 5px;
  background-color: #ebeef5;
  box-shadow: 0 0 5px 1px #ccc;

  ::v-deep .el-image__inner {
    transition: all 0.3s;
    cursor: pointer;

    &:hover {
      transform: scale(1.2);
    }
  }

  ::v-deep .image-slot {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    color: #909399;
    font-size: 30px;
  }
}
</style>
