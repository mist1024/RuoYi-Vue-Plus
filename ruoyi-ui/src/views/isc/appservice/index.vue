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
            v-for="dict in enabledOptions"
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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['isc:appservice:add']"
        >新增</el-button>
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
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="应用服务ID" align="center" prop="serviceAppId" v-if="false"/>
      <el-table-column label="服务名称" align="center" prop="serviceName" />
      <el-table-column label="启用状态" align="center" prop="enabled">
        <template slot-scope="scope">
          <dict-tag :options="enabledOptions" :value="scope.row.enabled"/>
        </template>
      </el-table-column>
      <el-table-column label="申请类型" align="center" prop="applyType">
        <template slot-scope="scope">
          <dict-tag :options="applyTypeOptions" :value="scope.row.applyType"/>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="statusOptions" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="虚拟地址" align="center" prop="virtualAddr" />
      <el-table-column label="到期时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新者" align="center" prop="updateBy" />
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['isc:appservice:edit']"
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
          <treeselect v-model="form.serviceId" :options="appServiceOptions" :show-count="true" :normalizer="normalizer" 
            :disable-branch-nodes="true" placeholder="请选择服务" style="width:215px"/>
        </el-form-item>
        <el-form-item label="启用状态">
          <el-radio-group v-model="form.enabled">
            <el-radio
              v-for="dict in enabledOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="到期时间" prop="endTime">
          <el-date-picker clearable size="small"
            v-model="form.endTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择到期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="日配额" prop="quotaDays">
          <el-input-number v-model="form.quotaDays" step="1000" placeholder="请输入日配额" />
        </el-form-item>
        <el-form-item label="小时配额" prop="quotaHours">
          <el-input-number v-model="form.quotaHours" step="100" placeholder="请输入小时配额" />
        </el-form-item>
        <el-form-item label="分钟配额" prop="quotaMinutes">
          <el-input-number v-model="form.quotaMinutes" step="10" placeholder="请输入分钟配额" />
        </el-form-item>
        <el-form-item label="秒配额" prop="quotaSeconds">
          <el-input-number v-model="form.quotaSeconds" placeholder="请输入秒配额" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAppservice, getAppservice, delAppservice, addAppservice, updateAppservice, treeselect } from "@/api/isc/appservice";
import { getApplication } from "@/api/isc/application";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "Appservice",
  components: { Treeselect },
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
      // 启用状态字典
      enabledOptions: [],
      // 申请类型字典
      applyTypeOptions: [],
      // 审核状态字典
      statusOptions: [],
      // 应用服务树
      appServiceOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        enabled: undefined,
        status: undefined,
        applicationId: undefined
      },
      applicationName: undefined,
      applicationId: undefined,
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
      }
    };
  },
  created() {
    const applicationId = this.$route.params && this.$route.params.applicationId;
    this.applicationId = applicationId;
    this.queryParams.applicationId = applicationId;
    this.getList(applicationId);
    this.getDicts("sys_normal_disable").then(response => {
      this.enabledOptions = response.data;
    });
    this.getDicts("isc_apply_type").then(response => {
      this.applyTypeOptions = response.data;
    });
    this.getDicts("sys_audit_status").then(response => {
      this.statusOptions = response.data;
    });
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
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        serviceAppId: undefined,
        serviceId: undefined,
        applicationId: this.applicationId,
        enabled: "0",
        applyType: undefined,
        status: [],
        endTime: undefined,
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
      this.ids = selection.map(item => item.serviceAppId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加应用服务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const serviceAppId = row.serviceAppId || this.ids
      getAppservice(serviceAppId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.form.status = this.form.status.split(",");
        this.open = true;
        this.title = "修改应用服务";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          this.form.status = this.form.status.join(",");
          if (this.form.serviceAppId != null) {
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
      const serviceAppIds = row.serviceAppId || this.ids;
      this.$confirm('是否确认删除应用服务编号为"' + serviceAppIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.loading = true;
          return delAppservice(serviceAppIds);
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
      this.downLoadExcel('/isc/appservice/export', this.queryParams);
    }
  }
};
</script>
