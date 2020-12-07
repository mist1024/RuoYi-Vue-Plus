<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="姓名" prop="patientName">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="床号" prop="bedId">
        <el-input
          v-model="queryParams.bedId"
          placeholder="请输入床号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="科室" prop="departId">
        <el-select v-model="queryParams.departId" placeholder="请选择科室">
          <el-option
            v-for="item in departOptions"
            :key="item.departName"
            :label="item.departName"
            :value="item.departId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="住院号" prop="hospitalId">
        <el-input
          v-model="queryParams.hospitalId"
          placeholder="请输入住院号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="启用状态" prop="flag">
        <el-select v-model="queryParams.flag" placeholder="请选择启用状态"
                   clearable
                   size="small"
                   @keyup.enter.native="handleQuery">
          <el-option
            v-for="item in flagOptions"
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
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['fantang:foodDemand:add']"
        >导入新入院配置数据
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['fantang:foodDemand:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="foodDemandList" @selection-change="handleSelectionChange" border>
      <!--      <el-table-column type="selection" width="55" align="center"/>-->
      <el-table-column label="id" align="center" prop="id" v-if="false"/>
      <!--      <el-table-column label="住院号" align="center" prop="hospitalId"/>-->
      <el-table-column label="科室" align="center" prop="departName" width="100px"/>
      <el-table-column label="姓名" align="center" prop="name" width="100px"/>
      <el-table-column label="床号" align="center" prop="bedId" width="100px"/>
      <el-table-column label="正餐" align="center" prop="type" :formatter="typeFormat" width="100px"/>
      <el-table-column label="正餐清单" align="center" prop="foods" :formatter="formatFoods"/>
      <el-table-column label="加菜" align="center" prop="vegetables" width="80px" :formatter="formatVegetables"/>
      <el-table-column label="加肉" align="center" prop="meat" width="80px" :formatter="formatMeat"/>
      <el-table-column label="加饭" align="center" prop="rice" width="80px" :formatter="formatRice"/>
      <el-table-column label="加蛋" align="center" prop="egg" width="80px"/>
      <el-table-column label="营养配餐" align="center" prop="nutritionFood" width="120px"/>
      <!--      <el-table-column label="更新日期" align="center" prop="updateAt" width="180">-->
      <!--        <template slot-scope="scope">-->
      <!--          <span>{{ parseTime(scope.row.updateAt, '{y}-{m}-{d}') }}</span>-->
      <!--        </template>-->
      <!--      </el-table-column>-->
      <el-table-column label="启用状态" align="center" prop="flag" width="80px"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['fantang:foodDemand:edit']"
          >修改
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

    <!-- 添加或修改病人报餐对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" :disabled="true"/>
        </el-form-item>
        <el-form-item label="床号" prop="bedId">
          <el-input v-model="form.bedId" :disabled="true"/>
        </el-form-item>
        <el-form-item label="正餐" prop="type">
          <el-input v-model="form.type" :disabled="true"/>
        </el-form-item>
        <el-form-item label="正餐清单" prop="foods">
          <el-select v-model="form.foods"
                     multiple style="width: 380px">
            <el-option
              v-for="item in foodList"
              :key="item.foodId"
              :label="item.name"
              :value="item.foodId">
              <span style="float: left; width:40px">{{ item.name }}</span>
              <el-divider direction="vertical"></el-divider>
              <span style="color: #8492a6; font-size: 13px">{{ item.price }} 元</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="加菜" prop="vegetables">
          <el-switch
            v-model="form.vegetables"
            active-text="是"
            inactive-text="否">
          </el-switch>
        </el-form-item>
        <el-form-item label="加肉" prop="meat">
          <el-switch
            v-model="form.meat"
            active-text="是"
            inactive-text="否">
          </el-switch>
        </el-form-item>
        <el-form-item label="加饭" prop="rice">
          <el-switch
            v-model="form.rice"
            active-text="是"
            inactive-text="否">
          </el-switch>
        </el-form-item>
        <el-form-item label="加蛋" prop="egg">
          <el-input-number v-model="form.egg" :min="0" :max="5"/>
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
  addFoodDemand,
  exportFoodDemand,
  getFoodDemand,
  listFoodDemand,
  updateFoodDemand
} from "@/api/fantang/foodDemand";
import {listFood} from "@/api/fantang/food";
import {listDepart} from "@/api/fantang/depart";

export default {
  name: "FoodDemand",
  components: {},
  data() {
    return {
      departOptions: [],
      foodList: [],
      flagOptions: [{
        value: 1,
        label: '启用'
      }, {
        value: 0,
        label: '禁用'
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
      // 病人报餐表格数据
      foodDemandList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 正餐类型字典
      typeOptions: [],
      // 更新来源字典
      updateFromOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        flag: null,
        name: null,
        bedId: null,
        departName: null,
        hospitalId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        type: [
          {required: true, message: "正餐类型不能为空", trigger: "change"}
        ],
      }
    };
  },

  created() {
    this.getList();
    this.getDicts("ft_book_type").then(response => {
      this.typeOptions = response.data;
    });
    this.getDicts("ft_update_from").then(response => {
      this.updateFromOptions = response.data;
    });
    listDepart().then(response => {
      console.log(response);
      this.departOptions = response.rows;
    })
  },

  beforeCreate() {
    listFood(this.queryParams).then(response => {
      this.foodList = response.rows;
      this.loading = false;
    });
  },
  methods: {
    // 格式化菜单回显文字
    formatFoods(row) {
      const _this = this;
      let arr = row.foods.split(",").map(Number);
      let ret = arr.map(item => {
        let obj = _this.foodList.find((value => {
          return value.foodId === item;
        }));
        return obj.name;
      });
      let str = ret.toString();
      if (row.vegetables > 0)
        str += ",加菜";
      if (row.rice > 0)
        str += ",加饭"
      if (row.meat > 0)
        str += ",加肉"
      if (row.egg > 0)
        str += ",加蛋" + row.egg;
      return str;
    },
    formatVegetables(row) {
      if (row.vegetables)
        return "是";
      return "否";
    },
    formatMeat(row) {
      if (row.meat)
        return "是";
      return "否";
    },

    formatRice(row) {
      if (row.rice)
        return "是";
      return "否";
    },
    formatEgg(row) {
      if (row.egg === null || row.egg === 0)
        return "否";
      return "是";
    },
    /** 查询病人报餐列表 */
    getList() {
      this.loading = true;
      listFoodDemand(this.queryParams).then(response => {
        this.foodDemandList = response.rows;
        this.total = response.total;

        this.loading = false;
      });
    },
    // 正餐类型字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
    // 更新来源字典翻译
    updateFromFormat(row, column) {
      return this.selectDictLabel(this.updateFromOptions, row.updateFrom);
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
        patientId: null,
        foods: null,
        type: null,
        createAt: null,
        createBy: null,
        updateAt: null,
        vegetables: null,
        updateBy: null,
        meat: null,
        updateFrom: null,
        rice: null,
        egg: null,
        orderInfo: null,
        flag: null
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
      this.title = "添加病人报餐";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getFoodDemand(id).then(response => {
        this.form = response.data;
        this.form.foods = this.form.foods.split(",").map(Number);
        this.form.type = this.selectDictLabel(this.typeOptions, row.type);
        this.open = true;
        this.title = "修改病人报餐";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            this.form.foods = this.form.foods.toString();
            if (this.form.type === '早餐')
              this.form.type = 1;
            else if (this.form.type === '午餐')
              this.form.type = 2;
            else if (this.form.type === '晚餐')
              this.form.type = 3;
            else
              this.form.type = 4;
            updateFoodDemand(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFoodDemand(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有病人报餐数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportFoodDemand(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
