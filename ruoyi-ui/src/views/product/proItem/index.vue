<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="产品名字" prop="proName">
        <el-input
          v-model="queryParams.proName"
          placeholder="请输入产品名字"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="库存" prop="sku">
        <el-input
          v-model="queryParams.sku"
          placeholder="请输入库存(限量库存)"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否发布" prop="publishFlag">
        <el-select v-model="queryParams.publishFlag">
          <el-option label="已发布" value="1"/>
          <el-option label="未发布" value="0"/>
        </el-select>
      </el-form-item>
      <el-form-item label="发布时间" prop="publishTime">
        <el-date-picker
          v-model="dateRange"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
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
          v-hasPermi="['product:proItem:add']"
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
          v-hasPermi="['product:proItem:edit']"
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
          v-hasPermi="['product:proItem:remove']"
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
          v-hasPermi="['product:proItem:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="proItemList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="主键id" align="center" prop="id" v-if="false"/>
      <el-table-column label="产品名字" align="center" prop="proName"/>
      <el-table-column label="价钱" align="center" prop="price"/>
      <el-table-column label="库存" align="center" prop="sku"/>
      <el-table-column label="封面图片" align="center" prop="coverImage">
        <template scope="scope"><img :src="scope.row.coverImage" width="40" height="40" class="banner_pic"/></template>
      </el-table-column>
      <el-table-column label="是否发布" align="center" prop="publishFlag">
        <template scope="scope">{{ scope.row.publishFlag === 0 ? '未发布' : '已发布' }}</template>
      </el-table-column>
      <el-table-column label="发布时间" align="center" prop="publishTime" width="180">
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
            v-hasPermi="['product:proItem:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="editSpecification(scope.row)"
            v-hasPermi="['product:proItem:edit']"
          >编辑规格
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['product:proItem:remove']"
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

    <!-- 添加或修改商品详情对话框 -->
    <el-drawer :title="title" :visible.sync="open" size="70%" append-to-body>
      <div class="drawerClass">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="产品名字" prop="proName">
            <el-input v-model="form.proName" placeholder="请输入产品名字"/>
          </el-form-item>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="价钱" prop="price">
                <el-input-number v-model="form.price" :step="0.1" :precision="2" :min="0" placeholder="请输入价钱"
                                 style="width: 100%"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="限量库存" prop="sku">
                <el-input-number v-model="form.sku" :min="0" placeholder="请输入库存(限量库存)" style="width: 100%"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="类别标签">
            <el-select v-model="form.proItemCategoryIds" style="width: 100%" multiple placeholder="请选择">
              <el-option
                v-for="item in categories"
                :key="item.id"
                :label="item.categoryName"
                :value="item.id"
              >
              </el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="描述">
            <EditorDc v-model="form.description" :min-height="200"/>
          </el-form-item>
          <el-form-item label="封面图片">
            <imageUpload v-model="form.coverImage"/>
          </el-form-item>
          <el-form-item label="轮播图">
            <el-upload
              action="#"
              list-type="picture-card"
              :on-preview="handlePictureCardPreview"
              :on-remove="handleRemove"
              :http-request="uploadSwiper"
              :before-upload="beforeSwiperUpload"
              :file-list="picList"
            >
              <i class="el-icon-plus"></i>
            </el-upload>
            <el-dialog :visible.sync="dialogVisible" title="预览" width="800" append-to-body>
              <img :src="dialogImageUrl" style="display: block; max-width: 100%; margin: 0 auto;">
            </el-dialog>
          </el-form-item>
          <el-form-item label="是否发布">
            <el-radio-group v-model="form.publishFlag">
              <el-radio :label="1">是</el-radio>
              <el-radio :label="0">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </div>
    </el-drawer>

    <el-drawer title="编辑规格" :visible.sync="specificationOpen" v-if="specificationOpen" size="70%" append-to-body>
      <div class="drawerClass">
        <ProItemSpecification :pro-item-id="itemId"/>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import {
  addProItem,
  delProItem,
  exportProItem,
  getProItem,
  listProItem,
  updateProItem,
  uploadFile
} from '@/api/product/proItem'
import {listCategoriesWithoutPage} from '@/api/product/proCategory'
import ImageUpload from '@/components/ImageUpload'
import EditorDc from '@/components/Editor/EditorDC'
import ProItemSpecification from '../proItemSpecification/index'

export default {
  name: 'ProItem',
  components: {
    ImageUpload,
    EditorDc,
    ProItemSpecification
  },
  data() {
    return {
      dateRange: "",
      // swiper相关
      dialogImageUrl: '',
      dialogVisible: false,
      picList: [],
      categories: [],
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
      // 商品详情表格数据
      proItemList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      specificationOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        proName: undefined,
        sku: undefined,
        coverImage: undefined,
        publishFlag: undefined,
        publishStartTime: undefined,
        publishEndTime: undefined
      },
      // 表单参数
      form: {},
      itemId: '',
      specificationForm: {},
      // 表单校验
      rules: {
        proName: [
          {required: true, message: '产品名字不能为空', trigger: 'blur'}
        ],
        price: [
          {required: true, message: '起步价不能为空', trigger: 'blur'}
        ],
        description: [
          {required: true, message: '描述不能为空', trigger: 'blur'}
        ],
        coverImage: [
          {required: true, message: '封面图片不能为空', trigger: 'blur'}
        ],
        publishFlag: [
          {required: true, message: '是否发布不能为空', trigger: 'blur'}
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getCategories()
  },
  methods: {
    /** 查询商品分类**/
    getCategories() {
      listCategoriesWithoutPage().then(resp => {
        this.categories = resp
      })
    },
    /** 查询商品详情列表 */
    getList() {
      this.loading = true
      listProItem(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.proItemList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        proName: undefined,
        price: undefined,
        description: undefined,
        sku: undefined,
        coverImage: undefined,
        proItemPictures: undefined,
        proItemCategoryIds: undefined,
        publishFlag: undefined,
        publishTime: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined
      }
      this.picList = []
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
      this.dateRange = ''
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
      this.title = '添加商品详情'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getProItem(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改商品详情'
        this.picList = response.data.proItemPictures
      })
    },

    editSpecification(row) {
      this.itemId = row.id
      this.specificationOpen = true
      // const itemId = row.id || this.ids
      // listProItemSpecification({ itemId: itemId }).then(response => {
      //   this.specificationForm = response.rows
      //   this.specificationOpen = true
      // })
    },
    /** 提交按钮 */
    submitForm() {
      this.form.proItemPictures = this.picList
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProItem(this.form).then(response => {
              this.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addProItem(this.form).then(response => {
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
      this.$confirm('是否确认删除商品详情编号为"' + ids + '"的数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        return delProItem(ids)
      }).then(() => {
        this.getList()
        this.msgSuccess('删除成功')
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams
      this.$confirm('是否确认导出所有商品详情数据项?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function () {
        return exportProItem(queryParams)
      }).then(response => {
        this.download(response.msg)
      })
    },

    // swiper相关图片列表上传
    uploadSwiper(item) {
      const formData = new FormData()
      formData.append('file', item.file)
      const uid = item.file.uid
      uploadFile(formData).then(res => {
        this.picList.push({key: uid, value: res.data.url, url: res.data.url})
        this.emptyUpload()
      }).catch((e) => {
        console.log(e)
        this.$message.error('上传失败，请重新上传')
        this.emptyUpload()
      })
    },
    beforeSwiperUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isPng = file.type === 'image/png'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG && !isPng) {
        this.$message.error('上传图片只能是 JPG或png 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 2MB!')
      }
      return (isJPG || isPng) && isLt2M
    },
    handleRemove(file) {
      for (const i in this.picList) {
        if (this.picList[i].url === file.url) {
          this.picList.splice(i, 1)
        }
      }
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    /**
     * 清空上传组件
     */
    emptyUpload() {
      const mainImg = this.$refs.upload
      if (mainImg) {
        if (mainImg.length) {
          mainImg.forEach(item => {
            item.clearFiles()
          })
        } else {
          this.$refs.upload.clearFiles()
        }
      }
    },
  }
}
</script>


<style>
/* /deep/  */
.el-upload-list__item.is-ready {
  display: none;
}

.drawerClass {
  padding: 0px 20px 30px 30px;
}

</style>
