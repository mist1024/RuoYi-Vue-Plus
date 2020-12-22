<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="补贴类型" prop="subsidyType">
        <el-select v-model="queryParams.subsidyType" placeholder="请选择补贴类型" clearable size="small">
          <el-option
            v-for="item in subsidyTypeOptions"
            :key="item.dictValue"
            :label="item.dictLabel"
            :value="item.dictValue">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="收支类型" prop="incomeType">
        <el-select v-model="queryParams.incomeType" placeholder="请选择收支类型" clearable size="small">
          <el-option
            v-for="item in incomeTypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="金额" prop="price">
        <el-input
          v-model="queryParams.price"
          placeholder="请输入金额"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="消费日期" prop="consumAt">
        <el-date-picker clearable size="small" style="width: 200px"
                        v-model="queryParams.consumAt"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择消费日期">
        </el-date-picker>
      </el-form-item>
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
          v-hasPermi="['fantang:staffSubsidy:add']"
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
          v-hasPermi="['fantang:staffSubsidy:edit']"
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
          v-hasPermi="['fantang:staffSubsidy:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['fantang:staffSubsidy:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="staffSubsidyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="补贴流水 id" align="center" prop="subsidyId" v-if="false"/>
      <el-table-column label="补贴类型" align="center" prop="subsidyType" :formatter="subsidyTypeFormat"/>
      <el-table-column label="收支类型" align="center" prop="incomeType" :formatter="incomeTypeFormat"/>
      <el-table-column label="金额" align="center" prop="price"/>
      <el-table-column label="消费日期" align="center" prop="consumAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.consumAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['fantang:staffSubsidy:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['fantang:staffSubsidy:remove']"
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

    <!-- 添加或修改补贴流水查看对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="补贴类型" prop="subsidyType">
          <el-select v-model="form.subsidyType" placeholder="请选择补贴类型">
            <el-option label="请选择字典生成" value=""/>
          </el-select>
        </el-form-item>
        <el-form-item label="收支类型" prop="incomeType">
          <el-select v-model="form.incomeType" placeholder="请选择收支类型">
            <el-option label="请选择字典生成" value=""/>
          </el-select>
        </el-form-item>
        <el-form-item label="金额" prop="price">
          <el-input v-model="form.price" placeholder="请输入金额"/>
        </el-form-item>
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
  addStaffSubsidy,
  delStaffSubsidy,
  exportStaffSubsidy,
  getStaffSubsidy,
  listStaffSubsidy,
  updateStaffSubsidy
} from "@/api/fantang/staffSubsidy";

export default {
  name: "StaffSubsidy",
  components: {},
  data() {
    return {
      subsidyTypeOptions: [],
      incomeTypeOptions: [{
        value: 1,
        label: '发放'
      }, {
        value: 2,
        label: '消费'
      }],
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
      // 补贴流水查看表格数据
      staffSubsidyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        subsidyType: null,
        incomeType: null,
        price: null,
        consumAt: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        subsidyType: [
          {required: true, message: "补贴类型不能为空", trigger: "change"}
        ],
        incomeType: [
          {required: true, message: "收支类型不能为空", trigger: "change"}
        ],
        price: [
          {required: true, message: "金额不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("ft_subsidy").then(response => {
      this.subsidyTypeOptions = response.data;
    });
  },
  methods: {
    /** 查询补贴流水查看列表 */
    getList() {
      this.loading = true;
      listStaffSubsidy(this.queryParams).then(response => {
        this.staffSubsidyList = response.rows;
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
        subsidyId: null,
        staffId: null,
        subsidyType: null,
        incomeType: null,
        price: null,
        consumAt: null,
        orderId: null
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
      this.ids = selection.map(item => item.subsidyId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加补贴流水查看";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const subsidyId = row.subsidyId || this.ids
      getStaffSubsidy(subsidyId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改补贴流水查看";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.subsidyId != null) {
            updateStaffSubsidy(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStaffSubsidy(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    // 补贴类型回显
    subsidyTypeFormat(row, column) {
      return this.selectDictLabel(this.subsidyTypeOptions, row.subsidyType);
    },
    // 收支类型回显
    incomeTypeFormat(row) {
      if (row.incomeType === '1' || row.incomeType === 1) {
        return "发放";
      } else if (row.incomeType === '2' || row.incomeType === 2) {
        return "消费";
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const subsidyIds = row.subsidyId || this.ids;
      this.$confirm('是否确认删除补贴流水查看编号为"' + subsidyIds + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delStaffSubsidy(subsidyIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有补贴流水查看数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportStaffSubsidy(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
