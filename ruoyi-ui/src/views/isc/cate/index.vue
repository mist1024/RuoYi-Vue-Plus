<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="分类名称" prop="cateName">
        <el-input
          v-model="queryParams.cateName"
          placeholder="请输入分类名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="启用状态" prop="enabled">
        <el-select v-model="queryParams.enabled" placeholder="请选择启用状态" clearable size="small">
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
	    <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['isc:cate:add']"
        >新增</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="cateList"
      row-key="cateId"
      default-expand-all
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="分类名称" align="center" prop="cateName"  width="160"/>
      <el-table-column label="启用状态" align="center" prop="enabled" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.enabled"/>
        </template>
      </el-table-column>
      <el-table-column label="显示顺序" align="center" prop="orderNum" />
      <el-table-column label="更新者" align="center" prop="updateBy" />
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['isc:cate:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['isc:cate:add']"
          >新增</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['isc:cate:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改服务分类对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="父分类" prop="parentId">
          <treeselect v-model="form.parentId" :options="cateOptions" :normalizer="normalizer" placeholder="请选择父分类ID" />
        </el-form-item>
        <el-form-item label="分类名称" prop="cateName">
          <el-input v-model="form.cateName" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="启用状态" prop="enabled">
          <el-radio-group v-model="form.enabled">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="显示顺序" prop="orderNum">
          <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCate, getCate, delCate, addCate, updateCate } from "@/api/isc/cate";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Cate",
  dicts:['sys_normal_disable'],
  components: {
    Treeselect
  },
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 服务分类表格数据
      cateList: [],
      // 服务分类树选项
      cateOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        cateName: null,
        enabled: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        parentId: [
          { required: true, message: "父分类不能为空", trigger: "change" }
        ],
        cateName: [
          { required: true, message: "分类名称不能为空", trigger: "change" },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        enabled: [
          { required: true, message: "启用状态不能为空", trigger: "change" }
        ],
        orderNum: [
          { required: true, message: "显示顺序不能为空", trigger: "change" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询服务分类列表 */
    getList() {
      this.loading = true;
      listCate(this.queryParams).then(response => {
        this.cateList = this.handleTree(response.data, "cateId", "parentId");
        this.loading = false;
      });
    },
    /** 转换服务分类数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.cateId,
        label: node.cateName,
        children: node.children
      };
    },
	/** 查询服务分类下拉树结构 */
    getTreeselect() {
      listCate().then(response => {
        this.cateOptions = [];
        const data = { cateId: 0, cateName: '顶级节点', children: [] };
        data.children = this.handleTree(response.data, "cateId", "parentId");
        this.cateOptions.push(data);
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
        cateId: null,
        parentId: 0,
        cateName: null,
        enabled: "0",
        createBy: null,
        orderNum: 99,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.cateId) {
        this.form.parentId = row.cateId;
      } else {
        this.form.parentId = 0;
      }
      this.open = true;
      this.title = "添加服务分类";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
	  this.loading = true;
      this.reset();
      this.getTreeselect();
      if (row != null) {
        this.form.parentId = row.cateId;
      }
      getCate(row.cateId).then(response => {
	    this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改服务分类";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
		  this.buttonLoading = true;
          if (this.form.cateId != null) {
            updateCate(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addCate(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('是否确认删除服务分类编号为"' + row.cateId + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
		  this.loading = true;
          return delCate(row.cateId);
        }).then(() => {
		  this.loading = false;
          this.getList();
          this.msgSuccess("删除成功");
      }).finally(() => {
              this.loading = false;
      });
    }
  }
};
</script>
