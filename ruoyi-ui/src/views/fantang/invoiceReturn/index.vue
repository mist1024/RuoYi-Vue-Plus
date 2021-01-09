<!--<template>-->
<!--  <div class="app-container">-->
<!--    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">-->
<!--      <el-form-item label="对应发票id" prop="invoiceId">-->
<!--        <el-input-->
<!--          v-model="queryParams.invoiceId"-->
<!--          placeholder="请输入对应发票id"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="回款日期" prop="returnAt">-->
<!--        <el-date-picker clearable size="small"-->
<!--          v-model="queryParams.returnAt"-->
<!--          type="date"-->
<!--          value-format="yyyy-MM-dd"-->
<!--          placeholder="选择回款日期">-->
<!--        </el-date-picker>-->
<!--      </el-form-item>-->
<!--      <el-form-item label="回款金额" prop="returnPrice">-->
<!--        <el-input-->
<!--          v-model="queryParams.returnPrice"-->
<!--          placeholder="请输入回款金额"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="余额" prop="balancePrice">-->
<!--        <el-input-->
<!--          v-model="queryParams.balancePrice"-->
<!--          placeholder="请输入余额"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="操作员" prop="opera">-->
<!--        <el-input-->
<!--          v-model="queryParams.opera"-->
<!--          placeholder="请输入操作员"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="凭证的图片url" prop="voucherUrl">-->
<!--        <el-input-->
<!--          v-model="queryParams.voucherUrl"-->
<!--          placeholder="请输入凭证的图片url"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item label="是否完成回款标志" prop="returnFlag">-->
<!--        <el-input-->
<!--          v-model="queryParams.returnFlag"-->
<!--          placeholder="请输入是否完成回款标志"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>-->
<!--        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>-->
<!--      </el-form-item>-->
<!--    </el-form>-->

<!--    <el-row :gutter="10" class="mb8">-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          icon="el-icon-plus"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--          v-hasPermi="['fantang:invoiceReturn:add']"-->
<!--        >新增</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="success"-->
<!--          icon="el-icon-edit"-->
<!--          size="mini"-->
<!--          :disabled="single"-->
<!--          @click="handleUpdate"-->
<!--          v-hasPermi="['fantang:invoiceReturn:edit']"-->
<!--        >修改</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="danger"-->
<!--          icon="el-icon-delete"-->
<!--          size="mini"-->
<!--          :disabled="multiple"-->
<!--          @click="handleDelete"-->
<!--          v-hasPermi="['fantang:invoiceReturn:remove']"-->
<!--        >删除</el-button>-->
<!--      </el-col>-->
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['fantang:invoiceReturn:export']"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
<!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
<!--    </el-row>-->

<!--    <el-table v-loading="loading" :data="invoiceReturnList" @selection-change="handleSelectionChange">-->
<!--      <el-table-column type="selection" width="55" align="center" />-->
<!--      <el-table-column label="id" align="center" prop="id" v-if="false"/>-->
<!--      <el-table-column label="对应发票id" align="center" prop="invoiceId" />-->
<!--      <el-table-column label="回款日期" align="center" prop="returnAt" width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <span>{{ parseTime(scope.row.returnAt, '{y}-{m}-{d}') }}</span>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="回款金额" align="center" prop="returnPrice" />-->
<!--      <el-table-column label="余额" align="center" prop="balancePrice" />-->
<!--      <el-table-column label="操作员" align="center" prop="opera" />-->
<!--      <el-table-column label="凭证的图片url" align="center" prop="voucherUrl" />-->
<!--      <el-table-column label="是否完成回款标志" align="center" prop="returnFlag" />-->
<!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(scope.row)"-->
<!--            v-hasPermi="['fantang:invoiceReturn:edit']"-->
<!--          >修改</el-button>-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['fantang:invoiceReturn:remove']"-->
<!--          >删除</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--    </el-table>-->
<!--    -->
<!--    <pagination-->
<!--      v-show="total>0"-->
<!--      :total="total"-->
<!--      :page.sync="queryParams.pageNum"-->
<!--      :limit.sync="queryParams.pageSize"-->
<!--      @pagination="getList"-->
<!--    />-->

<!--    &lt;!&ndash; 添加或修改回款登记对话框 &ndash;&gt;-->
<!--    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>-->
<!--      <el-form ref="form" :model="form" :rules="rules" label-width="80px">-->
<!--        <el-form-item label="对应发票id" prop="invoiceId">-->
<!--          <el-input v-model="form.invoiceId" placeholder="请输入对应发票id" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="回款日期" prop="returnAt">-->
<!--          <el-date-picker clearable size="small"-->
<!--            v-model="form.returnAt"-->
<!--            type="date"-->
<!--            value-format="yyyy-MM-dd"-->
<!--            placeholder="选择回款日期">-->
<!--          </el-date-picker>-->
<!--        </el-form-item>-->
<!--        <el-form-item label="回款金额" prop="returnPrice">-->
<!--          <el-input v-model="form.returnPrice" placeholder="请输入回款金额" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="余额" prop="balancePrice">-->
<!--          <el-input v-model="form.balancePrice" placeholder="请输入余额" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="操作员" prop="opera">-->
<!--          <el-input v-model="form.opera" placeholder="请输入操作员" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="凭证的图片url" prop="voucherUrl">-->
<!--          <el-input v-model="form.voucherUrl" placeholder="请输入凭证的图片url" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="是否完成回款标志" prop="returnFlag">-->
<!--          <el-input v-model="form.returnFlag" placeholder="请输入是否完成回款标志" />-->
<!--        </el-form-item>-->
<!--      </el-form>-->
<!--      <div slot="footer" class="dialog-footer">-->
<!--        <el-button type="primary" @click="submitForm">确 定</el-button>-->
<!--        <el-button @click="cancel">取 消</el-button>-->
<!--      </div>-->
<!--    </el-dialog>-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--import { listInvoiceReturn, getInvoiceReturn, delInvoiceReturn, addInvoiceReturn, updateInvoiceReturn, exportInvoiceReturn } from "@/api/fantang/invoiceReturn";-->

<!--export default {-->
<!--  name: "InvoiceReturn",-->
<!--  components: {-->
<!--  },-->
<!--  data() {-->
<!--    return {-->
<!--      // 遮罩层-->
<!--      loading: true,-->
<!--      // 选中数组-->
<!--      ids: [],-->
<!--      // 非单个禁用-->
<!--      single: true,-->
<!--      // 非多个禁用-->
<!--      multiple: true,-->
<!--      // 显示搜索条件-->
<!--      showSearch: true,-->
<!--      // 总条数-->
<!--      total: 0,-->
<!--      // 回款登记表格数据-->
<!--      invoiceReturnList: [],-->
<!--      // 弹出层标题-->
<!--      title: "",-->
<!--      // 是否显示弹出层-->
<!--      open: false,-->
<!--      // 查询参数-->
<!--      queryParams: {-->
<!--        pageNum: 1,-->
<!--        pageSize: 10,-->
<!--        invoiceId: undefined,-->
<!--        returnAt: undefined,-->
<!--        returnPrice: undefined,-->
<!--        balancePrice: undefined,-->
<!--        opera: undefined,-->
<!--        voucherUrl: undefined,-->
<!--        returnFlag: undefined-->
<!--      },-->
<!--      // 表单参数-->
<!--      form: {},-->
<!--      // 表单校验-->
<!--      rules: {-->
<!--      }-->
<!--    };-->
<!--  },-->
<!--  created() {-->
<!--    this.getList();-->
<!--  },-->
<!--  methods: {-->
<!--    /** 查询回款登记列表 */-->
<!--    getList() {-->
<!--      this.loading = true;-->
<!--      listInvoiceReturn(this.queryParams).then(response => {-->
<!--        this.invoiceReturnList = response.rows;-->
<!--        this.total = response.total;-->
<!--        this.loading = false;-->
<!--      });-->
<!--    },-->
<!--    // 取消按钮-->
<!--    cancel() {-->
<!--      this.open = false;-->
<!--      this.reset();-->
<!--    },-->
<!--    // 表单重置-->
<!--    reset() {-->
<!--      this.form = {-->
<!--        id: undefined,-->
<!--        invoiceId: undefined,-->
<!--        returnAt: undefined,-->
<!--        returnPrice: undefined,-->
<!--        balancePrice: undefined,-->
<!--        opera: undefined,-->
<!--        voucherUrl: undefined,-->
<!--        returnFlag: undefined-->
<!--      };-->
<!--      this.resetForm("form");-->
<!--    },-->
<!--    /** 搜索按钮操作 */-->
<!--    handleQuery() {-->
<!--      this.queryParams.pageNum = 1;-->
<!--      this.getList();-->
<!--    },-->
<!--    /** 重置按钮操作 */-->
<!--    resetQuery() {-->
<!--      this.resetForm("queryForm");-->
<!--      this.handleQuery();-->
<!--    },-->
<!--    // 多选框选中数据-->
<!--    handleSelectionChange(selection) {-->
<!--      this.ids = selection.map(item => item.id)-->
<!--      this.single = selection.length!==1-->
<!--      this.multiple = !selection.length-->
<!--    },-->
<!--    /** 新增按钮操作 */-->
<!--    handleAdd() {-->
<!--      this.reset();-->
<!--      this.open = true;-->
<!--      this.title = "添加回款登记";-->
<!--    },-->
<!--    /** 修改按钮操作 */-->
<!--    handleUpdate(row) {-->
<!--      this.reset();-->
<!--      const id = row.id || this.ids-->
<!--      getInvoiceReturn(id).then(response => {-->
<!--        this.form = response.data;-->
<!--        this.open = true;-->
<!--        this.title = "修改回款登记";-->
<!--      });-->
<!--    },-->
<!--    /** 提交按钮 */-->
<!--    submitForm() {-->
<!--      this.$refs["form"].validate(valid => {-->
<!--        if (valid) {-->
<!--          if (this.form.id != null) {-->
<!--            updateInvoiceReturn(this.form).then(response => {-->
<!--              this.msgSuccess("修改成功");-->
<!--              this.open = false;-->
<!--              this.getList();-->
<!--            });-->
<!--          } else {-->
<!--            addInvoiceReturn(this.form).then(response => {-->
<!--              this.msgSuccess("新增成功");-->
<!--              this.open = false;-->
<!--              this.getList();-->
<!--            });-->
<!--          }-->
<!--        }-->
<!--      });-->
<!--    },-->
<!--    /** 删除按钮操作 */-->
<!--    handleDelete(row) {-->
<!--      const ids = row.id || this.ids;-->
<!--      this.$confirm('是否确认删除回款登记编号为"' + ids + '"的数据项?', "警告", {-->
<!--          confirmButtonText: "确定",-->
<!--          cancelButtonText: "取消",-->
<!--          type: "warning"-->
<!--        }).then(function() {-->
<!--          return delInvoiceReturn(ids);-->
<!--        }).then(() => {-->
<!--          this.getList();-->
<!--          this.msgSuccess("删除成功");-->
<!--        })-->
<!--    },-->
<!--    /** 导出按钮操作 */-->
<!--    handleExport() {-->
<!--      const queryParams = this.queryParams;-->
<!--      this.$confirm('是否确认导出所有回款登记数据项?', "警告", {-->
<!--          confirmButtonText: "确定",-->
<!--          cancelButtonText: "取消",-->
<!--          type: "warning"-->
<!--        }).then(function() {-->
<!--          return exportInvoiceReturn(queryParams);-->
<!--        }).then(response => {-->
<!--          this.download(response.msg);-->
<!--        })-->
<!--    }-->
<!--  }-->
<!--};-->
<!--</script>-->
<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
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
          v-hasPermi="['fantang:invoice:add']"
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
          v-hasPermi="['fantang:invoice:edit']"
        >回款登记
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['fantang:invoice:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['fantang:invoice:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="invoiceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="id" align="center" prop="id" v-if="false"/>
      <el-table-column label="发票名" align="center" prop="invoiceName"/>
      <el-table-column label="发票号" align="center" prop="invoiceNum"/>
      <el-table-column label="税号" align="center" prop="taxId"/>
      <el-table-column label="收款方式" align="center" prop="collectionType"/>
      <el-table-column label="开票类型" align="center" prop="invoiceType" :formatter="formatInvoiceType"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['fantang:invoice:edit']"
          >回款登记
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['fantang:invoice:remove']"
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

    <!-- 添加或修改财务收费开票对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addInvoice, delInvoice, exportInvoice, getInvoice, listInvoice, updateInvoice} from "@/api/fantang/invoice";

export default {
  name: "Invoice",
  components: {},
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
      // 财务收费开票表格数据
      invoiceList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        // invoiceType:2,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    formatInvoiceType(row) {
      if (row.invoiceType === 1 || row.invoiceType === "1") {
        return "开票"
      } else {
        return "挂账"
      }
    },
    /** 查询财务收费开票列表 */
    getList() {
      this.loading = true;
      listInvoice(this.queryParams).then(response => {
        this.invoiceList = response.rows;
        this.total = response.total;
        this.loading = false;
        console.log(response.rows);
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
        invoiceUnit: undefined,
        invoiceId: undefined,
        createAt: undefined,
        drawer: undefined,
        collectionType: undefined,
        payable: undefined,
        receipts: undefined,
        voucherList: undefined
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
      this.title = "添加财务收费开票";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getInvoice(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改财务收费开票";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateInvoice(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInvoice(this.form).then(response => {
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
      this.$confirm('是否确认删除财务收费开票编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delInvoice(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有财务收费开票数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportInvoice(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
