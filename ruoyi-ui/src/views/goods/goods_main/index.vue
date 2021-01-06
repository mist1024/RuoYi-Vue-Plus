<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="商品名称" prop="goodsName">
        <el-input
          v-model="queryParams.goodsName"
          placeholder="请输入商品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品简称" prop="goodsAlias">
        <el-input
          v-model="queryParams.goodsAlias"
          placeholder="请输入商品简称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="商品类型" prop="goodsType">
        <el-select v-model="queryParams.goodsType" placeholder="请选择商品类型" clearable size="small">
          <el-option label="请选择字典生成" value=""/>
        </el-select>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input
          v-model="queryParams.remark"
          placeholder="请输入商品简称"
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
          v-hasPermi="['goods:goods_main:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['goods:goods_main:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['goods:goods_main:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['goods:goods_main:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="goodsList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="商品ID" align="center" prop="id" v-if="false"/>
      <el-table-column label="商品名称" align="center" prop="goodsName"/>
      <el-table-column label="商品简称" align="center" prop="goodsAlias"/>
      <el-table-column label="商品类型" align="center" prop="goodsType"/>
      <!--      <el-table-column label="关联规格" align="center" prop="goodsSpec"/>-->
      <!--      <el-table-column label="商品说明" align="center" prop="goodsDesc"/>-->
      <el-table-column label="商品封面" align="center" prop="goodsFaceImg">
        <template slot-scope="scope">
          <el-image :src="scope.row.goodsFaceImg|getImageForKey" style="width: 60px; height: 60px"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="state" :formatter="stateFormat" width="100px">
        <template slot-scope="scope">
          <el-tag :type="scope.row.state === 1 ? 'success' : 'danger'">
            {{scope.row.state | getStateName(stateOptions)}}
          </el-tag>
        </template>
      </el-table-column>
      <!--      <el-table-column label="商品图片" align="center" prop="goodsImg"/>-->
      <el-table-column label="备注" align="center" prop="remark"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['goods:goods_main:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['goods:goods_main:remove']"
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

    <!-- 添加或修改商品信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-form-item label="商品名称" prop="goodsName">
          <el-input v-model="form.goodsName" placeholder="请输入商品名称"/>
        </el-form-item>
        <el-form-item label="商品简称" prop="goodsAlias">
          <el-input v-model="form.goodsAlias" placeholder="请输入商品简称"/>
        </el-form-item>
        <el-form-item label="商品封面" prop="goodsFaceImg">
          <upload-image :value="form.goodsFaceImg" @input="inputGoodsFaceImg"/>
          <!--          <el-input v-model="form.goodsFaceImg" placeholder="请输入商品封面" />-->
        </el-form-item>
        <el-form-item label="商品图片" prop="goodsImg">
          <upload-image-multiple :value="form.goodsImg" @input="inputGoodsImg"/>
          <!--          <el-input v-model="form.goodsImg" placeholder="请输入商品图片"/>-->
        </el-form-item>
        <el-form-item label="状态" prop="state">

          <el-radio-group v-model="form.state">
            <el-radio v-for="(dict, index) in stateOptions" :key="index" :label="parseInt(dict.dictValue)">{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="商品类型" prop="goodsType">
          <el-select v-model="form.goodsType" placeholder="请选择商品类型">
            <el-option label="请选择字典生成" value=""/>
          </el-select>
        </el-form-item>
        <el-form-item label="关联规格" prop="goodsSpec">
          <!--          <el-input v-model="form.goodsSpec" type="textarea" placeholder="请输入内容"/>-->
          <el-transfer v-model="selectedSpecData" :data="specData" filterable
                       :titles="['商品列表','已关联商品']"
                       :button-texts="['取消关联', '关联商品']"
                       @change="handleChangeSpec"
          ></el-transfer>
        </el-form-item>
        <el-form-item label="商品说明" prop="goodsDesc">
          <!--          <el-input v-model="form.goodsDesc" placeholder="请输入商品说明"/>-->
          <editor :value="form.goodsDesc" :height="400" :min-height="400" @on-change="onChangeGoodsDesc"/>
        </el-form-item>

        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"/>
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
  listWinery_goods,
  getWinery_goods,
  delWinery_goods,
  addWinery_goods,
  updateWinery_goods,
  exportWinery_goods
} from "@/api/goods/goods_main";
import UploadImage from '@/components/UploadImage/index'
import UploadImageMultiple from '@/components/UploadImageMultiple/index'

import CommonMixin from "@/mixin/common";
import Editor from '@/components/Editor/index';
import {listSpec} from "@/api/goods/goods_spec";
import {getDictName} from "@/utils/utils";

export default {
  name: "GoodsMain",
  mixins: [CommonMixin],
  components: {
    UploadImage,
    UploadImageMultiple,
    Editor
  },
  computed: {},
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
      // 商品信息表格数据
      goodsList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态字典
      stateOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        goodsName: undefined,
        goodsAlias: undefined,
        goodsType: undefined,
        goodsSpec: undefined,
        goodsDesc: undefined,
        goodsFaceImg: undefined,
        goodsImg: undefined,
        state: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},

      // 规格
      specData: [],
      // 已选规格
      selectedSpecData: []

    };
  },
  filters: {
    getStateName(value, options) {
      return getDictName(value, options)
    }
  },
  created() {
    this.getList();
    this.getDicts("sys_release_status").then(response => {
      this.stateOptions = response.data;
    });
  },
  methods: {
    inputGoodsFaceImg(fileName) {
      this.form.goodsFaceImg = fileName
    },

    inputGoodsImg(fileName) {
      this.form.goodsImg = fileName
    },
    // 状态字典翻译
    stateFormat(row, column) {
      return this.selectDictLabel(this.stateOptions, row.state);
    },
    /** 查询商品信息列表 */
    getList() {
      this.loading = true;
      listWinery_goods(this.queryParams).then(response => {
        this.goodsList = response.rows;
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
        goodsName: undefined,
        goodsAlias: undefined,
        goodsType: undefined,
        goodsSpec: undefined,
        goodsDesc: undefined,
        goodsFaceImg: undefined,
        goodsImg: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加商品信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getWinery_goods(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改商品信息";

        listSpec({
          pageNum: 1,
          pageSize: 999
        }).then(r => {
          this.specData = []
          for (let i = 0; i < r.rows.length; i++) {
            this.specData.push({
              key: r.rows[i].id,
              label: r.rows[i].specName,
              // disabled: i % 4 === 0
            });
          }

          this.selectedSpecData = this.form.goodsSpec.split(',')
        })
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            this.form.goodsSpec = this.selectedSpecData.join(',')
            updateWinery_goods(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWinery_goods(this.form).then(response => {
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
      this.$confirm('是否确认删除商品信息编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delWinery_goods(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有商品信息数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportWinery_goods(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    },
    /**
     * 商品详情
     * @param value
     */
    onChangeGoodsDesc(value) {
      this.form.goodsDesc = value.html
    },


    /**
     * 规格关联穿梭框
     */
    handleChangeSpec(value, direction, movedKeys) {
      console.log(value, direction, movedKeys);
    }
  }
};
</script>
