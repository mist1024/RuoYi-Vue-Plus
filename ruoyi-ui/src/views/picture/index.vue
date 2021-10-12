<template>
  <div>
    <div class="tips">请根据姿势来判断人物的情绪为积极的还是消极情绪，麻烦认真选择，这很重要，十分感谢！！！</div>
    <div class="tips">若图片重复出现，直接舍弃掉即可</div>
    <div class="border">
      <el-carousel
        :interval="0"
        trigger="click"
        type="card"
        arrow="always"
        indicator-position="none"
        height="400px"
        :loop="false"
        @change="changeClick"
      >
        <el-carousel-item v-for="item in pic" :key="item.picId" class="img-box">
          <img v-lazy="item.picUrl"/>
        </el-carousel-item>
      </el-carousel>
    </div>

    <div class="selection">
      <el-radio-group v-model="radio" @change="clickRadio">
        <el-radio :label="0">积极</el-radio>
        <el-radio :label="1">消极</el-radio>
        <el-radio :label="2">无法确定</el-radio>
      </el-radio-group>
    </div>

    <div class="button">
      <span class="button-left"><el-button size="mini" type="primary" @click="clickCommitButton">提交</el-button></span>
      <el-button size="mini" type="danger" icon="el-icon-delete" @click="clickDrop">舍弃</el-button>
    </div>
  </div>
</template>


<script>
import {listPictureWithCondition, commitPic} from '@/api/demo/picture'

export default {
  data() {
    return {
      pic: [],
      selectionArr: [],
      radio: -1,
      cur: 0
    };
  },
  methods: {
    // 点击丢弃按钮
    clickDrop() {
      this.resetPic();
      this.getPictureList();
    },
    resetPic() {
      this.pic = []
      this.radio = 3;
      this.selectionArr = [];
      this.cur = 0;
    },
    getPictureList() {
      listPictureWithCondition().then(res => {
        this.pic = res;
        console.log("图片集是", res)
      })
    },
    //点击提交按钮
    clickCommitButton() {
      if (this.selectionArr.length != 0) {
        this.$confirm('十分感谢您的帮助，麻烦确认你的选择是否为认真选择，确定完成请按确定！', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          console.log(this.selectionArr)
          commitPic(this.selectionArr).then(res => {
            console.log(res)
            this.msgSuccess("修改成功");
            this.resetPic();
            this.getPictureList();
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消提交'
          });
        });
      } else {
        this.$message.error('您还未选择任何图片，无法提交');
      }
    },

    //点击选项按钮
    clickRadio() {
      let commit = {
        picId: this.pic[this.cur].picId,
        picSelection: this.radio
      }
      console.log(commit)
      this.pic[this.cur].picSelection = this.radio
      this.selectionArr.push(commit)
    },

    //更换图片
    changeClick(cur) {
      this.radio = 3
      console.log("当前cur是：" + cur)
      this.cur = cur
      this.radio = this.pic[cur].picSelection
    },
  },
  created() {
    this.getPictureList();
  }
};
</script>


<style>
.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

.border {
  text-align: center;
  margin: 0 auto;
  width: 1200px;
}

.border img {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  width: 100%;
  height: 100%;
  /* margin:auto; */
}

.border img[lazy="loading"] {
  display: block;
  width: 50px;
  height: 50px;
  margin: auto auto;
}

.selection {
  margin-top: 60px;
  text-align: center;
}

.button {
  margin-top: 50px;
  text-align: center;
}

.button-left {
  margin-right: 30px;
}

.tips {
  text-align: center;
  margin-top: 20px;
  margin-bottom: 20px;
  color: #e52241;
  font-size: 22px;
}

</style>
