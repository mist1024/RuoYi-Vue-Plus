<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="规格标题" prop="specificationTitle">
        <el-input
          v-model="queryParams.specificationTitle"
          placeholder="请输入商品规格标题"
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
          v-hasPermi="['product:proItemSpecification:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['product:proItemSpecification:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['product:proItemSpecification:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['product:proItemSpecification:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="proItemSpecificationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="规格名字" align="center" prop="specificationTitle"/>
      <el-table-column label="封面图片" align="center" prop="urlPath">
        <template scope="scope"><img :src="scope.row.urlPath" width="40" height="40" class="banner_pic"/></template>
      </el-table-column>
      <el-table-column label="规格库存" align="center" prop="specificationSku"/>
      <el-table-column label="价格" align="center" prop="price"/>
      <el-table-column label="别名" align="center" prop="aliasName"/>
      <el-table-column label="描述" align="center" prop="description"/>
      <el-table-column label="是否上架" align="center" prop="publishFlag">
        <template scope="scope">{{ scope.row.publishFlag === '0' ? '未上架' : '已上架' }}</template>
      </el-table-column>
      <el-table-column label="上次上架时间" align="center" prop="publishTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.publishTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['product:proItemSpecification:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['product:proItemSpecification:remove']"
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

    <!-- 添加或修改商品规格对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="规格名字" prop="specificationTitle">
          <el-input v-model="form.specificationTitle" placeholder="请输入规格名字"/>
        </el-form-item>
        <el-form-item label="封面图片" prop="urlPath">
          <imageUpload v-model="form.urlPath"/>
        </el-form-item>
        <el-form-item label="规格库存" prop="specificationSku">
          <el-input-number v-model="form.specificationSku" :min="0" placeholder="请输入规格库存"/>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input-number v-model="form.price" :step="0.1" :precision="2" :min="0" placeholder="请输入价格"/>
        </el-form-item>
        <el-form-item label="别名" prop="aliasName">
          <el-input v-model="form.aliasName" placeholder="请输入别名"/>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" maxlength="100"
                    show-word-limit placeholder="请输入描述"
          />
        </el-form-item>
        <el-form-item label="是否上架" prop="publishFlag">
          <el-switch v-model="form.publishFlag"
                     active-color="#13ce66"
                     active-value="1"
                     inactive-value="0"
          ></el-switch>
<!--          <el-input v-model="form.publishFlag" placeholder="请输入是否上架,0未上架,1上架,默认是0"/>-->
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
  listProItemSpecification,
  getProItemSpecification,
  delProItemSpecification,
  addProItemSpecification,
  updateProItemSpecification,
  exportProItemSpecification
} from '@/api/product/proItemSpecification'
import ImageUpload from '@/components/ImageUpload'

export default {
  name: 'ProItemSpecification',
  components: {
    ImageUpload
  },
  props: {
    proItemId: '',
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
      proItemSpecificationList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        itemId: undefined,
        specificationTitle: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        specificationTitle: [
          { required: true, message: '规格名字不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询商品规格列表 */
    getList() {
      this.loading = true
      this.queryParams.itemId = this.proItemId
      listProItemSpecification(this.queryParams).then(response => {
        this.proItemSpecificationList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.proItemSpecificationList = [];
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        itemId: undefined,
        specificationTitle: undefined,
        urlPath: undefined,
        specificationSku: undefined,
        price: undefined,
        aliasName: undefined,
        description: undefined,
        publishFlag: undefined,
        publishTime: undefined,
        createTime: undefined,
        createBy: undefined,
        updateTime: undefined,
        updateBy: undefined
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加商品规格'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getProItemSpecification(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改商品规格'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.form.itemId = this.proItemId
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProItemSpecification(this.form).then(response => {
              this.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addProItemSpecification(this.form).then(response => {
              this.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$confirm('是否确认删除商品规格编号为"' + ids + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return delProItemSpecification(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess('删除成功')
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      this.$confirm('是否确认导出所有商品规格数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return exportProItemSpecification(queryParams)
      }).then(response => {
        this.download(response.msg)
      })
    }
  }
}
</script>


<style lang="css">
.banner_pic {
  border-radius: 5px;
}
</style>
