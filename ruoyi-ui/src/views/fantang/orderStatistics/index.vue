<template>
  <div class="app-container">

    <el-form :inline="true" :model="formDay" class="demo-form-inline">
      <el-form-item label="日统计">
        <el-date-picker
          v-model="formDay.selectDay"
          type="date"
          placeholder="请选择统计日">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onDaySubmit">统计</el-button>
      </el-form-item>
    </el-form>


    <el-form :inline="true" :model="formWeek" class="demo-form-inline">
      <el-form-item label="周统计">
        <el-date-picker
          v-model="formWeek.selectWeek"
          :picker-options="{
            firstDayOfWeek:1
          }"
          type="week"
          format="yyyy 第 WW 周"
          placeholder="请选择统计周">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onWeekSubmit">统计</el-button>
      </el-form-item>
    </el-form>

    <el-form :inline="true" :model="formMonth" class="demo-form-inline">
      <el-form-item label="月统计">
        <el-date-picker
          v-model="formMonth.selectMonth"
          type="month"
          placeholder="请选择统计月">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onMonthSubmit">统计</el-button>
      </el-form-item>
      <el-form-item>
        <el-button @click="resetCount">重置</el-button>
      </el-form-item>
    </el-form>


    <!--    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">-->
    <!--      <el-form-item label="订单类型" prop="orderType">-->
    <!--        <el-select v-model="queryParams.orderType" placeholder="请选择订单类型" clearable-->
    <!--                   size="small">-->
    <!--          <el-option-->
    <!--            v-for="item in orderTypeOptions"-->
    <!--            :key="item.value"-->
    <!--            :label="item.label"-->
    <!--            :value="item.value">-->
    <!--          </el-option>-->
    <!--        </el-select>-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="总价" prop="totalPrice">-->
    <!--        <el-input-->
    <!--          v-model="queryParams.totalPrice"-->
    <!--          placeholder="请输入总价"-->
    <!--          clearable-->
    <!--          size="small"-->
    <!--          @keyup.enter.native="handleQuery"-->
    <!--        />-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="折扣" prop="discount">-->
    <!--        <el-input-->
    <!--          v-model="queryParams.discount"-->
    <!--          placeholder="请输入折扣"-->
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
    <!--      <el-form-item label="创建时间" prop="createAt">-->
    <!--        <el-date-picker clearable size="small" style="width: 200px"-->
    <!--                        v-model="queryParams.createAt"-->
    <!--                        type="date"-->
    <!--                        value-format="yyyy-MM-dd"-->
    <!--                        placeholder="选择创建时间">-->
    <!--        </el-date-picker>-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="订单来源" prop="orderSrc">-->
    <!--        <el-input-->
    <!--          v-model="queryParams.orderSrc"-->
    <!--          placeholder="请输入订单来源"-->
    <!--          clearable-->
    <!--          size="small"-->
    <!--          @keyup.enter.native="handleQuery"-->
    <!--        />-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="订单现售" prop="currentPrice">-->
    <!--        <el-input-->
    <!--          v-model="queryParams.currentPrice"-->
    <!--          placeholder="请输入订单现售"-->
    <!--          clearable-->
    <!--          size="small"-->
    <!--          @keyup.enter.native="handleQuery"-->
    <!--        />-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="支付方式" prop="payType">-->
    <!--        <el-select v-model="queryParams.payType" placeholder="请选择支付方式" clearable size="small">-->
    <!--          <el-option label="请选择字典生成" value=""/>-->
    <!--        </el-select>-->
    <!--      </el-form-item>-->
    <!--      <el-form-item label="核销时间" prop="writeOffAt">-->
    <!--        <el-date-picker clearable size="small" style="width: 200px"-->
    <!--                        v-model="queryParams.writeOffAt"-->
    <!--                        type="date"-->
    <!--                        value-format="yyyy-MM-dd"-->
    <!--                        placeholder="选择核销时间">-->
    <!--        </el-date-picker>-->
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
    <!--          v-hasPermi="['fantang:order:add']"-->
    <!--        >新增-->
    <!--        </el-button>-->
    <!--      </el-col>-->
    <!--      <el-col :span="1.5">-->
    <!--        <el-button-->
    <!--          type="success"-->
    <!--          icon="el-icon-edit"-->
    <!--          size="mini"-->
    <!--          :disabled="single"-->
    <!--          @click="handleUpdate"-->
    <!--          v-hasPermi="['fantang:order:edit']"-->
    <!--        >修改-->
    <!--        </el-button>-->
    <!--      </el-col>-->
    <!--      <el-col :span="1.5">-->
    <!--        <el-button-->
    <!--          type="danger"-->
    <!--          icon="el-icon-delete"-->
    <!--          size="mini"-->
    <!--          :disabled="multiple"-->
    <!--          @click="handleDelete"-->
    <!--          v-hasPermi="['fantang:order:remove']"-->
    <!--        >删除-->
    <!--        </el-button>-->
    <!--      </el-col>-->
    <!--      <el-col :span="1.5">-->
    <!--        <el-button-->
    <!--          type="warning"-->
    <!--          icon="el-icon-download"-->
    <!--          size="mini"-->
    <!--          @click="handleExport"-->
    <!--          v-hasPermi="['fantang:order:export']"-->
    <!--        >导出-->
    <!--        </el-button>-->
    <!--      </el-col>-->
    <!--      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
    <!--    </el-row>-->

    <el-table v-loading="loading" :data="orderCountList" border>
      <el-table-column label="报餐部门" align="center" prop="departName"/>
      <el-table-column label="报餐类型" align="center" prop="orderType" :formatter="formatOrderType"/>
      <el-table-column label="报餐数量" align="center" prop="countOrder"/>
    </el-table>

    <!--    <pagination-->
    <!--      v-show="total>0"-->
    <!--      :total="total"-->
    <!--      :page.sync="queryParams.pageNum"-->
    <!--      :limit.sync="queryParams.pageSize"-->
    <!--      @pagination="getList"-->
    <!--    />-->

  </div>
</template>

<script>
import {
  addOrder,
  delOrder,
  exportOrder,
  getOrder,
  getStatisGetOrderOfDay,
  getStatisGetOrderOfMonth,
  getStatisGetOrderOfWeek,
  listOrder,
  updateOrder
} from "@/api/fantang/order";

export default {
  name: "Order",
  components: {},
  data() {
    return {
      typeOptions: [],
      formMonth: {
        selectMonth: null,
      },
      formWeek: {
        selectWeek: null,
      },
      formDay: {
        selectDay: null,
      },
      orderTypeOptions: [],
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
      // 订单管理表格数据
      orderList: [],
      orderCountList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderType: null,
        totalPrice: null,
        discount: null,
        receipts: null,
        createAt: null,
        orderSrc: null,
        currentPrice: null,
        payType: null,
        writeOffAt: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
    this.getDicts("ft_book_type").then(response => {
      this.typeOptions = response.data;
    });
  },
  methods: {
    resetCount() {
      this.formDay = {
        selectDay: null
      }
      this.formWeek = {
        selectWeek: null
      }
      this.formMonth = {
        selectMonth: null
      }
      this.orderCountList = null
    },
    formatOrderType(row) {
      return this.selectDictLabel(this.typeOptions, row.orderType);
    },
    onMonthSubmit() {
      if (this.formMonth.selectMonth != null) {
        console.log(this.formMonth)
        getStatisGetOrderOfMonth(this.formMonth).then(response => {
          this.orderCountList = response.data;
        })
      }
    },
    onWeekSubmit() {
      if (this.formWeek.selectWeek != null) {
        console.log(this.formWeek)
        getStatisGetOrderOfWeek(this.formWeek).then(response => {
          console.log(response)
          this.orderCountList = response.data;
        })
      }
    },
    onDaySubmit() {
      if (this.formDay.selectDay != null) {
        console.log(this.formDay)
        getStatisGetOrderOfDay(this.formDay).then(response => {
          console.log(response)
          this.orderCountList = response.data;
        })
      }
    },
    /** 查询订单管理列表 */
    getList() {
      this.loading = true;
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows;
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
        orderId: null,
        orderType: null,
        workerId: null,
        orderList: null,
        totalPrice: null,
        discount: null,
        receipts: null,
        createAt: null,
        createBy: null,
        orderSrc: null,
        currentPrice: null,
        payType: null,
        payFlag: null,
        expiredFlag: null,
        writeOffFlag: null,
        writeOffAt: null,
        isExpired: null,
        deviceId: null
      };
      this.resetForm("form");
    },
    /** 订单类型回显**/
    orderTypeFormat(row, column) {
      return this.selectDictLabel(this.orderTypeOptions, row.orderType);
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
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加订单管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const orderId = row.orderId || this.ids
      getOrder(orderId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改订单管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.orderId != null) {
            updateOrder(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrder(this.form).then(response => {
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
      const orderIds = row.orderId || this.ids;
      this.$confirm('是否确认删除订单管理编号为"' + orderIds + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delOrder(orderIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有订单管理数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportOrder(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
