<template>
  <div class="container">
    <div class="center">
      <div class="header">
        <input
          type="text"
          class="ipt"
          v-model="queryParam.nodeName"
          @keydown.enter="search"
          placeholder="搜索灯芯绒试试"
        />
        <p class="tips">
          <span>搜索示例:灯芯绒</span>
        </p>
      </div>
      <div class="canvas">
        <Keywords v-show="type === 1" @searchData="searching" @windowResize="windowResize"></Keywords>
        <Charts ref="charts" v-show="type === 2" :chartList="listdata"/>
      </div>
    </div>
  </div>
</template>
<script>
import Keywords from "./Keywords";
import Charts from "./Charts";
import {listNodeWithChildren} from '@/api/system/node'
import {Message} from "element-ui";

export default {
  name: "KnowledgeGraph",
  components: {
    Keywords,
    Charts,
  },
  mixins: [],
  props: {},
  data() {
    return {
      type: 1,
      listdata: [],
      queryParam: {
        nodeName: ""
      }
    };
  },
  computed: {},
  methods: {
    searching(text) {
      this.queryParam.nodeName = text;
      this.getDataList();
      if (this.listdata != null) {
        this.type = 2
      }
    },
    //获取后端数据
    getDataList() {
      console.log(this.queryParam)
      listNodeWithChildren(this.queryParam).then(response => {
        this.listdata = [].concat(response.data)
      });
    },

    /**
     * 搜索方法,text为空则为点击类别操作,不为空则为输入框搜索
     */
    search() {
      if (this.queryParam.nodeName == "") {
        this.type = 1
      } else {
        this.getDataList();
        if (this.listdata.length == 0) {
          this.type = 2
        }
      }
    },
    /**
     * 窗体大小变化回调
     */
    windowResize() {
      const charts = this.$refs.charts
      charts && charts.myChart && charts.myChart.resize()
    },
  },
  mounted() {
    this.type = 1
  }
}
</script>
<style scoped>
.container {
  overflow: hidden;
  position: relative;
  min-height: 100vh;
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background-color: #0b2838;
}

input {
  outline: none;
  padding-left: 24px;
  padding-right: 41px;
}

input::-webkit-input-placeholder {
  color: #ccc;
  font-size: 14px;
}

input::-moz-placeholder {
  color: #ccc;
  font-size: 14px;
}

input:-ms-input-placeholder {
  color: #ccc;
  font-size: 14px;
}

.canvas {
  margin-top: 10px;
  height: calc(100vh - 110px);
}

.center {
  position: relative;
  min-width: 1200px;
  margin: 0 auto;
  width: 100%;
}

.header {
  padding-top: 30px;
}

.ipt {
  border: 1px solid #9093c7;
  border-radius: 20px;
  width: 374px;
  height: 44px;
  line-height: 44px;
  box-sizing: border-box;
  color: #555;
  background-color: #fff;
  background-image: none;
  border: 1px solid #ccc;
}

.tips {
  font-size: 14px;
  color: #ccc;
  line-height: 17px;
  margin-top: 5px;
}
</style>
