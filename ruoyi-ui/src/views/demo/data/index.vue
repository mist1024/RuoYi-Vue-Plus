<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item label="节点名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入节点名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关系" prop="categary">
        <el-input
          v-model="queryParams.categary"
          placeholder="请输入关系"
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
          v-hasPermi="['demo:data:add']"
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
export default {
  name: "index",
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
  methods: {
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
  },
}
</script>

<style scoped>

</style>
