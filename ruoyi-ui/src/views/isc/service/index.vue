<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="服务名称" prop="serviceName">
        <el-input
          v-model="queryParams.serviceName"
          placeholder="请输入服务名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否在线" prop="onlineStatus">
        <el-select v-model="queryParams.onlineStatus" placeholder="请选择是否在线" clearable size="small">
          <el-option
            v-for="dict in onlineStatusOptions"
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
      <el-form-item label="服务状态" prop="enabled">
        <el-select v-model="queryParams.enabled" placeholder="请选择服务状态" clearable size="small">
          <el-option
            v-for="dict in enabledOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="服务分类" prop="cateFullPath">
        <treeselect v-model="queryParams.cateFullPath" :options="cateOptions" :show-count="true" :normalizer="normalizer" placeholder="请选择服务分类" style="width:215px"/>
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
          v-hasPermi="['isc:service:add']"
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
          v-hasPermi="['isc:service:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-view"
          size="mini"
          :disabled="multiple"
          @click="handleUpdate"
          v-hasPermi="['isc:service:audit']"
        >批量审核</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['isc:service:remove']"
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
          v-hasPermi="['isc:service:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="serviceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="服务ID" align="center" prop="serviceId" width="100"/>
      <el-table-column label="服务名称" align="left" prop="serviceName" />
      <el-table-column label="服务分类" align="left" prop="cateName" width="150"/>
      <el-table-column label="服务状态" align="center" prop="enabled" width="100">
        <template slot-scope="scope">
          <dict-tag :options="enabledOptions" :value="scope.row.enabled"/>
        </template>
      </el-table-column>
      <el-table-column label="是否在线" align="center" prop="onlineStatus" width="100">
        <template slot-scope="scope">
          <dict-tag :options="onlineStatusOptions" :value="scope.row.onlineStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <dict-tag :options="statusOptions" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" prop="updateBy"  width="150"/>
      <el-table-column label="更新时间" align="center" prop="updateTime"  width="150">
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
            @click="handleUpdate(scope.row)"
            v-hasPermi="['isc:service:query']"
          >查看</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['isc:service:audit']"
          >审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['isc:service:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['isc:service:remove']"
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

    <!-- 添加或修改服务信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="服务分类" prop="cateFullPath">
          <treeselect v-model="form.cateFullPath" :options="cateOptions" :show-count="true" :normalizer="normalizer" placeholder="请选择服务分类" />
        </el-form-item>
        <el-form-item label="服务名称" prop="serviceName">
          <el-input v-model="form.serviceName" placeholder="请输入服务名称" />
        </el-form-item>
        <el-form-item label="服务地址" prop="serviceAddr">
          <el-input v-model="form.serviceAddr" placeholder="请输入服务地址" />
        </el-form-item>
        <el-form-item label="探活地址" prop="probeActiveAddr">
          <el-input v-model="form.probeActiveAddr" placeholder="请输入探活地址" />
        </el-form-item>
        <el-form-item label="请求方式" prop="requestMethod">
          <el-radio-group v-model="form.requestMethod">
            <el-radio
              v-for="dict in requestMethodOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="跨域标志">
          <el-radio-group v-model="form.corsFlag">
            <el-radio
              v-for="dict in corsFlagOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="隐藏参数" prop="hiddenParams">
          <el-input v-model="form.hiddenParams" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="JSON文档" prop="apiDoc">
          <el-input v-model="form.apiDoc" type="textarea" placeholder="请输入内容" />
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
import { listService, getService, delService, addService, updateService } from "@/api/isc/service";
import { treeselect } from "@/api/isc/cate";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { downLoadExcel } from "@/utils/download";

export default {
  name: "Service",
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
      // 服务信息表格数据
      serviceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 跨域标志字典
      corsFlagOptions: [],
      // 是否在线字典
      onlineStatusOptions: [],
      // 审核状态字典
      statusOptions: [],
      // 服务状态字典
      enabledOptions: [],
      // 服务状态字典
      requestMethodOptions: [],
      //服务分类
      cateOptions:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        serviceName: undefined,
        onlineStatus: undefined,
        status: undefined,
        enabled: undefined,
        cateFullPath: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        serviceName: [
          { required: true, message: "服务名称不能为空", trigger: "blur" }
        ],
        serviceAddr: [
          { required: true, message: "服务地址不能为空", trigger: "blur" }
        ],
        probeActiveAddr: [
          { required: true, message: "探活地址不能为空", trigger: "blur" }
        ],
        requestMethod: [
          { required: true, message: "请求方式不能为空", trigger: "blur" }
        ],
        corsFlag: [
          { required: true, message: "跨域标志不能为空", trigger: "blur" }
        ],
        apiDoc: [
          { required: true, message: "JSON文档不能为空", trigger: "blur" }
        ],
        enabled: [
          { required: true, message: "服务状态不能为空", trigger: "blur" }
        ],
        cateFullPath: [
          { required: true, message: "服务分类不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getTreeselect();
    this.getDicts("sys_yes_no").then(response => {
      this.corsFlagOptions = response.data;
    });
    this.getDicts("isc_online_status").then(response => {
      this.onlineStatusOptions = response.data;
    });
    this.getDicts("sys_audit_status").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("sys_normal_disable").then(response => {
      this.enabledOptions = response.data;
    });
    this.getDicts("isc_request_method").then(response => {
      this.requestMethodOptions = response.data;
    });
  },
  methods: {
    /** 查询服务信息列表 */
    getList() {
      this.loading = true;
      listService(this.queryParams).then(response => {
        this.serviceList = response.rows;
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
        serviceId: undefined,
        serviceName: undefined,
        serviceAddr: undefined,
        probeActiveAddr: undefined,
        requestMethod: 'GET',
        remark: undefined,
        corsFlag: 'Y',
        hiddenParams: undefined,
        onlineStatus: undefined,
        status: undefined,
        apiDoc: undefined,
        auditMind: undefined,
        enabled: "0",
        cateFullPath: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
        delFlag: undefined
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
      this.ids = selection.map(item => item.serviceId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加服务信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const serviceId = row.serviceId || this.ids
      getService(serviceId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改服务信息";
      });
    },
    /** 查询服务分类树结构 */
    getTreeselect() {
      treeselect().then(response => {
        this.cateOptions = response.data;
      });
    },
    //树节点转换
    normalizer(node) {
      return {
        id: node.fullPath,
        label: node.label,
        children: node.children,
      }
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.serviceId != null) {
            updateService(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addService(this.form).then(response => {
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
      const serviceIds = row.serviceId || this.ids;
      this.$confirm('是否确认删除服务信息编号为"' + serviceIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.loading = true;
          return delService(serviceIds);
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
      downLoadExcel('/isc/service/export', this.queryParams);
    }
  }
};
</script>
