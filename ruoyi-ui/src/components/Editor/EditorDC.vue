<template>
  <div>
    <quill-editor v-model="editorValue" :options="editorOption"></quill-editor>
  </div>
</template>

<script>
import {getToken} from "@/utils/auth";
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";
import "quill/dist/quill.bubble.css";
import {Quill, quillEditor} from "vue-quill-editor";
import {container, ImageExtend, QuillWatch} from "@/components/EditorDc/index";

Quill.register("modules/ImageExtend", ImageExtend);
export default {
  name: "EditorDC",
  props: {
    value: {
      type: [String, Number],
      default: "",
    },
  },
  components: {quillEditor},
  data() {
    return {
      editorValue: "",
      // 富文本框参数设置
      editorOption: {
        placeholder: "请输入内容",
        modules: {
          ImageExtend: {
            // 可选参数 是否显示上传进度和提示语
            loading: true,
            // 图片参数名
            name: "file",
            // 可选参数 图片大小，单位为M，1M = 1024kb
            size: 2,
            // 服务器地址, 如果action为空，则采用base64插入图片
            action: process.env.VUE_APP_BASE_API + "/common/upload",
            // 可选 可上传的图片格式
            // Optional, uploadable image format
            accept: "image/jpg, image/png, image/gif, image/jpeg, image/bmp, image/x-icon",
            // response 为一个函数用来获取服务器返回的具体图片地址
            // 例如服务器返回 {code: 200; data:{ url: 'baidu.com'}}
            response: (res) => {
              return res.data.url;
            },
            // 可选参数 设置请求头部
            headers: (xhr) => {
              xhr.setRequestHeader("Authorization", 'Bearer ' + getToken());
            },
            // 图片超过大小的回调
            sizeError: () => {
              alert("图片大小超过 2 M");
            },
            // 可选参数 自定义开始上传触发事件
            start: () => {
            },
            // 可选参数 自定义上传结束触发的事件，无论成功或者失败
            end: () => {
            },
            // 可选参数 上传失败触发的事件
            error: () => {
            },
            // 可选参数  上传成功触发的事件
            success: () => {
            },
            // 可选参数 选择图片触发，也可用来设置头部，但比headers多了一个参数，可设置formData
            change: (xhr, formData) => {
            },
          },
          toolbar: {
            // container为工具栏，此次引入了全部工具栏，也可自行配置
            container: container,
            handlers: {
              image: function () {
                // 劫持原来的图片点击按钮事件
                QuillWatch.emit(this.quill.id);
              },
            },
          },
        },
      },
    };
  },
  watch: {
    value: {
      handler(val) {
        this.editorValue = val;
      },
      immediate: true,
    },
    editorValue(val) {
      this.$emit("input", val);
    },
  },
  methods: {
    onEditorChange({editor, html, text}) {
      this.editorValue = html;
    },
  }
};
</script>

<style>
.editor, .ql-toolbar {
  white-space: pre-wrap !important;
  line-height: normal !important;
}

.quill-img {
  display: none;
}

.ql-editor {
  height: 200px;
}

.ql-snow .ql-tooltip[data-mode="link"]::before {
  content: "请输入链接地址:";
}

.ql-snow .ql-tooltip.ql-editing a.ql-action::after {
  border-right: 0px;
  content: "保存";
  padding-right: 0px;
}

.ql-snow .ql-tooltip[data-mode="video"]::before {
  content: "请输入视频地址:";
}

.ql-snow .ql-picker.ql-size .ql-picker-label::before,
.ql-snow .ql-picker.ql-size .ql-picker-item::before {
  content: "14px";
}

.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="small"]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="small"]::before {
  content: "10px";
}

.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="large"]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="large"]::before {
  content: "18px";
}

.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="huge"]::before,
.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="huge"]::before {
  content: "32px";
}

.ql-snow .ql-picker.ql-header .ql-picker-label::before,
.ql-snow .ql-picker.ql-header .ql-picker-item::before {
  content: "文本";
}

.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="1"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="1"]::before {
  content: "标题1";
}

.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="2"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="2"]::before {
  content: "标题2";
}

.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="3"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="3"]::before {
  content: "标题3";
}

.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="4"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="4"]::before {
  content: "标题4";
}

.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="5"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="5"]::before {
  content: "标题5";
}

.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="6"]::before,
.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="6"]::before {
  content: "标题6";
}

.ql-snow .ql-picker.ql-font .ql-picker-label::before,
.ql-snow .ql-picker.ql-font .ql-picker-item::before {
  content: "标准字体";
}

.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="serif"]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="serif"]::before {
  content: "衬线字体";
}

.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="monospace"]::before,
.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="monospace"]::before {
  content: "等宽字体";
}
</style>
