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
      <el-table-column label="回款跟踪" align="center" prop="invoiceType" :formatter="formatInvoiceType"/>
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
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="10">
          <el-col :span="8">
            <el-form-item label="应收" prop="payable">
              <el-input v-model="form.payable" placeholder="请输入应收" :disabled="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="实收" prop="receipts">
              <el-input v-model="form.receipts" placeholder="请输入实收" :disabled="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="开票金额" prop="invoiceAmount">
              <el-input v-model="form.invoiceAmount" placeholder="请输入开票金额" :disabled="true"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="10">
          <el-col :span="24">
            <el-form-item label="发票名" prop="invoiceName">
              <el-input v-model="form.invoiceName" :disabled="true"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="发票号" prop="invoiceNum">
              <el-input v-model="form.invoiceNum" :disabled="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="税号" prop="taxId">
              <el-input v-model="form.taxId" :disabled="true"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="收款方式" prop="collectionType">
              <el-input v-model="form.collectionType" :disabled="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="回款跟踪" prop="invoiceType">
              <el-select v-model="form.invoiceType" :disabled="true">
                <el-option
                  v-for="item in invoiceTypeOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="回款金额" prop="returnPrice">
              <el-input v-model="form.returnPrice" placeholder="请输入回款金额"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="凭证图片" prop="voucherUrl">
              <uploadImage v-model="form.voucherUrl"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
      <el-table :data="returnManageList">
        <el-table-column label="id" align="center" prop="id" v-if="false"/>
        <el-table-column label="回款时间" align="center" prop="returnAt" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.returnAt, '{y}-{m}-{d} {h}:{m}:{s}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="回款金额" align="center" prop="returnPrice"/>
        <el-table-column label="待付金额" align="center" prop="balancePrice"/>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import {delInvoice, exportInvoice, getInvoice, listInvoice} from "../../../api/fantang/invoice";
import UploadImage from '../../../components/UploadImage';
import {addToReturn, getReturnByInvoice} from "../../../api/fantang/returnManage";

export default {
  name: "Invoice",
  components: {
    UploadImage,
  },
  data() {
    return {
      // 回款记录
      returnManageList: [],
      invoiceTypeOptions: [{
        value: 1,
        label: '已完成'
      }, {
        value: 2,
        label: '未完成'
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
      rules: {
        returnPrice: [
          {required: true, message: "回款金额不能为空", trigger: "blur"},
          {
            required: true,
            pattern: "",
            message: "请输入正确的开票金额",
            trigger: "blur"
          }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    formatInvoiceType(row) {
      if (row.invoiceType === 1 || row.invoiceType === "1") {
        return "已完成"
      } else {
        return "未完成"
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
        collectionType: undefined,
        payable: undefined,
        receipts: undefined,
        invoiceNum: undefined,
        invoiceName: undefined,
        taxId: undefined,
        invoiceType: undefined,
        invoiceAmount: undefined,
        voucherUrl: undefined,
        returnPrice: undefined,
        balancePrice: undefined,
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
        this.title = "回款登记";
        console.log(response.data)
      });

      getReturnByInvoice(id).then(response => {
        this.returnManageList = response.rows;
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          addToReturn(this.form).then(response => {
            this.msgSuccess("回款成功");
            this.open = false;
            this.getList();
          })

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
