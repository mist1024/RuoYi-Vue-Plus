<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
<!--      <el-form-item label="部门ID" prop="deptId">-->
<!--        <el-input-->
<!--          v-model="queryParams.deptId"-->
<!--          placeholder="请输入部门ID"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="规格名称" prop="specName">
        <el-input
          v-model="queryParams.specName"
          placeholder="请输入规格名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="规格说明" prop="specDesc">
        <el-input
          v-model="queryParams.specDesc"
          placeholder="请输入规格说明"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="规格图片" prop="specImg">-->
<!--        <el-input-->
<!--          v-model="queryParams.specImg"-->
<!--          placeholder="请输入规格图片"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="规格单价" prop="specPrice">
        <el-input
          v-model="queryParams.specPrice"
          placeholder="请输入规格单价"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
<!--      <el-form-item label="规格详细" prop="detailSpec">-->
<!--        <el-input-->
<!--          v-model="queryParams.detailSpec"-->
<!--          placeholder="请输入规格详细"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
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
          v-hasPermi="['winery:goods_spec:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['winery:goods_spec:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['winery:goods_spec:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['winery:goods_spec:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="specList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="规格ID" align="center" prop="id" v-if="false"/>
<!--      <el-table-column label="部门ID" align="center" prop="deptId" />-->
      <el-table-column label="规格名称" align="center" prop="specName" />
      <el-table-column label="规格图片" align="center" prop="specImg" >
        <template slot-scope="scope">
          <el-image :src="scope.row.specImg|getImageForKey" style="width: 60px; height: 60px"/>
        </template>
      </el-table-column>
      <el-table-column label="规格单价（元）" align="center" prop="specPrice" />
      <el-table-column label="规格说明" align="center" prop="specDesc" />


<!--      <el-table-column label="规格详细" align="center" prop="detailSpec" />-->
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['winery:goods_spec:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['winery:goods_spec:remove']"
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

    <!-- 添加或修改商品规格对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px" >
<!--        <el-form-item label="部门ID" prop="deptId">-->
<!--          <el-input v-model="form.deptId" placeholder="请输入部门ID" />-->
<!--        </el-form-item>-->
        <el-form-item label="规格名称" prop="specName">
          <el-input v-model="form.specName" placeholder="请输入规格名称" />
        </el-form-item>
        <el-form-item label="规格说明" prop="specDesc">
          <el-input v-model="form.specDesc" placeholder="请输入规格说明" />
        </el-form-item>
        <el-form-item label="规格图片" prop="specImg">
<!--          <el-input v-model="form.specImg" placeholder="请输入规格图片" />-->
          <uploadImage v-model="form.specImg"/>
        </el-form-item>
        <el-form-item label="规格单价（元）" prop="specPrice">
          <el-input v-model="form.specPrice" type="number" placeholder="请输入规格单价" />
        </el-form-item>
        <el-form-item label="规格详细" prop="detailSpec">
          <el-input v-model="form.detailSpec" placeholder="请输入规格详细" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
import { listSpec, getSpec, delSpec, addSpec, updateSpec, exportSpec } from "@/api/goods/goods_spec";
import UploadImage from '@/components/UploadImage';
import CommonMixin from "@/mixin/common";

export default {
  name: "GoodsSpec",
  mixins:[CommonMixin],
  components: {
    UploadImage
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
      // 商品规格表格数据
      specList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: undefined,
        specName: undefined,
        specDesc: undefined,
        specImg: undefined,
        specPrice: undefined,
        detailSpec: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询商品规格列表 */
    getList() {
      this.loading = true;
      listSpec(this.queryParams).then(response => {
        this.specList = response.rows;
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
        updateTime: undefined,
        updateBy: undefined,
        specName: undefined,
        specDesc: undefined,
        specImg: undefined,
        specPrice: undefined,
        detailSpec: undefined,
        remark: undefined
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
      this.title = "添加商品规格";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSpec(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商品规格";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {



            updateSpec(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSpec(this.form).then(response => {
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
      this.$confirm('是否确认删除商品规格编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delSpec(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有商品规格数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportSpec(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
