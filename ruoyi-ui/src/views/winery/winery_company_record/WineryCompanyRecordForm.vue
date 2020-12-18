/**
*
* @author tottimctj
* @since 2020-12-18
*/
<template>

  <div>
    <el-form-item label="状态">
      <el-radio-group v-model="form.status">
        <el-radio
          v-for="dict in statusOptions"
          :key="dict.dictValue"
          :label="dict.dictValue"
        >{{dict.dictLabel}}
        </el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="创建者openid" prop="openid">
      <el-input v-model="form.openid" placeholder="请输入创建者openid"/>
    </el-form-item>
    <el-form-item label="联系邮箱" prop="email">
      <el-input v-model="form.email" placeholder="请输入联系邮箱"/>
    </el-form-item>
    <el-form-item label="联系人姓名" prop="personName">
      <el-input v-model="form.personName" placeholder="请输入联系人姓名"/>
    </el-form-item>
    <el-form-item label="手机号码" prop="mobile">
      <el-input v-model="form.mobile" placeholder="请输入手机号码"/>
    </el-form-item>
    <el-form-item label="酒庄名称" prop="wineryName">
      <el-input v-model="form.wineryName" placeholder="请输入酒庄名称"/>
    </el-form-item>
    <el-form-item label="建立时间" prop="buildTime">
      <el-input v-model="form.buildTime" placeholder="请输入建立时间"/>
    </el-form-item>
    <el-form-item label="酒庄地址" prop="region">
      <el-input v-model="form.region" placeholder="请输入酒庄地址"/>
    </el-form-item>
    <el-form-item label="酒庄地址" prop="address">
      <el-input v-model="form.address" placeholder="请输入酒庄地址"/>
    </el-form-item>
    <el-form-item label="总面积" prop="wineryArea">
      <el-input v-model="form.wineryArea" placeholder="请输入总面积"/>
    </el-form-item>
    <el-form-item label="建筑面积" prop="buildArea">
      <el-input v-model="form.buildArea" placeholder="请输入建筑面积"/>
    </el-form-item>

    <el-form-item label="酒庄现状" prop="wineryStatus">
      <el-select v-model="form.wineryStatus" placeholder="请选择灌溉方式">
        <el-option
          v-for="dict in wineryStatusOptions"
          :key="dict.dictValue"
          :label="dict.dictLabel"
          :value="dict.dictLabel"
        ></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="面积" prop="plantArea">
      <el-input v-model="form.plantArea" placeholder="请输入面积"/>
    </el-form-item>
    <el-form-item label="产量" prop="plantWeight">
      <el-input v-model="form.plantWeight" placeholder="请输入产量"/>
    </el-form-item>
    <el-form-item label="土壤类型" prop="soilType">
      <el-select v-model="form.soilType" placeholder="请选择土壤类型">
        <el-option
          v-for="dict in soilTypeOptions"
          :key="dict.dictValue"
          :label="dict.dictLabel"
          :value="dict.dictLabel"
        ></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="红葡萄品种" prop="redVariety">
      <el-row v-for="(item,index) in redVarietyList" :key="index" type="flex" justify="start">
        <el-col :span="4">{{item.name}}</el-col>
        <el-col :span="5">面积：{{item.msg.area}}</el-col>
        <el-col :span="6">最大树龄:{{item.msg.treeMaxOld}}</el-col>
        <el-col :span="5">树龄:{{item.msg.treeOld}}</el-col>
      </el-row>
    </el-form-item>

    <el-form-item label="白葡萄品种" prop="whiteVariety">
      <el-row v-for="(item,index) in whiteVarietyList" :key="index">
        <el-col :span="4">{{item.name}}</el-col>
        <el-col :span="5">面积：{{item.msg.area}}</el-col>
        <el-col :span="6">最大树龄:{{item.msg.treeMaxOld}}</el-col>
        <el-col :span="5">树龄:{{item.msg.treeOld}}</el-col>
      </el-row>
    </el-form-item>

    <el-form-item label="灌溉方式" prop="irrigationType">
      <el-select v-model="form.irrigationType" placeholder="请选择灌溉方式">
        <el-option
          v-for="dict in irrigationTypeOptions"
          :key="dict.dictValue"
          :label="dict.dictLabel"
          :value="dict.dictLabel"
        ></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="发酵工艺" prop="fermentationProcess">
      <el-input v-model="form.fermentationProcess" placeholder="请输入发酵工艺"/>
    </el-form-item>
    <el-form-item label="热化容器" prop="container">
      <el-input v-model="form.container" placeholder="请输入热化容器"/>
    </el-form-item>
    <el-form-item label="澄清方式" prop="clarificationMethod">
      <el-input v-model="form.clarificationMethod" placeholder="请输入澄清方式"/>
    </el-form-item>
    <el-form-item label="年产量" prop="annualOutput">
      <el-input v-model="form.annualOutput" placeholder="请输入年产量"/>
    </el-form-item>
    <el-form-item label="库存" prop="stock">
      <el-input v-model="form.stock" placeholder="请输入库存"/>
    </el-form-item>
    <el-form-item label="酒桶数量" prop="bucketCount">
      <el-input v-model="form.bucketCount" placeholder="请输入酒桶数量"/>
    </el-form-item>
    <el-form-item label="主要产品定价" prop="mainPrice">
      <el-input v-model="form.mainPrice" type="textarea" placeholder="请输入内容"/>
    </el-form-item>
    <el-form-item label="销售方式" prop="salesMode">
      <el-input v-model="form.salesMode" placeholder="请输入销售方式"/>
    </el-form-item>
    <el-form-item label="获奖种类" prop="awards">
      <el-input v-model="form.awards" type="textarea" placeholder="请输入内容"/>
    </el-form-item>
    <el-form-item label="获奖信息" prop="awardInformation">
      <el-input v-model="form.awardInformation" placeholder="请输入获奖信息"/>
    </el-form-item>
    <el-form-item label="征集口号" prop="slogan">
      <el-input v-model="form.slogan" placeholder="请输入征集口号"/>
    </el-form-item>
  </div>
</template>

<script>
export default {
  name: "WineryCompanyRecordForm",
  props: {
    form: {
      required: true,
      type: Object
    },
    statusOptions: {
      required: true,
      type: Array
    },
    wineryStatusOptions: {
      required: true,
      type: Array
    },
    soilTypeOptions: {
      required: true,
      type: Array
    },
    irrigationTypeOptions: {
      required: true,
      type: Array
    }
  },
  computed: {
    redVarietyList() {

      if (!this.redVariety.checked) {
        return []
      }

      return this.redVariety.params.filter(x => x.isChecked)

    },

    whiteVarietyList() {

      if (!this.whiteVariety.checked) {
        return []
      }

      return this.whiteVariety.params.filter(x => x.isChecked)

    },

    redVariety() {
      if (!this.form.redVariety) {
        return []
      }
      return JSON.parse(this.form.redVariety)
    },
    whiteVariety() {
      if (!this.form.whiteVariety) {
        return []
      }
      return JSON.parse(this.form.whiteVariety)
    }
  },

  data() {
    return {

      // 表单校验
      rules: {
        openid: [
          {required: true, message: "创建者openid不能为空", trigger: "blur"}
        ],
      }
    };
  },

  methods: {}
}
</script>

<style scoped>

</style>
