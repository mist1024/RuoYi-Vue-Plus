<template>
  <div class="app-container">

    <el-form :inline="true" :model="formStatisticsType" class="demo-form-inline">
      <el-form-item label="统计方式">
        <el-select v-model="formStatisticsType.statisticsType" placeholder="请选择统计方式" @change="changeStatisticsType">
          <el-option
            v-for="item in statisticsTypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>

    <el-form :inline="true" :model="formDay" class="demo-form-inline">
      <el-form-item label="日统计" label-width="68px">
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
      <el-form-item label="周统计" label-width="68px">
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
      <el-form-item label="月统计" label-width="68px">
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

    <el-table v-loading="loading" :data="orderCountList" border>
      <el-table-column label="报餐部门" align="center" prop="departName"/>
      <el-table-column label="报餐类型" align="center" prop="orderType" :formatter="formatOrderType"/>
      <el-table-column label="报餐数量" align="center" prop="countOrder"/>
      <el-table-column label="报餐员工" align="center" prop="staffName"/>
      <el-table-column label="电话" align="center" prop="tel"/>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getStatisticsList"
    />

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
      statisticsTypeOptions: [{
        value: 1,
        label: '按部门'
      }, {
        value: 2,
        label: '按人'
      }],
      typeOptions: [],
      formStatisticsType: {
        statisticsType: null,
      },
      formMonth: {
        selectMonth: null,
        statisticsType: null,
      },
      formWeek: {
        selectWeek: null,
        statisticsType: null,
      },
      formDay: {
        selectDay: null,
        statisticsType: null,
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
      // 查询类型
      selectType: null,
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
        selectDay: null,
        selectWeek: null,
        selectMonth: null,
        statisticsType: null,
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
    getStatisticsList() {
      if (this.selectType === 1 || this.selectType === '1') {
        getStatisGetOrderOfDay(this.queryParams).then(response => {
          console.log(response)
          this.orderCountList = response.data;
          this.selectType = 1;
          this.total = response.total;
        })
      } else if (this.selectType === 2 || this.selectType === '2') {
        getStatisGetOrderOfWeek(this.queryParams).then(response => {
          console.log(response)
          this.orderCountList = response.data;
          this.selectType = 2;
          this.total = response.total;
        })
      } else if (this.selectType === 3 || this.selectType === '3') {
        getStatisGetOrderOfMonth(this.queryParams).then(response => {
          this.orderCountList = response.data;
          this.selectType = 3;
          this.total = response.total;
        })
      }
    },
    changeStatisticsType(item) {
      console.log(item)
      this.formDay.statisticsType = item;
      this.formWeek.statisticsType = item;
      this.formMonth.statisticsType = item;
    },
    resetCount() {
      this.formStatisticsType = {
        statisticsType: null
      }
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
      if (this.formMonth.statisticsType == null) {
        this.msgError("请选择统计方式")
      }
      if (this.formMonth.selectMonth != null && this.formMonth.statisticsType != null) {
        console.log(this.formMonth)
        this.queryParams.selectMonth = this.formMonth.selectMonth;
        this.queryParams.statisticsType = this.formMonth.statisticsType;
        getStatisGetOrderOfMonth(this.queryParams).then(response => {
          this.orderCountList = response.data;
          this.selectType = 3;
          this.total = response.total;
        })
      }
    },
    onWeekSubmit() {
      if (this.formWeek.statisticsType == null) {
        this.msgError("请选择统计方式")
      }
      if (this.formWeek.selectWeek != null && this.formWeek.statisticsType != null) {
        console.log(this.formWeek)
        this.queryParams.selectWeek = this.formWeek.selectWeek;
        this.queryParams.statisticsType = this.formWeek.statisticsType;
        getStatisGetOrderOfWeek(this.queryParams).then(response => {
          console.log(response)
          this.orderCountList = response.data;
          this.selectType = 2;
          this.total = response.total;
        })
      }
    },
    onDaySubmit() {
      if (this.formDay.statisticsType == null) {
        this.msgError("请选择统计方式")
      }
      if (this.formDay.selectDay != null && this.formDay.statisticsType != null) {
        console.log(this.formDay)
        this.queryParams.selectDay = this.formDay.selectDay;
        this.queryParams.statisticsType = this.formDay.statisticsType;
        getStatisGetOrderOfDay(this.queryParams).then(response => {
          console.log(response)
          this.orderCountList = response.data;
          this.selectType = 1;
          // this.total = response.total;
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
