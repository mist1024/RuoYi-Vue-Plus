<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="统计日期" prop="statisticsType">
        <el-select v-model="queryParams.statisticsType" placeholder="请选择统计日期" clearable size="small">
          <el-option
              v-for="item in statisticsTypeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="病人姓名" prop="name">
        <el-input v-model="queryParams.name" size="small"></el-input>
      </el-form-item>
      <el-form-item label="营养餐" prop="nutritionFoodId">
        <el-select v-model="queryParams.nutritionFoodId" placeholder="请选择营养餐" clearable size="small">
          <el-option
              v-for="item in nutritionFoodOptions"
              :key="item.id"
              :label="item.name"
              :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="科室" prop="departId">
        <el-select v-model="queryParams.departId" placeholder="请选择科室" clearable size="small">
          <el-option
              v-for="item in departOptions"
              :key="item.departId"
              :label="item.departName"
              :value="item.departId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="用法" prop="cateringUsage">
        <el-select v-model="queryParams.cateringUsage" placeholder="请选择用法" clearable size="small">
          <el-option
              v-for="item in cateringUsageOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="正餐类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择用法" clearable size="small">
          <el-option
              v-for="item in typeOptions"
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
      <el-table-column label="姓名" align="center" prop="name"/>
      <el-table-column label="床号" align="center" prop="bedId"/>
      <el-table-column label="正餐类型" align="center" prop="type"/>
      <el-table-column label="营养餐" align="center" prop="nutritionName"/>
      <el-table-column label="用法" align="center" prop="cateringUsage"/>
      <el-table-column label="用餐日期" align="center" prop="diningAt">

      </el-table-column>
<!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button-->
<!--              size="mini"-->
<!--              type="text"-->
<!--              icon="el-icon-edit"-->
<!--              @click="handleUpdate(scope.row)"-->
<!--              v-hasPermi="['fantang:meals:edit']"-->
<!--          >修改-->
<!--          </el-button>-->
<!--          <el-button-->
<!--              size="mini"-->
<!--              type="text"-->
<!--              icon="el-icon-delete"-->
<!--              @click="handleDelete(scope.row)"-->
<!--              v-hasPermi="['fantang:meals:remove']"-->
<!--          >删除-->
<!--          </el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
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
import {addMeals, delMeals, exportMeals, getMeals, updateMeals} from "@/api/fantang/meals";
import {listStatistics} from "../../../api/fantang/meals";
import {listNutritionFood} from "../../../api/fantang/nutritionFood";
import {listDepart} from "../../../api/fantang/depart";

export default {
  name: "Meals",
  components: {},
  data() {
    return {
      typeOptions: [{
        value: 1,
        label: '早餐'
      }, {
        value: 2,
        label: '午餐'
      }, {
        value: 3,
        label: '晚餐'
      }, {
        value: 4,
        label: '加餐'
      }],
      cateringUsageOptions: [{
        value: 1,
        label: '鼻饲'
      }, {
        value: 2,
        label: '口服'
      }],
      departOptions: [],
      nutritionFoodOptions: [],
      statisticsTypeOptions: [{
        value: 1,
        label: '今日'
      }, {
        value: 2,
        label: '明日'
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
        statisticsType: null,
        name: null,
        nutritionFoodId: null,
        departId: null,
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
    /** 查询报餐管理列表 */
    getList() {
      this.loading = true;
      listStatistics(this.queryParams).then(response => {
        this.mealsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      listNutritionFood().then(response => {
        this.nutritionFoodOptions = response.rows;
      })
      listDepart().then(response => {
        this.departOptions = response.rows;
      })
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
