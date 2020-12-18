<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="创建者openid" prop="openid">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.openid"-->
      <!--          placeholder="请输入创建者openid"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="联系邮箱" prop="email">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.email"-->
      <!--          placeholder="请输入联系邮箱"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="联系人" prop="personName">
        <el-input
          v-model="queryParams.personName"
          placeholder="请输入联系人姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号码" prop="mobile">
        <el-input
          v-model="queryParams.mobile"
          placeholder="请输入手机号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="酒庄名称" prop="wineryName">
        <el-input
          v-model="queryParams.wineryName"
          placeholder="请输入酒庄名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--      <el-form-item label="建立时间" prop="buildTime">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.buildTime"-->
      <!--          placeholder="请输入建立时间"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="酒庄地址" prop="region">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.region"-->
      <!--          placeholder="请输入酒庄地址"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="酒庄地址" prop="address">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.address"-->
      <!--          placeholder="请输入酒庄地址"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="总面积" prop="wineryArea">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.wineryArea"-->
      <!--          placeholder="请输入总面积"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="建筑面积" prop="buildArea">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.buildArea"-->
      <!--          placeholder="请输入建筑面积"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="酒庄现状" prop="wineryStatus">
        <el-select v-model="queryParams.wineryStatus" placeholder="请选择酒庄现状" clearable size="small">
          <el-option
            v-for="dict in wineryStatusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="面积" prop="plantArea">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.plantArea"-->
      <!--          placeholder="请输入面积"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="产量" prop="plantWeight">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.plantWeight"-->
      <!--          placeholder="请输入产量"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="土壤类型" prop="soilType">-->
      <!--        <el-select v-model="queryParams.soilType" placeholder="请选择土壤类型" clearable size="small">-->
      <!--          <el-option-->
      <!--            v-for="dict in soilTypeOptions"-->
      <!--            :key="dict.dictValue"-->
      <!--            :label="dict.dictLabel"-->
      <!--            :value="dict.dictValue"-->
      <!--          />-->
      <!--        </el-select>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="红葡萄品种" prop="redVariety">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.redVariety"-->
      <!--          placeholder="请输入红葡萄品种"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="白葡萄品种" prop="whiteVariety">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.whiteVariety"-->
      <!--          placeholder="请输入白葡萄品种"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="灌溉方式" prop="irrigationType">-->
      <!--        <el-select v-model="queryParams.irrigationType" placeholder="请选择灌溉方式" clearable size="small">-->
      <!--          <el-option-->
      <!--            v-for="dict in irrigationTypeOptions"-->
      <!--            :key="dict.dictValue"-->
      <!--            :label="dict.dictLabel"-->
      <!--            :value="dict.dictValue"-->
      <!--          />-->
      <!--        </el-select>-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="发酵工艺" prop="fermentationProcess">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.fermentationProcess"-->
      <!--          placeholder="请输入发酵工艺"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="热化容器" prop="container">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.container"-->
      <!--          placeholder="请输入热化容器"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="澄清方式" prop="clarificationMethod">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.clarificationMethod"-->
      <!--          placeholder="请输入澄清方式"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="年产量" prop="annualOutput">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.annualOutput"-->
      <!--          placeholder="请输入年产量"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="库存" prop="stock">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.stock"-->
      <!--          placeholder="请输入库存"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="酒桶数量" prop="bucketCount">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.bucketCount"-->
      <!--          placeholder="请输入酒桶数量"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="销售方式" prop="salesMode">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.salesMode"-->
      <!--          placeholder="请输入销售方式"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="获奖信息" prop="awardInformation">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.awardInformation"-->
      <!--          placeholder="请输入获奖信息"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="征集口号" prop="slogan">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.slogan"-->
      <!--          placeholder="请输入征集口号"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['winery:winery_company_record:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['winery:winery_company_record:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['winery:winery_company_record:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['winery:winery_company_record:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="winery_company_recordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="id" align="center" prop="id" v-if="false"/>
      <el-table-column label="状态" align="center" prop="status" :formatter="statusFormat"/>
      <el-table-column label="酒庄名称" align="center" prop="wineryName"/>
      <el-table-column label="联系人姓名" align="center" prop="personName"/>

      <el-table-column label="酒庄地址" align="center" prop="region" :formatter="addressFormat" width="180"/>
      <!--      <el-table-column label="创建者openid" align="center" prop="openid" />-->
      <el-table-column label="联系邮箱" align="center" prop="email" width="180"/>

      <el-table-column label="手机号码" align="center" prop="mobile" width="120"/>

      <el-table-column label="建立时间" align="center" prop="buildTime" width="120"/>

      <el-table-column label="总面积" align="center" prop="wineryArea"/>
      <el-table-column label="建筑面积" align="center" prop="buildArea"/>
      <el-table-column label="酒庄现状" align="center" prop="wineryStatus" :formatter="wineryStatusFormat" width="120"/>
      <el-table-column label="面积" align="center" prop="plantArea"/>
      <el-table-column label="产量" align="center" prop="plantWeight"/>
      <el-table-column label="土壤类型" align="center" prop="soilType" :formatter="soilTypeFormat"/>
      <!--      <el-table-column label="红葡萄品种" align="center" prop="redVariety" />-->
      <!--      <el-table-column label="白葡萄品种" align="center" prop="whiteVariety" />-->
      <!--      <el-table-column label="灌溉方式" align="center" prop="irrigationType" :formatter="irrigationTypeFormat" />-->
      <!--      <el-table-column label="发酵工艺" align="center" prop="fermentationProcess" />-->
      <!--      <el-table-column label="热化容器" align="center" prop="container" />-->
      <!--      <el-table-column label="澄清方式" align="center" prop="clarificationMethod" />-->
      <el-table-column label="年产量" align="center" prop="annualOutput"/>
      <el-table-column label="库存" align="center" prop="stock"/>
      <el-table-column label="酒桶数量" align="center" prop="bucketCount"/>
      <!--      <el-table-column label="主要产品定价" align="center" prop="mainPrice" />-->
      <!--      <el-table-column label="销售方式" align="center" prop="salesMode" />-->
      <!--      <el-table-column label="获奖种类" align="center" prop="awards" />-->
      <!--      <el-table-column label="获奖信息" align="center" prop="awardInformation" />-->
      <!--      <el-table-column label="征集口号" align="center" prop="slogan" />-->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['winery:winery_company_record:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['winery:winery_company_record:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改酒庄厂家登记记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>

      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <winery-company-record-form ref="recordForm"
                                    :form="form"
                                    :wineryStatusOptions="wineryStatusOptions"
                                    :soilTypeOptions="soilTypeOptions"
                                    :irrigationTypeOptions="irrigationTypeOptions"
                                    :statusOptions="statusOptions"/>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listWinery_company_record,
  getWinery_company_record,
  delWinery_company_record,
  addWinery_company_record,
  updateWinery_company_record,
  exportWinery_company_record
} from "@/api/winery/winery_company_record";
import WineryCompanyRecordForm from "@/views/winery/winery_company_record/WineryCompanyRecordForm";

export default {
  name: "Winery_company_record",
  components: {WineryCompanyRecordForm},
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 酒庄厂家登记记录表格数据
      winery_company_recordList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态字典
      statusOptions: [],
      // 酒庄现状字典
      wineryStatusOptions: [],
      // 土壤类型字典
      soilTypeOptions: [],
      // 灌溉方式字典
      irrigationTypeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        status: undefined,
        openid: undefined,
        email: undefined,
        personName: undefined,
        mobile: undefined,
        wineryName: undefined,
        buildTime: undefined,
        region: undefined,
        address: undefined,
        wineryArea: undefined,
        buildArea: undefined,
        wineryStatus: undefined,
        plantArea: undefined,
        plantWeight: undefined,
        soilType: undefined,
        redVariety: undefined,
        whiteVariety: undefined,
        irrigationType: undefined,
        fermentationProcess: undefined,
        container: undefined,
        clarificationMethod: undefined,
        annualOutput: undefined,
        stock: undefined,
        bucketCount: undefined,
        mainPrice: undefined,
        salesMode: undefined,
        awards: undefined,
        awardInformation: undefined,
        slogan: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        openid: [
          {required: true, message: "创建者openid不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("winery_company_status").then(response => {
      this.wineryStatusOptions = response.data;
    });
    this.getDicts("winery_soil_types").then(response => {
      this.soilTypeOptions = response.data;
    });
    this.getDicts("winery_irrigation_type").then(response => {
      this.irrigationTypeOptions = response.data;
    });
  },
  methods: {
    /** 查询酒庄厂家登记记录列表 */
    getList() {
      this.loading = true;
      listWinery_company_record(this.queryParams).then(response => {
        this.winery_company_recordList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 酒庄现状字典翻译
    wineryStatusFormat(row, column) {

      return row.wineryStatus;
    },
    // 土壤类型字典翻译
    soilTypeFormat(row, column) {
      return row.soilType;
    },
    // 灌溉方式字典翻译
    irrigationTypeFormat(row, column) {
      return row.irrigationType;
    },

    addressFormat(row, column) {
      return row.region + row.address
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        status: "0",
        createTime: undefined,
        updateTime: undefined,
        openid: undefined,
        email: undefined,
        personName: undefined,
        mobile: undefined,
        wineryName: undefined,
        buildTime: undefined,
        region: undefined,
        address: undefined,
        wineryArea: undefined,
        buildArea: undefined,
        wineryStatus: "0",
        plantArea: undefined,
        plantWeight: undefined,
        soilType: undefined,
        redVariety: undefined,
        whiteVariety: undefined,
        irrigationType: undefined,
        fermentationProcess: undefined,
        container: undefined,
        clarificationMethod: undefined,
        annualOutput: undefined,
        stock: undefined,
        bucketCount: undefined,
        mainPrice: undefined,
        salesMode: undefined,
        awards: undefined,
        awardInformation: undefined,
        slogan: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加酒庄厂家登记记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {

      this.reset();
      const id = row.id || this.ids
      getWinery_company_record(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改酒庄厂家登记记录";

      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {

        if (valid) {
          if (this.form.id != null) {
            updateWinery_company_record(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWinery_company_record(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除酒庄厂家登记记录编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delWinery_company_record(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有酒庄厂家登记记录数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportWinery_company_record(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
