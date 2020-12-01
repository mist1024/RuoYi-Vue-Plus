<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="住院号" prop="hospitalId">
        <el-input
          v-model="queryParams.hospitalId"
          placeholder="请输入住院号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="结算日期" prop="settleAt">
        <el-date-picker clearable size="small" style="width: 200px"
                        v-model="queryParams.settleAt"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择结算日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结算总价" prop="price">
        <el-input
          v-model="queryParams.price"
          placeholder="请输入结算总价"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="结算标志" prop="settlementFlag">
        <el-select v-model="queryParams.settlementFlag" @change="selectSettlementFlag" placeholder="请选择">
          <el-option
            v-for="item in settlementFlagOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
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
      <!--          v-hasPermi="['fantang:settle:add']"-->
      <!--        >伙食费收款-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['fantang:settle:edit']"
        >出院结算
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['fantang:settle:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['fantang:settle:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="settleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="结算 id" align="center" prop="settleId" v-if="false"/>
      <el-table-column label="住院号" align="center" prop="hospitalId"/>
      <el-table-column label="姓名" align="center" prop="name"/>
      <el-table-column label="科室" align="center" prop="departName"/>
      <el-table-column label="床号" align="center" prop="bedId"/>
      <el-table-column label="结算日期" align="center" prop="settleAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.settleAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结算总价" align="center" prop="price"/>
      <el-table-column label="结算类型" align="center" prop="type"/>
      <el-table-column label="退款总额" align="center" prop="refund"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="clickAddNewSettlement(scope.row)"
            v-hasPermi="['fantang:settle:AddNewSettlement']"
          >收费
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="clickAddLeaveSettlement(scope.row)"
            v-hasPermi="['fantang:settle:AddLeaveSettlement']"
          >出院结算
          </el-button>
          <!--          <el-button-->
          <!--            size="mini"-->
          <!--            type="text"-->
          <!--            icon="el-icon-edit"-->
          <!--            @click="handleUpdate(scope.row)"-->
          <!--            v-hasPermi="['fantang:settle:edit']"-->
          <!--          >修改-->
          <!--          </el-button>-->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['fantang:settle:remove']"
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

    <!--    日常收费弹出层对话框-->
    <el-dialog title="伙食费结算窗口" :visible.sync="flagAddNewSettlementOpen" width="600px" append-to-body>
      <el-form ref="form" :model="formAddNewSettlement" :rules="rules" label-width="160px">
        <el-form-item label="住院号" prop="hospitalId">
          <el-input v-model="formAddNewSettlement.hospitalId" readonly/>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="formAddNewSettlement.name" readonly/>
        </el-form-item>
        <el-form-item label="科室" prop="name">
          <el-input v-model="formAddNewSettlement.departName" readonly/>
        </el-form-item>
        <el-form-item label="床号" prop="name">
          <el-input v-model="formAddNewSettlement.bedId" readonly/>
        </el-form-item>
        <el-form-item label="上次结算日期" prop="name">
          <el-input v-model="formAddNewSettlement.name" readonly/>
        </el-form-item>
        <el-form-item label="应收" prop="name">
          <el-input v-model="formAddNewSettlement.price" readonly/>
        </el-form-item>
        <el-form-item label="用餐天数" prop="name">
          <el-input v-model="formAddNewSettlement.name" readonly/>
        </el-form-item>
        <el-form-item label="已收预付伙食费" prop="name" v-if="flagAddPrepaymentShow">
          <el-input v-model="formAddNewSettlement.prepayment" readonly/>
        </el-form-item>
        <el-form-item label="实收" prop="receipts">
          <el-input v-model="form.receipts" placeholder="请输入实收"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--    出院结算弹出层对话框-->

    <!-- 添加或修改结算报对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="记录清单" prop="list">
          <el-input v-model="form.list" placeholder="请输入记录清单"/>
        </el-form-item>
        <el-form-item label="结算总价" prop="price">
          <el-input v-model="form.price" placeholder="请输入结算总价"/>
        </el-form-item>
        <el-form-item label="应收" prop="payable">
          <el-input v-model="form.payable" placeholder="请输入应收"/>
        </el-form-item>
        <el-form-item label="实收" prop="receipts">
          <el-input v-model="form.receipts" placeholder="请输入实收"/>
        </el-form-item>
        <el-form-item label="结算类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择结算类型">
            <el-option label="请选择字典生成" value=""/>
          </el-select>
        </el-form-item>
        <el-form-item label="退款总额" prop="refund">
          <el-input v-model="form.refund" placeholder="请输入退款总额"/>
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
import {addSettle, delSettle, exportSettle, getSettle, listSettle, updateSettle} from "@/api/fantang/settle";
import {listAll, listNoPay, listPayoff} from "@/api/fantang/meals";

export default {
  name: "Settle",
  components: {},
  data() {
    return {
      // 权限相关的参数
      userName: null,
      roleGroup: null,
      postGroup: null,
      // 出院结算状态显示标志位
      flagAddPrepaymentShow: false,
      // 费用结算弹出层控制标志位
      flagAddNewSettlementOpen: false,

      // 费用结算弹出层数据
      formAddNewSettlement: {
        hospitalId: null,
        patientId: null,
        name: null,
        departName: null,
        bedId: null,
        price: null,
        receivable: null,
        netPeceipt: null,
        prepayment: null,
        settlementDate: null,
        userName: null,
      },

      // 结算类型字典
      settlementFlagOptions: [{
        value: null,
        label: '全部数据'
      }, {
        value: 0,
        label: '未结算'
      }, {
        value: 1,
        label: '已结算'
      },],

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
      // 结算报表格数据
      settleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        settleAt: null,
        price: null,
        payable: null,
        receipts: null,
        type: null,
        refund: null,
        hospitalId: null,
        name: null,
        settlementFlag: 0,
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
        ]
      }
    };
  },
  created() {
    this.loading = true;
    this.myGetUser();
    listNoPay(this.queryParams).then(response => {
      this.settleList = response.rows;
      this.total = response.total;
      this.loading = false;
    });
  },
  methods: {
    // 获取用户相关信息
    myGetUser() {
      getUserProfile().then(response => {
        this.userName = response.data;
        this.roleGroup = response.roleGroup;
        this.postGroup = response.postGroup;
      });
    },

    // 日常伙食费结算操作按钮
    clickAddNewSettlement(row) {
      this.flagAddNewSettlementOpen = true;
      this.flagAddPrepaymentShow = false;
      this.formAddNewSettlement.hospitalId = row.hospitalId;
      this.formAddNewSettlement.name = row.name;
      this.formAddNewSettlement.departName = row.departName;
      this.formAddNewSettlement.bedId = row.bedId;
      this.formAddNewSettlement.patientId = row.patientId;
      this.formAddNewSettlement.price = row.price;
      this.formAddNewSettlement.prepayment = row.prepayment;
      this.formAddNewSettlement.netPeceipt = null;
      this.formAddNewSettlement.userName = this.userName;
    },

    // 出院伙食费结算按钮
    clickAddLeaveSettlement() {
      this.flagAddNewSettlementOpen = true;
      this.flagAddPrepaymentShow = true;

    },

    // 处理筛选结算标志
    selectSettlementFlag(value) {
      console.log("value", value)
      if (value === null) {
        this.loading = true;
        listAll(this.queryParams).then(response => {
          this.settleList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      } else if (value === 0) {
        listNoPay(this.queryParams).then(response => {
          this.settleList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      } else {
        listPayoff(this.queryParams).then(response => {
          this.settleList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      }
    },
    /** 查询结算报列表 */
    getList() {
      this.loading = true;
      listSettle(this.queryParams).then(response => {
        this.settleList = response.rows;
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
        settleId: null,
        patientId: null,
        settleAt: null,
        opera: null,
        list: null,
        price: null,
        payable: null,
        receipts: null,
        type: null,
        flag: null,
        refund: null
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
      this.title = "添加结算报";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const settleId = row.settleId || this.ids
      getSettle(settleId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改结算报";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.settleId != null) {
            updateSettle(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSettle(this.form).then(response => {
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
      this.$confirm('是否确认删除结算报编号为"' + settleIds + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delSettle(settleIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有结算报数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportSettle(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
