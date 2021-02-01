<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="报餐日期" prop="createAt">
        <el-date-picker clearable size="small" style="width: 200px"
                        v-model="queryParams.createAt"
                        align="right"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择报餐日期"
                        @change="handleQuery"
                        :picker-options="pickerOptions">
        </el-date-picker>
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
          v-hasPermi="['fantang:meals:add']"
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
          v-hasPermi="['fantang:meals:edit']"
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
          v-hasPermi="['fantang:meals:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['fantang:meals:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mealsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="科室" align="center" prop="departName"/>
      <el-table-column label="住院号" align="center" prop="HospitalId"/>
      <el-table-column label="床号" align="center" prop="bedId"/>
      <el-table-column label="姓名" align="center" prop="name"/>
      <el-table-column label="类型" align="center" prop="type" :formatter="formatToDinner"/>
      <el-table-column label="菜品" align="center" prop="foods"/>
      <el-table-column label="加饭" align="center" prop="rice" :formatter="formatToRice"/>
      <el-table-column label="加菜" align="center" prop="vegetables" :formatter="formatToVegetables"/>
      <el-table-column label="加肉" align="center" prop="meat" :formatter="formatToMeat"/>
      <el-table-column label="加蛋" align="center" prop="egg" :formatter="formatToEgg"/>
      <el-table-column label="正餐价格" align="center" prop="price"/>
      <el-table-column label="是否配餐" align="center" prop="openFlag" :formatter="formatToOpenFlag"/>
      <el-table-column label="是否报餐" align="center" prop="nutritionFoodFlag" :formatter="formatToNutritionFoodFlag"/>
      <el-table-column label="营养餐名" align="center" prop="nutritionName" :formatter="formatToNutritionName"/>
      <el-table-column label="方式" align="center" prop="isReplaceFood" :formatter="formatToReplace"/>
      <el-table-column label="营养餐价" align="center" prop="nutritionFoodPrice" :formatter="formatToNutritionFoodPrice"/>
      <el-table-column label="总价" align="center" prop="totalPrice"/>
      <el-table-column label="报餐日期" align="center" prop="createAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['fantang:meals:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['fantang:meals:remove']"
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

    <!-- 添加或修改报餐管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="报餐日期" prop="createAt">
          <el-date-picker clearable size="small" style="width: 200px"
                          v-model="form.createAt"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择报餐日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="报餐类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择报餐类型">
            <el-option label="请选择字典生成" value=""/>
          </el-select>
        </el-form-item>
        <el-form-item label="订单列表" prop="foods">
          <el-input v-model="form.foods" placeholder="请输入订单列表"/>
        </el-form-item>
        <el-form-item label="正餐总价" prop="price">
          <el-input v-model="form.price" placeholder="请输入总价"/>
        </el-form-item>
      </el-form>
      <el-form-item>

      </el-form-item>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addMeals, delMeals, exportMeals, getMeals, listMeals, updateMeals} from "../../../api/fantang/meals";

export default {
  name: "Meals",
  components: {},
  data() {
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [{
          text: '今天',
          onClick(picker) {
            picker.$emit('pick', new Date());
          }
        }, {
          text: '明天',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() + 3600 * 1000 * 24);
            picker.$emit('pick', date);
          }
        }, {
          text: '一周前',
          onClick(picker) {
            const date = new Date();
            date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', date);
          }
        }]
      },

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
      // 报餐管理表格数据
      mealsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        createAt: null,
        type: null,
        createBy: null,
        price: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        createAt: [
          {required: true, message: "报餐日期不能为空", trigger: "blur"}
        ],
        type: [
          {required: true, message: "报餐类型不能为空", trigger: "change"}
        ],
        createBy: [
          {required: true, message: "报餐人不能为空", trigger: "blur"}
        ],
        foods: [
          {required: true, message: "订单列表不能为空", trigger: "blur"}
        ],
        price: [
          {required: true, message: "总价不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {

    // 报餐类型字典翻译
    formatToDinner(row) {
      if (row.type === 1)
        return '早餐';
      if (row.type === 2)
        return '午餐';
      if (row.type === 3)
        return '晚餐';
      if (row.type === 4)
        return '加餐';
    },

    formatToRice(row) {
      if (row.rice)
        return '加饭';
      else
        return '-';
    },
    formatToMeat(row) {
      if (row.meat)
        return '加肉';
      else
        return '-';
    },
    formatToVegetables(row) {
      if (row.vegetables)
        return '加菜';
      else
        return '-';
    },
    formatToEgg(row) {
      if (row.egg)
        return row.egg;
      else
        return '-';
    },

    formatToOpenFlag(row) {
      if (row.openFlag)
        return '是';
      else
        return '-';
    },
    formatToReplace(row) {
      if (row.openFlag) {
        if(row.nutritionFoodFlag) {
          if (row.isReplaceFood)
            return '替';
          else
            return '加';
        } else
          return '-'

      } else
        return '-';
    },


    formatToNutritionName(row) {
      if (row.openFlag) {

        return row.nutritionName;
      } else
        return '-';
    },
    formatToNutritionFoodPrice(row) {
      if (row.openFlag) {
        return row.nutritionFoodPrice;
      } else
        return '-';
    },
    formatToNutritionFoodFlag(row) {
      if (row.openFlag) {
        if (row.nutritionFoodFlag)
          return '报';
        else
          return '停';
      } else
        return '-';

    },

    /** 查询报餐管理列表 */
    getList() {
      this.loading = true;
      listMeals(this.queryParams).then(response => {
        this.mealsList = response.rows;
        this.total = response.total;
        this.loading = false;
        console.log(this.mealsList);
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
        id: null,
        createAt: null,
        type: null,
        patientId: null,
        createBy: null,
        foods: null,
        price: null,
        settlementFlag: null
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
      this.title = "添加报餐管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMeals(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改报餐管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMeals(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMeals(this.form).then(response => {
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
      this.$confirm('是否确认删除报餐管理编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delMeals(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有报餐管理数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportMeals(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
