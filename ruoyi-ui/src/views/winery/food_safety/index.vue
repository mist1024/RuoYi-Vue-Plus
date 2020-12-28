<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="部门ID" prop="deptId">
        <el-input
          v-model="queryParams.deptId"
          placeholder="请输入部门ID"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="生产许可证编号" prop="prodLicense">
        <el-input
          v-model="queryParams.prodLicense"
          placeholder="请输入生产许可证编号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品标准号" prop="standardId">
        <el-input
          v-model="queryParams.standardId"
          placeholder="请输入产品标准号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="厂名" prop="makerName">
        <el-input
          v-model="queryParams.makerName"
          placeholder="请输入厂名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="厂址" prop="makerAddress">
        <el-input
          v-model="queryParams.makerAddress"
          placeholder="请输入厂址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="厂家联系方式" prop="makerContact">
        <el-input
          v-model="queryParams.makerContact"
          placeholder="请输入厂家联系方式"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="配料表" prop="ingredients">
        <el-input
          v-model="queryParams.ingredients"
          placeholder="请输入配料表"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="保质期" prop="period">
        <el-input
          v-model="queryParams.period"
          placeholder="请输入保质期"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="葡萄酒等级" prop="wineLevel">
        <el-input
          v-model="queryParams.wineLevel"
          placeholder="请输入葡萄酒等级"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="储藏方法
" prop="storageMode">
        <el-input
          v-model="queryParams.storageMode"
          placeholder="请输入储藏方法
"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="供应商
" prop="supplier">
        <el-input
          v-model="queryParams.supplier"
          placeholder="请输入供应商
"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="生产日期" prop="makeDate">
        <el-date-picker clearable size="small" style="width: 200px"
          v-model="queryParams.makeDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择生产日期">
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
          v-hasPermi="['winery:food_safety:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['winery:food_safety:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['winery:food_safety:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['winery:food_safety:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="food_safetyList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="规格ID" align="center" prop="id" v-if="false"/>
      <el-table-column label="部门ID" align="center" prop="deptId" />
      <el-table-column label="生产许可证编号" align="center" prop="prodLicense" />
      <el-table-column label="产品标准号" align="center" prop="standardId" />
      <el-table-column label="厂名" align="center" prop="makerName" />
      <el-table-column label="厂址" align="center" prop="makerAddress" />
      <el-table-column label="厂家联系方式" align="center" prop="makerContact" />
      <el-table-column label="配料表" align="center" prop="ingredients" />
      <el-table-column label="保质期" align="center" prop="period" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="葡萄酒等级" align="center" prop="wineLevel" />
      <el-table-column label="储藏方法
" align="center" prop="storageMode" />
      <el-table-column label="供应商
" align="center" prop="supplier" />
      <el-table-column label="生产日期" align="center" prop="makeDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.makeDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['winery:food_safety:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['winery:food_safety:remove']"
          >删除</el-button>
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

    <!-- 添加或修改食品安全详情对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="部门ID" prop="deptId">
          <el-input v-model="form.deptId" placeholder="请输入部门ID" />
        </el-form-item>
        <el-form-item label="生产许可证编号" prop="prodLicense">
          <el-input v-model="form.prodLicense" placeholder="请输入生产许可证编号" />
        </el-form-item>
        <el-form-item label="产品标准号" prop="standardId">
          <el-input v-model="form.standardId" placeholder="请输入产品标准号" />
        </el-form-item>
        <el-form-item label="厂名" prop="makerName">
          <el-input v-model="form.makerName" placeholder="请输入厂名" />
        </el-form-item>
        <el-form-item label="厂址" prop="makerAddress">
          <el-input v-model="form.makerAddress" placeholder="请输入厂址" />
        </el-form-item>
        <el-form-item label="厂家联系方式" prop="makerContact">
          <el-input v-model="form.makerContact" placeholder="请输入厂家联系方式" />
        </el-form-item>
        <el-form-item label="配料表" prop="ingredients">
          <el-input v-model="form.ingredients" placeholder="请输入配料表" />
        </el-form-item>
        <el-form-item label="保质期" prop="period">
          <el-input v-model="form.period" placeholder="请输入保质期" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="葡萄酒等级" prop="wineLevel">
          <el-input v-model="form.wineLevel" placeholder="请输入葡萄酒等级" />
        </el-form-item>
        <el-form-item label="储藏方法
" prop="storageMode">
          <el-input v-model="form.storageMode" placeholder="请输入储藏方法
" />
        </el-form-item>
        <el-form-item label="供应商
" prop="supplier">
          <el-input v-model="form.supplier" placeholder="请输入供应商
" />
        </el-form-item>
        <el-form-item label="生产日期" prop="makeDate">
          <el-date-picker clearable size="small" style="width: 200px"
            v-model="form.makeDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择生产日期">
          </el-date-picker>
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
import { listFood_safety, getFood_safety, delFood_safety, addFood_safety, updateFood_safety, exportFood_safety } from "@/api/winery/food_safety";

export default {
  name: "Food_safety",
  components: {
  },
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
      // 食品安全详情表格数据
      food_safetyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: undefined,
        prodLicense: undefined,
        standardId: undefined,
        makerName: undefined,
        makerAddress: undefined,
        makerContact: undefined,
        ingredients: undefined,
        period: undefined,
        wineLevel: undefined,
        storageMode: undefined,
        supplier: undefined,
        makeDate: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        deptId: [
          { required: true, message: "部门ID不能为空", trigger: "blur" }
        ],
        createBy: [
          { required: true, message: "创建者不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
        standardId: [
          { required: true, message: "产品标准号不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询食品安全详情列表 */
    getList() {
      this.loading = true;
      listFood_safety(this.queryParams).then(response => {
        this.food_safetyList = response.rows;
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
        id: undefined,
        deptId: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
        prodLicense: undefined,
        standardId: undefined,
        makerName: undefined,
        makerAddress: undefined,
        makerContact: undefined,
        ingredients: undefined,
        period: undefined,
        remark: undefined,
        wineLevel: undefined,
        storageMode: undefined,
        supplier: undefined,
        makeDate: undefined
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加食品安全详情";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getFood_safety(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改食品安全详情";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateFood_safety(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFood_safety(this.form).then(response => {
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
      this.$confirm('是否确认删除食品安全详情编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delFood_safety(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有食品安全详情数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportFood_safety(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
