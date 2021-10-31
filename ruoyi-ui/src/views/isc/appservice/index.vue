<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="应用名称">
        <el-input
          v-model="applicationName"
          disabled
          clearable
          size="small"
        />
      </el-form-item>
      </el-form-item>
      </el-form-item>
      <el-form-item label="启用状态" prop="enabled">
        <el-select v-model="queryParams.enabled" placeholder="请选择启用状态" clearable size="small">
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审核状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择审核状态" clearable size="small">
          <el-option
            v-for="dict in dict.type.sys_audit_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['isc:appservice:add']"
        >申请</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['isc:appservice:edit']"
        >修改</el-button>
        </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['isc:appservice:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['isc:appservice:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="appserviceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" :selectable="selectable"/>
      <el-table-column label="应用服务ID" align="center" prop="appServiceId" v-if="false"/>
      <el-table-column label="服务名称" align="left" prop="serviceName" />
      <el-table-column label="虚拟地址" align="left" prop="virtualAddr">
        <template slot-scope="scope">
          <copy-tag :data="scope.row.virtualAddr"/>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_audit_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="启用状态" align="center" prop="enabled" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.enabled"/>
        </template>
      </el-table-column>
      <el-table-column label="到期时间" align="center" prop="endTime" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
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
            @click="handleView(scope.row)"
            v-hasPermi="['isc:appservice:query']"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-time"
            @click="handleRenewal(scope.row)"
            v-hasPermi="['isc:appservice:edit']"
            v-if="scope.row.status == 1"
          >续期</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['isc:appservice:edit']"
            v-if="scope.row.status != 0"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['isc:appservice:remove']"
          >删除</el-button>
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

    <!-- 添加或修改应用服务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="服务名称" prop="serviceId">
          <!-- :disable-branch-nodes="true" 只能选叶子节点 -->
          <treeselect 
            v-model="form.serviceId" 
            :options="appServiceOptions" 
            :show-count="true" 
            :normalizer="normalizer" 
            :disable-branch-nodes="true" 
            :disabled="this.applyType !== 0"
            placeholder="请选择服务" 
            style="width:215px"/>
        </el-form-item>
        <el-form-item label="启用状态" v-if="this.applyType === 0">
          <el-radio-group v-model="form.enabled">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="申请时长" prop="renewalDuration" v-if="!(this.form.status == 1 && this.applyType == 2)">
          <el-select v-model="form.renewalDuration" placeholder="请选择申请时长" clearable size="small">
            <el-option key="1" label="1(月)" value="1"/>
            <el-option key="3" label="3(月)" value="3"/>
            <el-option key="6" label="6(月)" value="6"/>
            <el-option key="9" label="9(月)" value="9"/>
            <el-option key="12" label="12(月)" value="12"/>
            <el-option key="24" label="24(月)" value="24"/>
          </el-select>
        </el-form-item>
        <el-form-item label="每日配额" prop="quotaDays" v-if="this.applyType === 0 || this.applyType === 2">
          <el-input-number v-model="form.quotaDays" :step="1000" placeholder="请输入日配额" />
        </el-form-item>
        <el-form-item label="小时配额" prop="quotaHours" v-if="this.applyType === 0 || this.applyType === 2">
          <el-input-number v-model="form.quotaHours" :step="100" placeholder="请输入小时配额" />
        </el-form-item>
        <el-form-item label="分钟配额" prop="quotaMinutes" v-if="this.applyType === 0 || this.applyType === 2">
          <el-input-number v-model="form.quotaMinutes" :step="10" placeholder="请输入分钟配额" />
        </el-form-item>
        <el-form-item label="每秒配额" prop="quotaSeconds" v-if="this.applyType === 0 || this.applyType === 2">
          <el-input-number v-model="form.quotaSeconds" placeholder="请输入秒配额" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    
    <!-- 查看应用服务信息对话框 -->
    <el-dialog title="应用服务查看" :visible.sync="openView" width="500px" append-to-body>
      <el-card shadow="never" style="margin-bottom: 20px">
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">服务名称</el-col>
          <el-col :span="18" class="col-content">{{this.form.serviceName}}</el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">虚拟地址</el-col>
          <el-col :span="18" class="col-content">{{this.form.virtualAddr}}</el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">审核状态</el-col>
          <el-col :span="18" class="col-content">
            <dict-tag :options="dict.type.sys_audit_status" :value="this.form.status"/>
          </el-col>
        </el-row>
        <el-row :gutter="20" v-if="this.form.status == 2">
          <el-col :span="6" class="col-title">审核意见</el-col>
          <el-col :span="18" class="col-content">{{this.form.auditMind}}</el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">启用状态</el-col>
          <el-col :span="18" class="col-content">
            <dict-tag :options="dict.type.sys_normal_disable" :value="this.form.enabled"/>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">到期时间</el-col>
          <el-col :span="18" class="col-content">
            <span>{{ parseTime(this.form.endTime, '{y}-{m}-{d}') }}</span>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">每日配额</el-col>
          <el-col :span="18" class="col-content" v-text="this.form.quotaDays"/>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">小时配额</el-col>
          <el-col :span="18" class="col-content" v-text="this.form.quotaHours"/>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">分钟配额</el-col>
          <el-col :span="18" class="col-content" v-text="this.form.quotaMinutes"/>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">每秒配额</el-col>
          <el-col :span="18" class="col-content" v-text="this.form.quotaSeconds"/>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">更新人</el-col>
          <el-col :span="18" class="col-content">{{this.form.updateBy}}</el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">更新时间</el-col>
          <el-col :span="18" class="col-content">{{parseTime(this.form.updateTime)}}</el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6" class="col-title">备注</el-col>
          <el-col :span="18" class="col-content">{{this.form.remark}}</el-col>
        </el-row>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAppservice, getAppservice, delAppservice, addAppservice, updateAppservice, treeselect } from "@/api/isc/appservice";
import { getApplication } from "@/api/isc/application";
import Treeselect from "@riophae/vue-treeselect";
import CopyTag from '@/components/CopyTag';
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "Appservice",
  dicts: ['sys_normal_disable', 'sys_audit_status'],
  components: { Treeselect, CopyTag },
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
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 应用服务表格数据
      appserviceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示查看弹出层
      openView: false,
      // 应用服务树
      appServiceOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        enabled: undefined,
        status: undefined,
        applicationId: undefined,
        orderByColumn: 'update_time',
        isAsc: 'desc'
      },
      applicationName: undefined,
      applicationId: undefined,
      applyType: undefined,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        serviceId: [
          { required: true, message: "服务ID不能为空", trigger: "blur" }
        ],
        enabled: [
          { required: true, message: "启用状态不能为空", trigger: "blur" }
        ],
        renewalDuration: [
          { required: true, message: "申请时长不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    const applicationId = this.$route.params && this.$route.params.applicationId;
    this.applicationId = applicationId;
    this.queryParams.applicationId = applicationId;
    this.getList(applicationId);
    this.getApplicationName(applicationId);
    this.getTreeselect(applicationId);
  },
  methods: {
    /** 查询应用服务列表 */
    getList() {
      this.loading = true;
      listAppservice(this.queryParams).then(response => {
        this.appserviceList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    selectable(row, index) {
      return row.status != 0;
    },
    /** 获取应用信息 */
    getApplicationName(applicationId) {
      getApplication(applicationId).then(response => {
        this.applicationName = response.data.applicationName;
      });
    },
    /** 获取应用服务分类树结构 */
    getTreeselect(applicationId) {
      treeselect(applicationId).then(response => {
        this.appServiceOptions = response.data;
      });
    },
    //树节点转换
    normalizer(node) {
      return {
        id: node.id,
        label: node.label,
        children: node.children,
        isDisabled: node.exist,
        type: node.type
      };
    },
    // 取消按钮
    cancel() {
      this.open = this.openView = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        appServiceId: undefined,
        serviceId: undefined,
        applicationId: this.applicationId,
        enabled: "0",
        status: [],
        renewalDuration: undefined,
        quotaDays: undefined,
        quotaHours: undefined,
        quotaMinutes: undefined,
        quotaSeconds: undefined,
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
      this.ids = selection.map(item => item.appServiceId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.applyType = 0;
      this.getTreeselect(this.applicationId);
      this.open = true;
      this.title = "添加应用服务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      this.applyType = 2;
      const appServiceId = row.appServiceId || this.ids
      getAppservice(appServiceId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.form.status = this.form.status.split(",");
        this.open = true;
        this.title = "修改应用服务";
      });
    },
    /** 续期按钮操作 */
    handleRenewal(row) {
      this.loading = true;
      this.reset();
      this.applyType = 1;
      const appServiceId = row.appServiceId || this.ids
      getAppservice(appServiceId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.form.remark = undefined;
        this.form.status = this.form.status.split(",");
        this.open = true;
        this.title = "应用服务续期";
      });
    },
    /** 查看按钮操作 */
    handleView(row) {
      this.loading = true;
      this.reset();
      const appServiceId = row.appServiceId;
      getAppservice(appServiceId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.form.status = this.form.status.split(",");
        this.openView = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          this.form.applyType = this.applyType;
          this.form.status = this.form.status.join(",");
          if (this.form.appServiceId != null) {
            updateAppservice(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addAppservice(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const appServiceIds = row.appServiceId || this.ids;
      this.$confirm('是否确认删除应用服务编号为"' + appServiceIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.loading = true;
          return delAppservice(appServiceIds);
        }).then(() => {
          this.loading = false;
          this.getList();
          this.msgSuccess("删除成功");
      }).finally(() => {
          this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.$download.excel('/isc/appservice/export', this.queryParams);
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