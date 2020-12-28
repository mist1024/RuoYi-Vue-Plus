<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="品牌" prop="brand">
        <el-input
          v-model="queryParams.brand"
          placeholder="请输入品牌"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="净含量" prop="capacity">
        <el-input
          v-model="queryParams.capacity"
          placeholder="请输入净含量"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="采摘年份" prop="year">
        <el-input
          v-model="queryParams.year"
          placeholder="请输入采摘年份"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="酒精度" prop="alcohol">
        <el-input
          v-model="queryParams.alcohol"
          placeholder="请输入酒精度"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="葡萄酒种类" prop="wineType">
        <el-select v-model="queryParams.wineType" placeholder="请选择葡萄酒种类" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
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
      <el-form-item label="葡萄种类" prop="grape">
        <el-input
          v-model="queryParams.grape"
          placeholder="请输入葡萄种类"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="包装方式" prop="packingType">
        <el-select v-model="queryParams.packingType" placeholder="请选择包装方式" clearable size="small">
          <el-option label="请选择字典生成" value="" />
        </el-select>
      </el-form-item>
      <el-form-item label="醒酒时间" prop="decanteDuration">
        <el-input
          v-model="queryParams.decanteDuration"
          placeholder="请输入醒酒时间"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支数" prop="count">
        <el-input
          v-model="queryParams.count"
          placeholder="请输入支数"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="香味" prop="aroma">
        <el-input
          v-model="queryParams.aroma"
          placeholder="请输入香味"
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
          v-hasPermi="['winery:wine_detail:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['winery:wine_detail:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['winery:wine_detail:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['winery:wine_detail:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="wine_detailList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="规格ID" align="center" prop="id" v-if="false"/>
      <el-table-column label="品牌" align="center" prop="brand" />
      <el-table-column label="净含量" align="center" prop="capacity" />
      <el-table-column label="采摘年份
" align="center" prop="year" />
      <el-table-column label="酒精度" align="center" prop="alcohol" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="葡萄酒种类" align="center" prop="wineType" />
      <el-table-column label="葡萄酒等级" align="center" prop="wineLevel" />
      <el-table-column label="葡萄种类" align="center" prop="grape" />
      <el-table-column label="糖分" align="center" prop="sugarContent" />
      <el-table-column label="包装方式" align="center" prop="packingType" />
      <el-table-column label="醒酒时间" align="center" prop="decanteDuration" />
      <el-table-column label="支数" align="center" prop="count" />
      <el-table-column label="香味" align="center" prop="aroma" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['winery:wine_detail:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['winery:wine_detail:remove']"
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

    <!-- 添加或修改葡萄酒规格详情对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="品牌" prop="brand">
          <el-input v-model="form.brand" placeholder="请输入品牌" />
        </el-form-item>
        <el-form-item label="净含量" prop="capacity">
          <el-input v-model="form.capacity" placeholder="请输入净含量" />
        </el-form-item>
        <el-form-item label="采摘年份" prop="year">
          <el-input v-model="form.year" placeholder="请输入采摘年份" />
        </el-form-item>
        <el-form-item label="酒精度" prop="alcohol">
          <el-input v-model="form.alcohol" placeholder="请输入酒精度" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="葡萄酒种类" prop="wineType">
          <el-select v-model="form.wineType" placeholder="请选择葡萄酒种类">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="葡萄酒等级" prop="wineLevel">
          <el-input v-model="form.wineLevel" placeholder="请输入葡萄酒等级" />
        </el-form-item>
        <el-form-item label="葡萄种类" prop="grape">
          <el-input v-model="form.grape" placeholder="请输入葡萄种类" />
        </el-form-item>
        <el-form-item label="糖分">
          <editor v-model="form.sugarContent" :min-height="192"/>
        </el-form-item>
        <el-form-item label="包装方式" prop="packingType">
          <el-select v-model="form.packingType" placeholder="请选择包装方式">
            <el-option label="请选择字典生成" value="" />
          </el-select>
        </el-form-item>
        <el-form-item label="醒酒时间" prop="decanteDuration">
          <el-input v-model="form.decanteDuration" placeholder="请输入醒酒时间" />
        </el-form-item>
        <el-form-item label="支数" prop="count">
          <el-input v-model="form.count" placeholder="请输入支数" />
        </el-form-item>
        <el-form-item label="香味" prop="aroma">
          <el-input v-model="form.aroma" placeholder="请输入香味" />
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
import { listWine_detail, getWine_detail, delWine_detail, addWine_detail, updateWine_detail, exportWine_detail } from "@/api/winery/wine_detail";
import Editor from '@/components/Editor';

export default {
  name: "Wine_detail",
  components: {
    Editor,
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
      // 葡萄酒规格详情表格数据
      wine_detailList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: undefined,
        brand: undefined,
        capacity: undefined,
        year: undefined,
        alcohol: undefined,
        wineType: undefined,
        wineLevel: undefined,
        grape: undefined,
        sugarContent: undefined,
        packingType: undefined,
        decanteDuration: undefined,
        count: undefined,
        aroma: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
        capacity: [
          { required: true, message: "净含量不能为空", trigger: "blur" }
        ],
        alcohol: [
          { required: true, message: "酒精度不能为空", trigger: "blur" }
        ],
        count: [
          { required: true, message: "支数不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询葡萄酒规格详情列表 */
    getList() {
      this.loading = true;
      listWine_detail(this.queryParams).then(response => {
        this.wine_detailList = response.rows;
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
        brand: undefined,
        capacity: undefined,
        year: undefined,
        alcohol: undefined,
        remark: undefined,
        wineType: undefined,
        wineLevel: undefined,
        grape: undefined,
        sugarContent: undefined,
        packingType: undefined,
        decanteDuration: undefined,
        count: undefined,
        aroma: undefined
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
      this.title = "添加葡萄酒规格详情";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getWine_detail(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改葡萄酒规格详情";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWine_detail(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWine_detail(this.form).then(response => {
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
      this.$confirm('是否确认删除葡萄酒规格详情编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delWine_detail(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有葡萄酒规格详情数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportWine_detail(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
