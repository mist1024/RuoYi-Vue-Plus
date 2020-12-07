<template>
  <div class="app-container">
    <!--    搜索栏-->
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="住院号" prop="hospitalId">
        <el-input
          v-model="queryParams.hospitalId"
          placeholder="请输入住院号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="科室" prop="departId">
        <el-select v-model="queryParams.departId"
                   size="small"
                   @keyup.enter.native="handleQuery"
                   placeholder="请选择科室">
          <el-option
            v-for="item in departOptions"
            :key="item.departName"
            :label="item.departName"
            :value="item.departId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="床号" prop="bedId">
        <el-input
          v-model="queryParams.bedId"
          placeholder="请输入床号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否启用" prop="flag">
        <el-select v-model="queryParams.flag"
                   size="small"
                   @keyup.enter.native="handleQuery"
                   placeholder="请选是否启用">
          <el-option
            v-for="item in flagOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="cyan" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!--    功能按钮栏-->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['fantang:catering:add']"
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
          v-hasPermi="['fantang:catering:edit']"
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
          v-hasPermi="['fantang:catering:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click=""
          v-hasPermi="['fantang:catering:remove']"
        >作废
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click=""
          v-hasPermi="['fantang:catering:remove']"
        >每日简报
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click=""
          v-hasPermi="['fantang:catering:remove']"
        >记录表
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click=""
          v-hasPermi="['fantang:catering:remove']"
        >拷贝并新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click=""
          v-hasPermi="['fantang:catering:remove']"
        >拷贝
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click=""
          v-hasPermi="['fantang:catering:remove']"
        >粘贴
        </el-button>
      </el-col>
      <el-col :span="1.5" v-if="false">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['fantang:catering:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!--    数据列表栏-->
    <el-table v-loading="loading" :data="cateringList" @selection-change="handleSelectionChange" broder>
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="id" align="center" prop="id" v-if="false"/>
      <el-table-column label="科室" align="center" prop="departName"/>
      <el-table-column label="姓名" align="center" prop="name"/>
      <el-table-column label="床号" align="center" prop="bedId"/>
      <el-table-column label="正餐类型" align="center" prop="type" :formatter="typeFormat" v-if="false"/>
      <el-table-column label="配餐号" align="center" prop="number"/>
      <el-table-column label="配餐频次" align="center" prop="frequency"/>
      <el-table-column label="用法" align="center" prop="usage"/>
      <el-table-column label="描述" align="center" prop="describe"/>
      <el-table-column label="启用标示" align="center" prop="flag"/>
      <el-table-column label="创建时间" align="center" prop="createAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新人" align="center" prop="updateBy"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['fantang:catering:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['fantang:catering:remove']"
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

    <!-- 添加或修改配餐功能对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="科室" prop="departId">
          <el-select v-model="form.departId"
                     placeholder="请选择科室"
                     @change="changeDepart">
            <el-option
              v-for="item in departOptions"
              :key="item.departName"
              :label="item.departName"
              :value="item.departId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="病人" prop="patientId">
          <el-select v-model="form.patientId" placeholder="请选择病人" @change="changePatient">
            <el-option
              v-for="item in patientOptions"
              :key="item.name"
              :label="item.name"
              :value="item.patientId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="床号" prop="bedId">
          <el-input v-model="form.bedId" placeholder="床号" :disabled="true"/>
        </el-form-item>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="配餐号" prop="number">
              <el-input v-model="form.number" placeholder="请输入配餐号"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用法" prop="usage">
              <el-select v-model="form.usage" placeholder="请选择用法">
                <el-option
                  v-for="item in usageOptions"
                  :key="item.label"
                  :label="item.label"
                  :value="item.label"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="正餐类型" prop="type">
              <el-select v-model="form.type"
                         multiple
                         @change="changeDinnerType"
                         placeholder="请选择正餐类型">
                <el-option
                  v-for="dict in typeOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="parseInt(dict.dictValue)"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="配餐频次" prop="frequency">
              <el-select v-model="form.frequency" placeholder="请选择频次">
                <el-option
                  v-for="item in frequencyOptions"
                  :key="item.label"
                  :label="item.label"
                  :value="item.label"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
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
  addCatering,
  delCatering,
  exportCatering,
  getCatering,
  listCatering,
  updateCatering
} from "@/api/fantang/catering";
import {listDepart} from "@/api/fantang/depart";
import {getBedIdById, selectPatientByDepartId} from "@/api/fantang/patient";

export default {
  name: "Catering",
  components: {},
  data() {
    return {
      // 频次列表
      frequencyOptions:[{
        value: 'qd',
        label: 'qd'
      }, {
        value: 'bid',
        label: 'bid'
      }, {
        value: 'tid',
        label: 'tid'
      }, {
        value: 'qn',
        label: 'qn'
      }
      ],
      // 用法列表
      usageOptions: [{
        value: 1,
        label: '鼻饲'
      }, {
        value: 2,
        label: '口服'
      }],
      // 作废标志
      flagOptions: [{
        value: 1,
        label: '是'
      }, {
        value: 0,
        label: '否'
      }],
      // 科室列表
      departOptions: [],
      // 病人列表
      patientOptions: [],
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
      // 配餐功能表格数据
      cateringList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 正餐类型字典
      typeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        flag: null,
        hospitalId: null,
        name: null,
        departId: null,
        bedId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
    this.getDicts("ft_book_type").then(response => {
      this.typeOptions = response.data;
    });
    listDepart().then(response => {
      this.departOptions = response.rows;
    })
  },
  methods: {
    // 响应营养配餐用餐安排多选列表
    changeDinnerType(value) {
      console.log(value)
      if (value.length === 1)
        this.form.frequency = 'qd';
      else if (value.length === 2)
        this.form.frequency = 'bid';
      else if (value.length === 3)
        this.form.frequency = 'tid';
      else
        this.form.frequency = 'qn';
    },
    // 响应科室信息切换
    changeDepart(value) {
      selectPatientByDepartId(value).then(response => {
        this.patientOptions = response.data;
      });
    },
    // 相应病人信息切换
    changePatient(value) {
      const _this = this;
      getBedIdById(value).then(response => {
        console.log("aaaaaaa", response);
        _this.form.bedId = response.msg;
        console.log(_this.form.bedId);
      })
    },
    /** 查询配餐功能列表 */
    getList() {
      this.loading = true;
      listCatering(this.queryParams).then(response => {
        this.cateringList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 正餐类型字典翻译
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
        id: null,
        patientId: null,
        type: null,
        number: null,
        frequency: null,
        usage: null,
        isReplace: null,
        flag: null,
        updateAt: null,
        updateBy: null,
        createAt: null,
        createBy: null,
        describe: null
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
      this.title = "添加配餐功能";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCatering(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改配餐功能";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCatering(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCatering(this.form).then(response => {
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
      this.$confirm('是否确认删除配餐功能编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delCatering(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有配餐功能数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportCatering(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
