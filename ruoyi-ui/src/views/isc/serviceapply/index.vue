<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="申请类型" prop="applyType">
        <el-select v-model="queryParams.applyType" placeholder="请选择申请类型" clearable size="small">
          <el-option
            v-for="dict in applyTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审核状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择审核状态" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-view"
          size="mini"
          :disabled="multiple"
          @click="handleAudit"
          v-hasPermi="['isc:serviceapply:audit']"
        >批量审核</el-button>
      </el-col>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['isc:serviceapply:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="serviceapplyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" :selectable="selectable"/>
      <el-table-column label="申请ID" align="center" prop="applyId" v-if="false"/>
      <el-table-column label="应用名称" align="left" prop="applicationName" />
      <el-table-column label="服务名称" align="left" prop="serviceName" />
      <el-table-column label="申请类型" align="center" prop="applyType" width="80">
        <template slot-scope="scope">
          <dict-tag :options="applyTypeOptions" :value="scope.row.applyType"/>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="status" width="80">
        <template slot-scope="scope">
          <dict-tag :options="statusOptions" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="续期时长(月)" align="center" prop="renewalDuration" width="100"/>
      <el-table-column label="天配额" align="center" prop="quotaDays" width="80"/>
      <el-table-column label="小时配额" align="center" prop="quotaHours" width="80"/>
      <el-table-column label="分钟配额" align="center" prop="quotaMinutes" width="80"/>
      <el-table-column label="秒配额" align="center" prop="quotaSeconds" width="70"/>
      <el-table-column label="更新者" align="center" prop="updateBy" width="100"/>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-info"
            @click="handleAuditSingle(scope.row, true)"
            v-hasPermi="['isc:serviceapply:query']"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleAuditSingle(scope.row, false)"
            v-if="scope.row.status == 0"
            v-hasPermi="['isc:serviceapply:audit']"
          >审核</el-button>
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

    <!-- 查看或审核服务申请信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-card shadow="never" v-if="this.show" style="margin-bottom: 20px">
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">应用名称</el-col>
          <el-col :span="18" class="col-content">{{this.viewData.applicationName}}</el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">服务名称</el-col>
          <el-col :span="18" class="col-content">{{this.viewData.serviceName}}</el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">申请类型</el-col>
          <el-col :span="18" class="col-content">
            <dict-tag :options="applyTypeOptions" :value="this.viewData.applyType"/>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">审核状态</el-col>
          <el-col :span="18" class="col-content">
            <dict-tag :options="statusOptions" :value="this.viewData.status"/>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">续期时长(月)</el-col>
          <el-col :span="18" class="col-content">{{this.viewData.renewalDuration}}</el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">每日配额</el-col>
          <el-col :span="18" class="col-content">{{this.viewData.quotaDays}}</el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">小时配额</el-col>
          <el-col :span="18" class="col-content">{{this.viewData.quotaHours}}</el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">分钟配额</el-col>
          <el-col :span="18" class="col-content">{{this.viewData.quotaMinutes}}</el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">每秒配额</el-col>
          <el-col :span="18" class="col-content">{{this.viewData.quotaSeconds}}</el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">提交人</el-col>
          <el-col :span="18" class="col-content">{{this.viewData.updateBy}}</el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">提交时间</el-col>
          <el-col :span="18" class="col-content">{{this.viewData.updateTime}}</el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">备注</el-col>
          <el-col :span="18" class="col-content">{{this.viewData.remark}}</el-col>
        </el-row>
      </el-card>
      <el-form ref="auditForm" :model="auditForm" :rules="rules" label-width="80px">
        <el-form-item label="审核意见" prop="remark">
          <el-input v-model="auditForm.remark" type="textarea" :disabled='this.view' placeholder="请输入审核意见"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="success" @click="submitForm(1)" icon="el-icon-check" v-if="!this.view">通 过</el-button>
        <el-button :loading="buttonLoading" type="warning" @click="submitForm(2)" icon="el-icon-close" v-if="!this.view">驳 回</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listServiceapply, getServiceapply, auditServiceapply } from "@/api/isc/serviceapply";

export default {
  name: "Serviceapply",
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 是否查看
      view: false,
      // 是否展示详情
      show: false,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 应用服务申请信息表格数据
      serviceapplyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 申请类型(0申请 1续期)字典
      applyTypeOptions: [],
      // 审核状态字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        applyType: undefined,
        status: undefined,
      },
      // 表单参数
      viewData: {},
      // 审核表单参数
      auditForm: {},
      // 表单校验
      rules: {
        remark: [
          { max: 200, message: '最大长度为200个字符', trigger: 'blur' }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("isc_apply_type").then(response => {
      this.applyTypeOptions = response.data;
    });
    this.getDicts("sys_audit_status").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询应用服务申请信息列表 */
    getList() {
      this.loading = true;
      listServiceapply(this.queryParams).then(response => {
        this.serviceapplyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
      this.resetAudit();
    },
    selectable(row, index) {
      return row.status == 0;
    },
    // 表单重置
    reset() {
      this.viewData = { };
    },
    // 审核表单重置
    resetAudit() {
      this.auditForm = {
        ids: [],
        status: "0",
        remark: undefined
      };
      this.resetForm("auditForm");
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
      this.ids = selection.map(item => item.applyId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 审核按钮操作 */
    handleAudit(row) {
      if(this.single) {
        this.open = true;
        this.title = "服务申请审核";
        this.resetAudit();
        this.show = false;
        this.auditForm.ids = this.ids;
        return;
      }
      this.handleAuditSingle(row, false);
    },
    handleAuditSingle(row, view) {
      this.loading = true;
      this.view = view;
      this.show = true;
      this.reset();
      const applyId = row.applyId || this.ids
      getServiceapply(applyId).then(response => {
        this.loading = false;
        this.viewData = response.data;
        this.open = true;
        if(view) {
          this.title = "服务申请查看";
        }else{
          this.title = "服务申请审核";
        }
        this.auditForm.ids = [applyId]
      });
    },
    /** 提交按钮 */
    submitForm(status) {
      this.auditForm.status = status;
      this.$refs["auditForm"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.auditForm.ids.length > 0) {
            auditServiceapply(this.auditForm).then(response => {
              this.msgSuccess("审核成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
              this.msgError("请选择记录");
              this.buttonLoading = false;
          }
        }
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.downLoadExcel('/isc/serviceapply/export', this.queryParams);
    }
  }
};
</script>
<style lang="scss" scoped>
     .el-card .el-row {
       font-size: 14px;
       line-height: 36px;
       vertical-align: middle;
     }
     .el-card .el-row .col-title {
       text-align: right;
       color: #606266;
       font-weight: bold;
     }
</style>