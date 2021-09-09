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
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['isc:serviceapply:add']"
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
          v-hasPermi="['isc:serviceapply:edit']"
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
          v-hasPermi="['isc:serviceapply:remove']"
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
          v-hasPermi="['isc:serviceapply:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="serviceapplyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
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
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['isc:serviceapply:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['isc:serviceapply:remove']"
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

    <!-- 添加或修改应用服务申请信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="应用服务ID" prop="appServiceId">
          <el-input v-model="form.appServiceId" placeholder="请输入应用服务ID" />
        </el-form-item>
        <el-form-item label="续期时长" prop="renewalDuration">
          <el-input v-model="form.renewalDuration" placeholder="请输入续期时长" />
        </el-form-item>
        <el-form-item label="天配额" prop="quotaDays">
          <el-input v-model="form.quotaDays" placeholder="请输入天配额" />
        </el-form-item>
        <el-form-item label="小时配额" prop="quotaHours">
          <el-input v-model="form.quotaHours" placeholder="请输入小时配额" />
        </el-form-item>
        <el-form-item label="分钟配额" prop="quotaMinutes">
          <el-input v-model="form.quotaMinutes" placeholder="请输入分钟配额" />
        </el-form-item>
        <el-form-item label="秒配额" prop="quotaSeconds">
          <el-input v-model="form.quotaSeconds" placeholder="请输入秒配额" />
        </el-form-item>
        <el-form-item label="创建者" prop="createBy">
          <el-input v-model="form.createBy" placeholder="请输入创建者" />
        </el-form-item>
        <el-form-item label="创建时间" prop="createTime">
          <el-date-picker clearable size="small"
            v-model="form.createTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择创建时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="更新者" prop="updateBy">
          <el-input v-model="form.updateBy" placeholder="请输入更新者" />
        </el-form-item>
        <el-form-item label="更新时间" prop="updateTime">
          <el-date-picker clearable size="small"
            v-model="form.updateTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择更新时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="删除标志" prop="delFlag">
          <el-input v-model="form.delFlag" placeholder="请输入删除标志" />
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
  </div>
</template>

<script>
import { listServiceapply, getServiceapply, delServiceapply, addServiceapply, updateServiceapply } from "@/api/isc/serviceapply";

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
      form: {},
      // 表单校验
      rules: {
        appServiceId: [
          { required: true, message: "应用服务ID不能为空", trigger: "blur" }
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
    },
    // 表单重置
    reset() {
      this.form = {
        applyId: undefined,
        appServiceId: undefined,
        applyType: undefined,
        status: "0",
        auditMind: undefined,
        renewalDuration: undefined,
        quotaDays: undefined,
        quotaHours: undefined,
        quotaMinutes: undefined,
        quotaSeconds: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
        delFlag: undefined,
        remark: undefined
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
      this.ids = selection.map(item => item.applyId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加应用服务申请信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const applyId = row.applyId || this.ids
      getServiceapply(applyId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改应用服务申请信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.applyId != null) {
            updateServiceapply(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addServiceapply(this.form).then(response => {
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
      const applyIds = row.applyId || this.ids;
      this.$confirm('是否确认删除应用服务申请信息编号为"' + applyIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.loading = true;
          return delServiceapply(applyIds);
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
      this.downLoadExcel('/isc/serviceapply/export', this.queryParams);
    }
  }
};
</script>
