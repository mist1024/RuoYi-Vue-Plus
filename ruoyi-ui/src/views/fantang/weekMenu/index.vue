<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['fantang:weekMenu:edit']"
        >保存
        </el-button>
      </el-col>
      <el-col :span="1.5">
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['fantang:weekMenu:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="weekMenuList"
              :span-method="objectSpanMethod"
              :border="true"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="id" align="center" prop="id" v-if="false"/>
      <el-table-column label="星期几" align="center" prop="weekday" width="80px"/>
      <el-table-column label="类型" align="center" prop="dinnerType" width="80px"/>
      <el-table-column label="菜品列表" align="center" prop="foods" width="500px">
        <template slot-scope="scope">
          <el-select v-model="scope.row.foods"
                     @change="changeFoods(scope.row)"
                     multiple style="width: 400px">
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
        </template>
      </el-table-column>
      <el-table-column label="总价格" align="center" prop="price" width="100px" :formatter="formatPrice"/>
      <el-table-column label="启用标志" align="center" prop="flag" width="180px">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.flag"
            active-text="启用"
            inactive-text="禁用">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['fantang:weekMenu:edit']"
          >保存
          </el-button>
        </template>
      </el-table-column>
    </el-table>

<!--    <pagination-->
<!--      v-show="total>0"-->
<!--      :total="total"-->
<!--      :page.sync="queryParams.pageNum"-->
<!--      :limit.sync="queryParams.pageSize"-->
<!--      @pagination="getList"-->
<!--    />-->

    <!-- 添加或修改每周菜单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form-item label="星期几" prop="weekday">
        <el-select v-model="form.weekday" placeholder="请选择星期几">
          <el-option
            v-for="item in weekdayOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用餐类型" prop="dinnerType">
          <el-select v-model="form.dinnerType" placeholder="请选择用餐类型">
            <el-option
              v-for="item in dinnerTypeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="菜品列表" prop="foods">
          <el-select v-model="form.foods" multiple placeholder="请选择菜品列表">
            <el-option
              v-for="item in foodList"
              :key="item.foodId"
              :label="item.name"
              :value="item.foodId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="启用标志" prop="flag">
          <el-switch
            v-model="form.flag"
            active-text="启用"
            inactive-text="禁用">
          </el-switch>
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
    listWeekMenu,
    getWeekMenu,
    updateWeekMenu,
    exportWeekMenu
  } from "@/api/fantang/weekMenu";
  import {listFood} from "../../../api/fantang/food";

  export default {
    name: "WeekMenu",
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
        // 每周菜单表格数据
        weekMenuList: [],
        // 弹出层标题
        title: "",
        // 菜品列表
        foodList: [],
        // queryParams
        foodQueryParams: {
          pageNum: 1,
          pageSize: 9,
          name: null,
          price: null,
          type: null
        },

        // 是否显示弹出层
        open: false,
        dinnerTypeOptions: [{
          value: '早餐',
          label: '早餐'
        }, {
          value: '午餐',
          label: '午餐'
        }, {
          value: '晚餐',
          label: '晚餐'
        }
        ],
        weekdayOptions: [{
          value: '周一',
          label: '周一'
        }, {
          value: '周二',
          label: '周二'
        }, {
          value: '周三',
          label: '周三'
        }, {
          value: '周四',
          label: '周四'
        }, {
          value: '周五',
          label: '周五'
        }, {
          value: '周六',
          label: '周六'
        }, {
          value: '周日',
          label: '周日'
        }],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 50,
          dinnerType: '',
          weekday: '',
          flag: null
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          dinnerType: [
            {required: true, message: "用餐类型不能为空", trigger: "change"}
          ],
          weekday: [
            {required: true, message: "星期几不能为空", trigger: "change"}
          ],
          foods: [
            {required: true, message: "菜品列表不能为空", trigger: "change"}
          ],
          flag: [
            {required: true, message: "启用标志不能为空", trigger: "blur"}
          ]
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      // 格式化总价的显示，加上“元”
      formatPrice(row) {
        return row.price + " 元";
      },
      // 根据菜单选择的变化，计算总价
      changeFoods(row) {
        console.log("row-->", row);
        console.log("foodList-->", this.foodList)
        let ret = 0;
        for (let i = 0; i < row.foods.length; i++) {
          let foodPrice = row.foods[i];
          this.foodList.forEach(item => {
            if (item.foodId === foodPrice) {
              ret += item.price;
            }
          });
        }
        this.weekMenuList[row.id - 1].price = ret;
      },

      // 控制合并列
      objectSpanMethod({row, column, rowIndex, columnIndex}) {
        if (columnIndex === 1) {
          if (rowIndex % 3 === 0) {
            return {
              rowspan: 3,
              colspan: 1
            };
          } else {
            return {
              rowspan: 0,
              colspan: 0
            };
          }
        }
      },

      /** 查询每周菜单列表 */
      getList() {
        this.loading = true;
        listWeekMenu(this.queryParams).then(response => {
          this.weekMenuList = response.rows;
          this.total = response.total;
          for (let i = 0; i < this.weekMenuList.length; i++) {
            if (this.weekMenuList[i].foods !== null)
              this.weekMenuList[i].foods = this.weekMenuList[i].foods.split(',').map(Number);
          }
          this.loading = false;
        });
        listFood(this.queryParams).then(response => {
          this.foodList = response.rows;
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
          dinnerType: null,
          weekday: null,
          foods: null,
          price: null,
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
      /** 修改按钮操作 */
      handleUpdate(row) {
        row.foods = row.foods.toLocaleString();
        updateWeekMenu(row).then(response => {
          this.msgSuccess("修改成功");
          row.foods = row.foods.split(',').map(Number);
        });

        // this.reset();
        // const id = row.id || this.ids
        // getWeekMenu(id).then(response => {
        //   this.form = response.data;
        //   this.open = true;
        //   this.title = "修改每周菜单";
        // });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.id != null) {
              updateWeekMenu(this.form).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
            }
          }
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有每周菜单数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportWeekMenu(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
      }
    }
  };
</script>
