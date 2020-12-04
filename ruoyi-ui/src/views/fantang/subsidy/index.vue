<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px" v-if="false">
      <el-form-item label="补贴类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择补贴类型" clearable size="small">
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="金额" prop="price">
        <el-input
          v-model="queryParams.price"
          placeholder="请输入金额"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建日期" prop="createAt">
        <el-date-picker clearable size="small" style="width: 200px"
                        v-model="queryParams.createAt"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择创建日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="创建人" prop="createBy">
        <el-input
          v-model="queryParams.createBy"
          placeholder="请输入创建人"
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
          v-hasPermi="['fantang:subsidy:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5" v-if="false">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['fantang:subsidy:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5" v-if="false">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['fantang:subsidy:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5" v-if="false">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['fantang:subsidy:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" v-if="false"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="subsidyList" @selection-change="handleSelectionChange" border>
      <el-table-column type="selection" width="55" align="center" v-if="false"/>
      <el-table-column label="补贴 id" align="center" prop="subsidyId" v-if="false"/>
      <el-table-column label="补贴类型" align="center" prop="type" :formatter="typeFormat"/>
      <el-table-column label="金额" align="center" prop="price"/>
      <el-table-column label="创建日期" align="center" prop="createAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="启用标志" align="center" prop="flag" :formatter="formatFlag"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-money"
            @click="clickSubsidyGiveOut(scope.row)"
            v-hasPermi="['fantang:subsidy:edit']"
          >发放
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['fantang:subsidy:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['fantang:subsidy:remove']"
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


    <!--    补贴发放弹出层-->
    <el-dialog title="选择发放员工" :visible.sync="showPopupSubsidyGiveOut" width="500px" align="center">
      <el-table :data="staffData">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column property="departName" label="科室" width="200"></el-table-column>
        <el-table-column property="name" label="姓名" width="150"></el-table-column>
      </el-table>
      <br>
      <el-button type="primary">发放</el-button>
    </el-dialog>

    <!-- 添加或修改补贴管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="补贴类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择补贴类型">
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="金额" prop="price">
          <el-input v-model="form.price" placeholder="请输入金额"/>
        </el-form-item>
        <el-form-item label="启用标志" prop="flag">
          <el-switch
            v-model="form.flag"
            active-color="#13ce66"
            inactive-color="#ff4949"
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
import {addSubsidy, delSubsidy, exportSubsidy, getSubsidy, listSubsidy, updateSubsidy} from "@/api/fantang/subsidy";
import {staffListWithDepart} from "@/api/fantang/staffInfo";

export default {
  name: "Subsidy",
  components: {},
  data() {
    return {
      staffData: [],
      // 发放弹出层
      showPopupSubsidyGiveOut: false,
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
      // 补贴管理表格数据
      subsidyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 补贴类型字典
      typeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        type: null,
        price: null,
        createAt: null,
        createBy: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        type: [
          {required: true, message: "补贴类型不能为空", trigger: "change"}
        ],
        price: [
          {required: true, message: "金额不能为空", trigger: "blur"}
        ],
        flag: [
          {required: true, message: "启用标志不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("ft_subsidy").then(response => {
      this.typeOptions = response.data;
    });
  },
  methods: {

    //  响应发放补贴按钮
    clickSubsidyGiveOut(row) {
      staffListWithDepart().then(response => {
        console.log(response);
        this.staffData = response.data;
        this.showPopupSubsidyGiveOut = true;
      })

    },
    // 控制补贴列表启用状态的回显
    formatFlag(row) {
      if (row.flag)
        return '启用';
      else
        return '禁用';
    },

    /** 查询补贴管理列表 */
    getList() {
      this.loading = true;
      listSubsidy(this.queryParams).then(response => {
        this.subsidyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 补贴类型字典翻译
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        subsidyId: null,
        type: null,
        price: null,
        subsidyRange: null,
        cycle: null,
        flag: true,
        createAt: null,
        createBy: null,
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
      this.ids = selection.map(item => item.subsidyId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加补贴管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const subsidyId = row.subsidyId || this.ids
      getSubsidy(subsidyId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改补贴管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.subsidyId != null) {
            if (this.form.flag === true) {
              this.form.flag = 1;
            } else {
              this.form.flag = 0;
            }
            updateSubsidy(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            if (this.form.flag === true) {
              this.form.flag = 1;
            } else {
              this.form.flag = 0;
            }
            addSubsidy(this.form).then(response => {
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
      const subsidyIds = row.subsidyId || this.ids;
      this.$confirm('是否确认删除补贴管理编号为"' + subsidyIds + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delSubsidy(subsidyIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有补贴管理数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportSubsidy(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
