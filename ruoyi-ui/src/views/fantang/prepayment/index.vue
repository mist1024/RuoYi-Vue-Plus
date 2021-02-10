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
      <el-form-item label="是否已缴费" prop="settlementFlag" label-width="90px">
        <el-select v-model="queryParams.settlementFlag" @change="selectSettlementFlag" placeholder="请选择">
          <el-option
            v-for="item in settlementFlagOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="预付费时间" prop="prepaidAt" label-width="50" v-if="payFlag">
        <el-date-picker clearable size="small" style="width: 200px"
                        v-model="queryParams.prepaidAt"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择预付费时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="prepaymentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="预付费id" align="center" prop="prepaymentId" v-if="false"/>
      <el-table-column label="住院号" align="center" prop="hospitalId" width="180">
      </el-table-column>
      <el-table-column label="姓名" align="center" prop="name"/>
      <el-table-column label="科室" align="center" prop="departName" width="180">
      </el-table-column>
      <el-table-column label="床号" align="center" prop="bedId"/>
      <el-table-column label="预付款余额" align="center" prop="prepaid"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-s-claim"
            @click="clickSettle(scope.row)"
            v-hasPermi="['fantang:prepayment:remove']"
            v-if="scope.row.noPrepayment"
          >出院结清
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-printer"
            @click="handleGenerateReceiptPdf(scope.row)"
            v-hasPermi="['fantang:prepayment:remove']"
            v-if="scope.row.noPrepayment"
          >打印
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-money"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['fantang:prepayment:remove']"
            v-if="!scope.row.noPrepayment"
          >收款
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

    <!-- 出院结清收费管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px"
               append-to-body
               :close-on-click-modal="false"
               :modal="true">
      <el-form ref="formAddPrepayment" :model="formAddPrepayment" :rules="rules" label-width="120px">
        <el-form-item label="住院号" prop="hospitalId">
          <el-autocomplete :disabled="payFlag"
                           popper-class="my-autocomplete"
                           v-model="formAddPrepayment.hospitalId"
                           :fetch-suggestions="querySearch"
                           placeholder="请输入住院号"
                           @select="handleSelect" style="width: 250px">
            <i
              class="el-icon-edit el-input__icon"
              slot="suffix"
              @click="handleIconClick">
            </i>
            <template slot-scope="{ item }" style="width: 300px">
              <div class="name">{{ item.name }}</div>
              <span class="addr">
                {{ item.departName }}
                <el-divider direction="vertical"></el-divider>
                {{ item.bedId }}
                <el-divider direction="vertical"></el-divider>
                {{ item.value }}
              </span>
            </template>
          </el-autocomplete>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="formAddPrepayment.name" style="width: 250px" readonly/>
        </el-form-item>
        <el-form-item label="预付费金额" prop="prepaid">
          <el-input v-model="formAddPrepayment.prepaid"
                    onKeypress="return(/[\d]/.test(String.fromCharCode(event.keyCode)))" type="number"
                    placeholder="请输入预付费金额"
                    style="width: 250px"
                    :disabled="payFlag"/>
        </el-form-item>
        <el-form-item label="预付费时间" prop="collectAt">
          <el-date-picker clearable size="small" style="width: 250px"
                          v-model="formAddPrepayment.collectAt"
                          type="date"
                          value-format="yyyy-MM-dd"
                          readonly
                          placeholder="选择预付费时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitLeaveSettle">结 清</el-button>
        <el-button @click="cancel">关 闭</el-button>
        <el-button @click="clickPrintSettle">打印收据</el-button>
      </div>
    </el-dialog>

    <!-- 收费管理对话框 -->
    <el-dialog :title="openChargeDialogTitle" :visible.sync="openChargeDialogFlag" width="500px"
               append-to-body
               :close-on-click-modal="false"
               :modal="true">
      <el-form ref="formAddPrepayment" :model="formAddPrepayment" :rules="rules" label-width="120px">
        <el-form-item label="住院号" prop="hospitalId">
          <el-autocomplete
            :disabled="!this.payFlag"
            popper-class="my-autocomplete"
            v-model="formAddPrepayment.hospitalId"
            :fetch-suggestions="querySearch"
            placeholder="请输入住院号"
            @select="handleSelect" style="width: 250px">
            <i
              class="el-icon-edit el-input__icon"
              slot="suffix"
              @click="handleIconClick">
            </i>
            <template slot-scope="{ item }" style="width: 300px">
              <div class="name">{{ item.name }}</div>
              <span class="addr">
                {{ item.departName }}
                <el-divider direction="vertical"></el-divider>
                {{ item.bedId }}
                <el-divider direction="vertical"></el-divider>
                {{ item.value }}
              </span>
            </template>
          </el-autocomplete>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="formAddPrepayment.name" style="width: 250px" readonly/>
        </el-form-item>
        <el-form-item label="预付费金额" prop="prepaid">
          <el-input v-model="formAddPrepayment.prepaid"
                    onKeypress="return(/[\d]/.test(String.fromCharCode(event.keyCode)))" type="number"
                    placeholder="请输入预付费金额"
                    style="width: 250px"
                    :disabled="!this.payFlag"/>
        </el-form-item>
        <el-form-item label="预付费时间" prop="collectAt">
          <el-date-picker clearable size="small" style="width: 250px"
                          v-model="formAddPrepayment.collectAt"
                          type="date"
                          value-format="yyyy-MM-dd"
                          readonly
                          placeholder="选择预付费时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormChangePrepayment">收 费</el-button>
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {
  addPrepayment,
  delPrepayment,
  exportPrepayment,
  generateReceiptPdf,
  getPrepayment,
  leaveSettlePrepayment,
  listNoPrepayment,
  listPrepay
} from "../../../api/fantang/prepayment";

export default {
  name: "Prepayment",
  components: {},
  data() {
    return {
      // 缴费标志
      payFlag: true,
      openChargeDialogTitle: null,
      openChargeDialogFlag: false,
      // 权限相关的参数
      user: null,
      roleGroup: null,
      postGroup: null,

      settlementFlagOptions: [{
        value: 0,
        label: '已缴费'
      }, {
        value: 1,
        label: '未缴费'
      }],
      suggestionList: [],
      NoPrepayments: [],
      state: '',
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
      // 收费管理表格数据
      prepaymentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        collectAt: null,
        settlementAt: null,
        settlementFlag: 0,
        prepaid: null,
        prepaidAt: null,
        hospitalId: null,
        name: null,
      },
      // 表单参数
      formAddPrepayment: {
        hospitalId: null,
        name: null,
        prepaid: null,
        collectAt: null,
        row: null,
        userName: null,
        patientId: null,
        settlementFlag: null,
      },
      // 表单校验
      rules: {
        prepaid: [
          {required: true, message: "预付费金额不能为空", trigger: "blur"}
        ],
        prepaidAt: [
          {required: true, message: "预付费时间不能为空", trigger: "blur"}
        ]
      }
    };
  },

  created() {
    this.getList();
    this.buildSuggestionList();
  },
  mounted() {
  },

  methods: {

    clickPrintSettle() {
      this.handleGenerateReceiptPdf(this.formAddPrepayment.row);
    },

    // 相应处理出院结清功能
    clickSettle(row) {
      this.open = true;
      getPrepayment(row.prepaymentId).then(response => {
        this.formAddPrepayment.hospitalId = row.hospitalId;
        this.formAddPrepayment.name = row.name;
        this.formAddPrepayment.collectAt = response.data.prepayment = response.data.collectAt;
        this.formAddPrepayment.prepaid = response.data.prepaid;
        this.formAddPrepayment.row = row;
      });
    },
    // 获取数据列表，跟进筛选条件不同获取不同类型的数据
    getList() {
      if (this.queryParams.settlementFlag === 0) {
        // 查询已交预付费信息
        listPrepay(this.queryParams).then(response => {
          this.prepaymentList = response.rows.map((item) => {
            item.noPrepayment = true;
            return item;
          });
          this.total = response.total;
          this.loading = false;
        })
      } else {
        this.getDefaultNoPrepayment(this.queryParams);
      }

    },

    // 生成收据 pdf
    handleGenerateReceiptPdf(row) {
      generateReceiptPdf(row).then(response => {
        window.open(response.msg)
      })
    },
    // 处理筛选结算标志
    selectSettlementFlag(value) {
      this.payFlag = value !== 1;
      this.getList();
    },

    // 响应自动查询回显
    querySearch(queryString, cb) {
      var restaurants = this.suggestionList;
      var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
      // 调用 callback 返回建议列表的数据
      cb(results);
    },

    // 处理过滤器
    createFilter(queryString) {
      return (restaurant) => {
        return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
      };
    },

    buildSuggestionList() {
      listNoPrepayment(this.queryParams).then(response => {
        let prepaymentList = response.rows;
        this.suggestionList = prepaymentList.map(item => {
          return {
            "value": item.hospitalId,
            "departName": item.departName,
            "name": item.name,
            "bedId": item.bedId,
            "patientId": item.patientId,
          }
        });
      });
    },

    // 填充所有待缴预付伙食费的病人清单
    getDefaultNoPrepayment() {
      this.loading = true;
      listNoPrepayment(this.queryParams).then(response => {
        this.prepaymentList = response.rows.map(item => {
          item.noPrepayment = false;
          return item;
        });
        this.total = response.total;
        this.suggestionList = this.prepaymentList.map(item => {
          return {
            "value": item.hospitalId,
            "departName": item.departName,
            "name": item.name,
            "bedId": item.bedId,
            "patientId": item.patientId,
          }
        });
        this.loading = false;
        return response.rows;
      });
    },

    // 处理自动查询列表选择的事件
    handleSelect(item) {
      this.formAddPrepayment.name = item.name;
      this.formAddPrepayment.patientId = item.patientId;
      this.formAddPrepayment.bedId = item.bedId;
      this.formAddPrepayment.departName = item.departName;
      this.formAddPrepayment["hospitalId"] = item.value;
    },

    // 处理点击查询图标的事件
    handleIconClick(ev) {
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.openChargeDialogFlag = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.formAddPrepayment = {
        prepaymentId: null,
        patientId: null,
        collectAt: null,
        collectBy: null,
        settlementAt: null,
        settlementBy: null,
        settlementId: null,
        settlementFlag: null,
        prepaid: 700,
        prepaidAt: new Date(),
        hospitalId: null,
        name: null,
        userName: null,
      };
      this.resetForm("formAddPrepayment");
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
      this.ids = selection.map(item => item.prepaymentId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加收费管理";
    },
    /** 收费按钮操作 */
    handleUpdate(row) {
      this.reset();
      const prepaymentId = row.prepaymentId || this.ids

      this.formAddPrepayment.hospitalId = row.hospitalId;
      this.formAddPrepayment.name = row.name;
      this.formAddPrepayment.collectAt = new Date();
      this.formAddPrepayment.prepaid = 700;
      this.formAddPrepayment.row = row;
      this.openChargeDialogFlag = true;
    },
    submitFormChangePrepayment() {
      this.formAddPrepayment.collectBy = this.$store.state.user.name;
      this.formAddPrepayment.patientId = this.formAddPrepayment.row.patientId;
      this.formAddPrepayment.settlementFlag = 0;
      this.formAddPrepayment.collectAt = null;
      this.formAddPrepayment.prepaidAt = null;

      console.log("bbbbbbbbbb", this.formAddPrepayment);
      addPrepayment(this.formAddPrepayment).then(response => {
        this.msgSuccess("已收费")
        this.openChargeDialogFlag = false;
        this.reset();
        this.getList();
      })
    },

    /** 提交按钮 */
    submitFormAddPrepayment() {
      this.formAddPrepayment.userName = this.$store.state.user.name
      console.log(this.formAddPrepayment);
    },

    submitLeaveSettle() {
      console.log(this.formAddPrepayment.row);
      leaveSettlePrepayment(this.formAddPrepayment.row.prepaymentId).then(response => {
        this.msgSuccess("已结清")
        this.open = false;
        this.reset();
        this.getList();
      })
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const prepaymentIds = row.prepaymentId || this.ids;
      this.$confirm('是否确认删除收费管理编号为"' + prepaymentIds + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delPrepayment(prepaymentIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有收费管理数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportPrepayment(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>

<style lang="scss">
.my-autocomplete {
  li {
    line-height: normal;
    padding: 7px;

    .name {
      text-overflow: ellipsis;
      overflow: hidden;
    }

    .addr {
      font-size: 12px;
      color: #b4b4b4;
    }

    .highlighted .addr {
      color: #ddd;
    }
  }
}
</style>
