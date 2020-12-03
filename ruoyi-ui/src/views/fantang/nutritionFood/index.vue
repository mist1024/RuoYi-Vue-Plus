<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['fantang:nutritionFood:add']"
        >新增
        </el-button>
      </el-col>
      <el-button
        type="warning"
        icon="el-icon-download"
        size="mini"
        @click="handleExport"
        v-hasPermi="['fantang:nutritionFood:export']"
      >导出
      </el-button>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="nutritionFoodList" @selection-change="handleSelectionChange" fit border>
      <!--      <el-table-column type="selection" width="55" align="center"/>-->
      <el-table-column label="id" align="center" prop="id" v-if="false"/>
      <el-table-column label="营养餐名称" align="center" prop="name" width="200px" fixed="left">
        <template slot-scope="scope">
          <el-input v-model="scope.row.name"/>
        </template>
      </el-table-column>
      <el-table-column label="价格" align="center" prop="price" fixed="left" width="250px">
        <template slot-scope="scope">
          <el-input-number v-model="scope.row.price" :min="0" :precision="2"/>
        </template>
      </el-table-column>
      <el-table-column label="启用标志" align="center" prop="flag" width="180px" fixed="left">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.flag"
            active-text="启用"
            inactive-text="禁用">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="left" width="100px">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['fantang:nutritionFood:edit']"
          >保存
          </el-button>
          <!--          <el-button-->
          <!--            size="mini"-->
          <!--            type="text"-->
          <!--            icon="el-icon-delete"-->
          <!--            @click="handleDelete(scope.row)"-->
          <!--            v-hasPermi="['fantang:nutritionFood:remove']"-->
          <!--          >停用-->
          <!--          </el-button>-->
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

    <!-- 添加或修改病患营养配餐对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="营养餐名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入营养餐名称"/>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入价格"/>
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
import {
  addNutritionFood,
  deactivate,
  exportNutritionFood,
  listNutritionFood,
  updateNutritionFood
} from "@/api/fantang/nutritionFood";

export default {
  name: "NutritionFood",
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
      // 病患营养配餐表格数据
      nutritionFoodList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        price: null,
        flag: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "营养餐名称不能为空", trigger: "blur"}
        ],
        price: [
          {required: true, message: "价格不能为空", trigger: "blur"}
        ],
        flag: [
          {required: true, message: "启用标志不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询病患营养配餐列表 */
    getList() {
      this.loading = true;
      listNutritionFood(this.queryParams).then(response => {
        this.nutritionFoodList = response.rows;
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
        id: null,
        name: null,
        price: null,
        flag: null,
        createAt: null,
        createBy: null
      };
      this.resetForm("form");
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
      this.title = "添加病患营养配餐";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      console.log(row);
      updateNutritionFood(row).then(response => {
        this.msgSuccess("保存成功");
        this.getList();
      })
      // this.reset();
      // const id = row.id || this.ids
      // getNutritionFood(id).then(response => {
      //   this.form = response.data;
      //   this.open = true;
      //   this.title = "修改病患营养配餐";
      // });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateNutritionFood(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addNutritionFood(this.form).then(response => {
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
      // const ids = row.id || this.ids;
      // this.$confirm('是否确认删除病患营养配餐编号为"' + ids + '"的数据项?', "警告", {
      //     confirmButtonText: "确定",
      //     cancelButtonText: "取消",
      //     type: "warning"
      //   }).then(function() {
      //     return delNutritionFood(ids);
      //   }).then(() => {
      //     this.getList();
      //     this.msgSuccess("删除成功");
      //   })
      console.log(row);
      deactivate(row.id).then(response => {
        this.msgSuccess("停用成功")
        this.getList();
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有病患营养配餐数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportNutritionFood(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
