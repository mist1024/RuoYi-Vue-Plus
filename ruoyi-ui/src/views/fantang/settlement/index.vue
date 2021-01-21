<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="结算日期" prop="settleAt">
        <el-date-picker clearable size="small" style="width: 200px"
                        v-model="queryParams.settleAt"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择结算日期">
        </el-date-picker>
      </el-form-item>
      <!--      <el-form-item label="应收" prop="payable">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.payable"-->
      <!--          placeholder="请输入应收"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <!--      <el-form-item label="实收" prop="receipts">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.receipts"-->
      <!--          placeholder="请输入实收"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="结算类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择结算类型" clearable size="small">
          <el-option
              v-for="item in typeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="退款总额" prop="refund">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.refund"-->
      <!--          placeholder="请输入退款总额"-->
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
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          type="primary"-->
      <!--          icon="el-icon-plus"-->
      <!--          size="mini"-->
      <!--          @click="handleAdd"-->
      <!--          v-hasPermi="['fantang:settlement:add']"-->
      <!--        >新增-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
            type="success"
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['fantang:settlement:edit']"
        >开票
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['fantang:settlement:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['fantang:settlement:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="settlementList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="结算 id" align="center" prop="settleId" v-if="false"/>
      <el-table-column label="姓名" align="center" prop="name"/>
      <el-table-column label="应收" align="center" prop="payable"/>
      <el-table-column label="实收" align="center" prop="receipts"/>
      <el-table-column label="结算类型" align="center" prop="type"/>
      <el-table-column label="结算日期" align="center" prop="settleAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.settleAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['fantang:settlement:edit']"
          >开票
          </el-button>
          <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['fantang:settlement:remove']"
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

    <!-- 添加或修改结算管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <!--        <el-form-item label="记录清单" prop="list">-->
        <!--          <el-input v-model="form.list" placeholder="请输入记录清单" />-->
        <!--        </el-form-item>-->
        <!--        <el-form-item label="结算总价" prop="price">-->
        <!--          <el-input v-model="form.price" placeholder="请输入结算总价" />-->
        <!--        </el-form-item>-->
        <el-form-item label="应收" prop="payable">
          <el-input v-model="form.payable" placeholder="请输入应收" :disabled="true"/>
        </el-form-item>
        <el-form-item label="实收" prop="receipts">
          <el-input v-model="form.receipts" placeholder="请输入实收" :disabled="true"/>
        </el-form-item>
        <el-form-item label="结算类型" prop="type">
          <el-input v-model="form.type" placeholder="请输入结算类型" :disabled="true"/>
        </el-form-item>
        <el-form-item label="发票名" prop="invoiceName">
          <el-input v-model="form.invoiceName" placeholder="请输入发票名"/>
        </el-form-item>
        <el-form-item label="税号" prop="taxId">
          <el-input v-model="form.taxId" placeholder="请输入税号"/>
        </el-form-item>
        <el-form-item label="发票号" prop="invoiceNum">
          <el-input v-model="form.invoiceNum" placeholder="请输入发票号"/>
        </el-form-item>
        <el-form-item label="开票类型" prop="invoiceType">
          <el-select v-model="form.invoiceType" placeholder="请选择开票类型">
            <el-option
                v-for="item in invoiceTypeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <!--        <el-form-item label="退款总额" prop="refund">-->
        <!--          <el-input v-model="form.refund" placeholder="请输入退款总额"/>-->
        <!--        </el-form-item>-->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addSettlement, delSettlement, exportSettlement, listSettlement} from "@/api/fantang/settlement";
import {getSettlement} from "../../../api/fantang/settlement";
import {addToInvoice} from "../../../api/fantang/invoice";

export default {
  name: "Settlement",
  components: {},
  data() {
    return {
      invoiceTypeOptions: [{
        value: 1,
        label: '开票'
      }, {
        value: 2,
        label: '挂账'
      }],
      typeOptions: [{
        value: '现金',
        label: '现金'
      }, {
        value: '预付款冲减',
        label: '预付款冲减'
      }, {
        value: '银行汇款',
        label: '银行汇款'
      }, {
        value: '在线支付',
        label: '在线支付'
      }, {
        value: '挂账',
        label: '挂账'
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
      // 结算管理表格数据
      settlementList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        settleAt: undefined,
        price: undefined,
        payable: undefined,
        receipts: undefined,
        type: undefined,
        refund: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        list: [
          {required: true, message: "记录清单不能为空", trigger: "blur"}
        ],
        price: [
          {required: true, message: "结算总价不能为空", trigger: "blur"}
        ],
        payable: [
          {required: true, message: "应收不能为空", trigger: "blur"}
        ],
        receipts: [
          {required: true, message: "实收不能为空", trigger: "blur"}
        ],
        type: [
          {required: true, message: "结算类型不能为空", trigger: "change"}
        ],
        refund: [
          {required: true, message: "退款总额不能为空", trigger: "blur"}
        ],
        invoiceName: [
          {required: true, message: "发票名不能为空", trigger: "blur"}
        ],
        taxId: [
          {required: true, message: "税号不能为空", trigger: "blur"}
        ],
        invoiceNum: [
          {required: true, message: "发票号不能为空", trigger: "blur"}
        ],
        invoiceType: [
          {required: true, message: "开票类型不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询结算管理列表 */
    getList() {
      this.loading = true;
      listSettlement(this.queryParams).then(response => {
        this.settlementList = response.rows;
        this.total = response.total;
        this.loading = false;
        console.log(response.rows)
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
        settleId: undefined,
        patientId: undefined,
        settleAt: undefined,
        opera: undefined,
        list: undefined,
        price: undefined,
        payable: undefined,
        receipts: undefined,
        type: undefined,
        flag: undefined,
        refund: undefined,
        invoiceName: undefined,
        taxId: undefined,
        invoiceNum: undefined,
        invoiceType: undefined,
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
      this.ids = selection.map(item => item.settleId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加结算管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const settleId = row.settleId || this.ids
      console.log(row)
      getSettlement(settleId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "开票";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.settleId != null) {
            addToInvoice(this.form).then(response => {
              this.msgSuccess("已开票");
              this.open = false;
              this.getList();
            })
          } else {
            addSettlement(this.form).then(response => {
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
      const settleIds = row.settleId || this.ids;
      this.$confirm('是否确认删除结算管理编号为"' + settleIds + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delSettlement(settleIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有结算管理数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportSettlement(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
