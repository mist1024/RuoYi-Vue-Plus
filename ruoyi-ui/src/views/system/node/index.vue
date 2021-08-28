<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="节点名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入节点名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类" prop="categary">
        <el-input
          v-model="queryParams.categary"

          placeholder="请输入分类"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="父id" prop="pid">
        <el-input
          v-model="queryParams.pid"
          placeholder="请输入父id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['system:node:add']"
        >新增
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="nodeList"
      row-key="id"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="节点名" prop="name"/>
      <el-table-column label="id" prop="id"/>
      <el-table-column label="分类" align="center" prop="categary"/>
      <el-table-column label="父id" align="center" prop="pid"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:node:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:node:add']"
          >新增
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAddMultiple(scope.row)"
            v-hasPermi="['system:node:add']"
          >新增多个
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:node:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改节点维护对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="节点名" prop="name">
          <el-input v-model="form.name" placeholder="请输入节点名"/>
        </el-form-item>
        <el-form-item label="分类" prop="categary">
          <el-input v-model="form.categary" placeholder="请输入分类"/>
        </el-form-item>
        <el-form-item label="父id" prop="pid">
          <treeselect v-model="form.pid" :options="nodeOptions" :normalizer="normalizer" placeholder="请选择父id"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加多个节点维护对话框 -->
    <el-dialog :title="title" :visible.sync="addMultiple" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="节点名" prop="name">
          <el-input v-model="form.name" placeholder="请输入节点名，多个节点名以#隔开"/>
        </el-form-item>
        <el-form-item label="分类" prop="categary">
          <el-input v-model="form.categary" placeholder="多个分类名以#隔开,若分类为空以空格代替"/>
        </el-form-item>
        <el-form-item label="父id" prop="pid">
          <treeselect v-model="form.pid" :options="nodeOptions" :normalizer="normalizer" placeholder="请选择父id"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitNewForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listNode, getNode, delNode, addNode, updateNode, exportNode, addMultipleNode} from "@/api/system/node";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Node",
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
      // 节点维护表格数据
      nodeList: [],
      // 节点维护树选项
      nodeOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否弹出添加多个对话框
      addMultiple: false,
      // 查询参数
      queryParams: {
        name: null,
        categary: null,
        pid: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询节点维护列表 */
    getList() {
      this.loading = true;
      listNode(this.queryParams).then(response => {
        this.nodeList = this.handleTree(response.data, "id", "pid");
        this.loading = false;
      });
    },
    /** 转换节点维护数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.id,
        label: node.name,
        children: node.children
      };
    },
    /** 查询节点维护下拉树结构 */
    getTreeselect() {
      listNode().then(response => {
        this.nodeOptions = [];
        const data = {id: 0, name: '顶级节点', children: []};
        data.children = this.handleTree(response.data, "id", "pid");
        this.nodeOptions.push(data);
      });
    },
    // 取消按钮
    cancel() {
      this.addMultiple = false;
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        name: null,
        categary: null,
        pid: null
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
      if (row != null && row.id) {
        this.form.pid = row.id;
      } else {
        this.form.pid = 0;
      }
      this.open = true;
      this.title = "添加节点维护";
    },
    /** 新增多个按钮操作 */
    handleAddMultiple(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.id) {
        this.form.pid = row.id;
      } else {
        this.form.pid = 0;
      }
      this.addMultiple = true;
      this.title = "添加多个节点维护";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      this.getTreeselect();
      if (row != null) {
        this.form.pid = row.id;
      }
      getNode(row.id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改节点维护";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateNode(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addNode(this.form).then(response => {
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
    submitNewForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          console.log(this.form)
          addMultipleNode(this.form).then(response => {
            this.msgSuccess("新增成功");
            this.addMultiple = false;
            this.getList();
          }).catch(e => {
            console.log(e)
            this.addMultiple = false;
          }).finally(() => {
            this.buttonLoading = false;
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('是否确认删除节点维护编号为"' + row.id + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.loading = true;
        return delNode(row.id);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(() => {
      });
    }
  }
};
</script>
