<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="对应发票id" prop="invoiceId">
        <el-input
          v-model="queryParams.invoiceId"
          placeholder="请输入对应发票id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="回款日期" prop="returnAt">
        <el-date-picker clearable size="small"
          v-model="queryParams.returnAt"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择回款日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="回款金额" prop="returnPrice">
        <el-input
          v-model="queryParams.returnPrice"
          placeholder="请输入回款金额"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="余额" prop="balancePrice">
        <el-input
          v-model="queryParams.balancePrice"
          placeholder="请输入余额"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作员" prop="opera">
        <el-input
          v-model="queryParams.opera"
          placeholder="请输入操作员"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="凭证的图片url" prop="voucherUrl">
        <el-input
          v-model="queryParams.voucherUrl"
          placeholder="请输入凭证的图片url"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否完成回款标志" prop="returnFlag">
        <el-input
          v-model="queryParams.returnFlag"
          placeholder="请输入是否完成回款标志"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['fantang:returnManage:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['fantang:returnManage:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['fantang:returnManage:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['fantang:returnManage:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="returnManageList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" v-if="false"/>
      <el-table-column label="对应发票id" align="center" prop="invoiceId" />
      <el-table-column label="回款日期" align="center" prop="returnAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.returnAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="回款金额" align="center" prop="returnPrice" />
      <el-table-column label="余额" align="center" prop="balancePrice" />
      <el-table-column label="操作员" align="center" prop="opera" />
      <el-table-column label="凭证的图片url" align="center" prop="voucherUrl" />
      <el-table-column label="是否完成回款标志" align="center" prop="returnFlag" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['fantang:returnManage:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['fantang:returnManage:remove']"
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

    <!-- 添加或修改回款登记对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="对应发票id" prop="invoiceId">
          <el-input v-model="form.invoiceId" placeholder="请输入对应发票id" />
        </el-form-item>
        <el-form-item label="回款日期" prop="returnAt">
          <el-date-picker clearable size="small"
            v-model="form.returnAt"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择回款日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="回款金额" prop="returnPrice">
          <el-input v-model="form.returnPrice" placeholder="请输入回款金额" />
        </el-form-item>
        <el-form-item label="余额" prop="balancePrice">
          <el-input v-model="form.balancePrice" placeholder="请输入余额" />
        </el-form-item>
        <el-form-item label="操作员" prop="opera">
          <el-input v-model="form.opera" placeholder="请输入操作员" />
        </el-form-item>
        <el-form-item label="凭证的图片url" prop="voucherUrl">
          <el-input v-model="form.voucherUrl" placeholder="请输入凭证的图片url" />
        </el-form-item>
        <el-form-item label="是否完成回款标志" prop="returnFlag">
          <el-input v-model="form.returnFlag" placeholder="请输入是否完成回款标志" />
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
import { listReturnManage, getReturnManage, delReturnManage, addReturnManage, updateReturnManage, exportReturnManage } from "@/api/fantang/returnManage";

export default {
  name: "ReturnManage",
  components: {
  },
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
      // 回款登记表格数据
      returnManageList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        invoiceId: undefined,
        returnAt: undefined,
        returnPrice: undefined,
        balancePrice: undefined,
        opera: undefined,
        voucherUrl: undefined,
        returnFlag: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询回款登记列表 */
    getList() {
      this.loading = true;
      listReturnManage(this.queryParams).then(response => {
        this.returnManageList = response.rows;
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
        id: undefined,
        invoiceId: undefined,
        returnAt: undefined,
        returnPrice: undefined,
        balancePrice: undefined,
        opera: undefined,
        voucherUrl: undefined,
        returnFlag: undefined
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加回款登记";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getReturnManage(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改回款登记";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateReturnManage(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addReturnManage(this.form).then(response => {
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
      this.$confirm('是否确认删除回款登记编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delReturnManage(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有回款登记数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportReturnManage(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
