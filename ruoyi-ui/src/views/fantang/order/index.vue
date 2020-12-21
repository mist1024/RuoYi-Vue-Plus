<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单类型" prop="orderType">
        <el-select v-model="queryParams.orderType" placeholder="请选择订单类型" clearable
                   size="small">
          <el-option
            v-for="item in orderTypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="总价" prop="totalPrice">
        <el-input
          v-model="queryParams.totalPrice"
          placeholder="请输入总价"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="折扣" prop="discount">
        <el-input
          v-model="queryParams.discount"
          placeholder="请输入折扣"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实收" prop="receipts">
        <el-input
          v-model="queryParams.receipts"
          placeholder="请输入实收"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createAt">
        <el-date-picker clearable size="small" style="width: 200px"
                        v-model="queryParams.createAt"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="订单来源" prop="orderSrc">
        <el-input
          v-model="queryParams.orderSrc"
          placeholder="请输入订单来源"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单现售" prop="currentPrice">
        <el-input
          v-model="queryParams.currentPrice"
          placeholder="请输入订单现售"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支付方式" prop="payType">
        <el-select v-model="queryParams.payType" placeholder="请选择支付方式" clearable size="small">
          <el-option label="请选择字典生成" value=""/>
        </el-select>
      </el-form-item>
      <el-form-item label="核销时间" prop="writeOffAt">
        <el-date-picker clearable size="small" style="width: 200px"
                        v-model="queryParams.writeOffAt"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择核销时间">
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
          v-hasPermi="['fantang:order:add']"
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
          v-hasPermi="['fantang:order:edit']"
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
          v-hasPermi="['fantang:order:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['fantang:order:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="订单 id" align="center" prop="orderId" v-if="false"/>
      <el-table-column label="订单类型" align="center" prop="orderType" :formatter="orderTypeFormat"/>
      <el-table-column label="清单" align="center" prop="orderList"/>
      <el-table-column label="总价" align="center" prop="totalPrice"/>
      <el-table-column label="折扣" align="center" prop="discount"/>
      <el-table-column label="实收" align="center" prop="receipts"/>
      <el-table-column label="创建时间" align="center" prop="createAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="订单来源" align="center" prop="orderSrc"/>
      <el-table-column label="订单现售" align="center" prop="currentPrice"/>
      <el-table-column label="支付方式" align="center" prop="payType"/>
      <el-table-column label="核销时间" align="center" prop="writeOffAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.writeOffAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否过期" align="center" prop="isExpired"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['fantang:order:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['fantang:order:remove']"
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

    <!-- 添加或修改订单管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单类型" prop="orderType">
          <el-select v-model="form.orderType" placeholder="请选择订单类型">
            <el-option label="请选择字典生成" value=""/>
          </el-select>
        </el-form-item>
        <el-form-item label="清单" prop="orderList">
          <el-input v-model="form.orderList" placeholder="请输入清单"/>
        </el-form-item>
        <el-form-item label="总价" prop="totalPrice">
          <el-input v-model="form.totalPrice" placeholder="请输入总价"/>
        </el-form-item>
        <el-form-item label="折扣" prop="discount">
          <el-input v-model="form.discount" placeholder="请输入折扣"/>
        </el-form-item>
        <el-form-item label="实收" prop="receipts">
          <el-input v-model="form.receipts" placeholder="请输入实收"/>
        </el-form-item>
        <el-form-item label="订单来源" prop="orderSrc">
          <el-input v-model="form.orderSrc" placeholder="请输入订单来源"/>
        </el-form-item>
        <el-form-item label="订单现售" prop="currentPrice">
          <el-input v-model="form.currentPrice" placeholder="请输入订单现售"/>
        </el-form-item>
        <el-form-item label="支付方式" prop="payType">
          <el-select v-model="form.payType" placeholder="请选择支付方式">
            <el-option label="请选择字典生成" value=""/>
          </el-select>
        </el-form-item>
        <el-form-item label="是否过期" prop="isExpired">
          <el-select v-model="form.isExpired" placeholder="请选择是否过期">
            <el-option label="请选择字典生成" value=""/>
          </el-select>
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
import {addOrder, delOrder, exportOrder, getOrder, listOrder, updateOrder} from "@/api/fantang/order";

export default {
  name: "Order",
  components: {},
  data() {
    return {
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
      rules: {
        orderType: [
          {required: true, message: "订单类型不能为空", trigger: "change"}
        ],
        orderList: [
          {required: true, message: "清单不能为空", trigger: "blur"}
        ],
        totalPrice: [
          {required: true, message: "总价不能为空", trigger: "blur"}
        ],
        discount: [
          {required: true, message: "折扣不能为空", trigger: "blur"}
        ],
        receipts: [
          {required: true, message: "实收不能为空", trigger: "blur"}
        ],
        orderSrc: [
          {required: true, message: "订单来源不能为空", trigger: "blur"}
        ],
        currentPrice: [
          {required: true, message: "订单现售不能为空", trigger: "blur"}
        ],
        isExpired: [
          {required: true, message: "是否过期不能为空", trigger: "change"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("ft_book_type").then(response => {
      this.orderTypeOptions = response.data;
    });
  },
  methods: {
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
