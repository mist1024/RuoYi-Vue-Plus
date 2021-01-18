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
          @click="handleCancel"
          v-hasPermi="['fantang:catering:remove']"
        >暂停
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
          @click="handleCopyAndAdd"
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
    <el-table v-loading="loading"
              :data="cateringList"
              border
              :span-method="objectSpanMethod"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="id" align="center" prop="id" v-if="false"/>
      <el-table-column label="暂停" align="center" prop="suspendFlag" width="80px">
        <template slot-scope="scope">
          <el-checkbox v-model="scope.row.suspendFlag"  @change="changeSuspend(scope.row, $event)">暂停</el-checkbox>
        </template>
      </el-table-column>
      <el-table-column label="科室" align="center" prop="departName"/>
      <el-table-column label="姓名" align="center" prop="name"/>
      <el-table-column label="床号" align="center" prop="bedId"/>
      <el-table-column label="配餐频次" align="center" prop="frequency"/>
      <el-table-column label="正餐类型" align="center" prop="type" :formatter="typeFormat"/>
      <el-table-column label="营养配餐" align="center" prop="foodName"/>
      <el-table-column label="用法" align="center" prop="cateringUsage" :formatter="cateringUsageFormat"/>
      <!--      <el-table-column label="描述" align="center" prop="cateringDescribe"/>-->
      <el-table-column label="启用标志" align="center" prop="flag" :formatter="formatFlag"/>
      <el-table-column label="创建时间" align="center" prop="createAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <!--      <el-table-column label="更新人" align="center" prop="updateBy"/>-->
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
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      :page-sizes="[12, 24]"
      @pagination="getList"
    />

    <!-- 添加配餐功能对话框 -->
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
              :key="item.patientId"
              :label="item.name"
              :value="item.patientId"
            >
              <span style="float: left">{{ item.name }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px; margin-right:16px">{{ item.bedId }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="床号" prop="bedId">
          <el-input v-model="form.bedId" placeholder="床号" :disabled="true"/>
        </el-form-item>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="配餐号" prop="number">
              <el-select v-model="form.number" placeholder="请选择配餐号">
                <el-option
                  v-for="item in numberOptions"
                  :key="item.name"
                  :label="item.name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用法" prop="cateringUsage">
              <el-select v-model="form.cateringUsage" placeholder="请选择用法">
                <el-option
                  v-for="item in cateringUsageOptions"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="正餐类型" prop="types">
              <el-select v-model="form.types"
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
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
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

    <!-- 修改配餐功能对话框 -->
    <el-dialog :title="title" :visible.sync="modifyItem" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="科室" prop="departId">
          <el-select v-model="form.departId"
                     placeholder="请选择科室"
                     @change="changeDepart"
                     :disabled="true">
            <el-option
              v-for="item in departOptions"
              :key="item.departName"
              :label="item.departName"
              :value="item.departId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="病人" prop="patientId">
          <el-input v-model="form.name" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="床号" prop="bedId">
          <el-input v-model="form.bedId" placeholder="床号" :disabled="true"/>
        </el-form-item>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="配餐号" prop="number">
              <el-select v-model="form.number" placeholder="请选择配餐号">
                <el-option
                  v-for="item in numberOptions"
                  :key="item.name"
                  :label="item.name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用法" prop="cateringUsage">
              <el-select v-model="form.cateringUsage" placeholder="请选择用法">
                <el-option
                  v-for="item in cateringUsageOptions"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="正餐类型" prop="type">
              <el-select v-model="form.type"
                         placeholder="请选择正餐类型"
                         :disabled="true">
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
              <!--              <el-select v-model="form.frequency" placeholder="频次" :disabled="true">-->
              <!--                <el-option-->
              <!--                  v-for="item in frequencyOptions"-->
              <!--                  :key="item.label"-->
              <!--                  :label="item.label"-->
              <!--                  :value="item.label"-->
              <!--                ></el-option>-->
              <!--              </el-select>-->
              <el-input v-model="form.frequency" placeholder="频次" :disabled="true"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
            <el-form-item label="启用标志">
              <el-switch
                v-model="form.flag"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="启用"
                inactive-text="关闭">
              </el-switch>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 拷贝并新增对话框 -->
    <el-dialog :title="title" :visible.sync="copyItem" width="800px" append-to-body>
      <el-form ref="copyItemForm" :model="copyItemForm" :rules="copyItemFormRules" label-width="80px">
        <el-form-item label="科室" prop="departId">
          <el-select v-model="copyItemForm.departId"
                     placeholder="请选择科室"
                     style="width:450px"
                     @change="changeDepart">
            <el-option
              v-for="item in departOptions"
              :key="item.departName"
              :label="item.departName"
              :value="item.departId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="病人" prop="patientIds" >
          <el-select v-model="copyItemForm.patientIds" placeholder="请选择病人" multiple  style="width:450px">
            <el-option
              v-for="item in patientOptions"
              :key="item.name"
              :label="item.name"
              :value="item.patientId"
            >
              <span style="float: left">{{ item.name }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px; margin-right:16px">{{ item.bedId }}</span>
            </el-option>
          </el-select>
        </el-form-item>

        <el-table :data="copyItemForm.data"
                  border
                  :span-method="objectSpanMethodForCopyAndCreate">
          <el-table-column label="配餐频次" align="center" prop="frequency" width="100px">
            <template slot-scope="scope">
              <el-select v-model="scope.row.frequency">
                <el-option
                  v-for="item in frequencyOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="正餐类型" align="center" prop="type" :formatter="typeFormat" width="120px"/>
          <el-table-column label="营养配餐" align="center" prop="foodName" width="200px">
            <template slot-scope="scope">
              <el-select  v-model="scope.row.number">
                <el-option
                  v-for="item in numberOptions"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="用法" align="center" prop="cateringUsage" :formatter="cateringUsageFormat" width="120px">
            <template slot-scope="scope">
              <el-select v-model="scope.row.cateringUsage" placeholder="请选择用法">
                <el-option
                  v-for="item in cateringUsageOptions"
                  :key="item.label"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="启用标志" align="center" prop="flag" :formatter="formatFlag">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.flag"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="启用"
                inactive-text="关闭">
              </el-switch>
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCopyForm">拷贝新增</el-button>
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
} from "../../../api/fantang/catering";
import {listDepart} from "../../../api/fantang/depart";
import {getBedIdById, selectNoCateringByDepartId} from "../../../api/fantang/patient";
import {listNutritionFood} from "../../../api/fantang/nutritionFood";
import {cancelCatering, copyAndAdd, getByPatient, getAllByPatient} from "../../../api/fantang/catering";

export default {
  name: "Catering",
  components: {},
  data() {
    return {
      frequencyOptions: [{
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
      }],
      // 配餐列表
      numberOptions: [],
      // 用法列表
      cateringUsageOptions: [{
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
      names: [],
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
      // 修改弹出层
      modifyItem: false,
      // 拷贝并新增弹出层
      copyItem: false,
      // 正餐类型字典
      typeOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 12,
        flag: null,
        hospitalId: null,
        name: null,
        departId: null,
        bedId: null,
      },
      // 表单参数
      form: {},
      // 拷贝并新增表单
      copyItemForm: {},
      // 拷贝并新增表单校验
      copyItemFormRules: {
        patientIds: [
          {required: true, message: "病人不能为空", trigger: "blur"}
        ],
      },
      // 表单校验
      rules: {
        departId: [
          {required: true, message: "部门不能为空", trigger: "blur"}
        ],
        patientId: [
          {required: true, message: "姓名不能为空", trigger: "blur"}
        ],
        bedId: [
          {required: true, message: "床号不能为空", trigger: "blur"}
        ],
        types: [
          {required: true, message: "正餐类型不能为空", trigger: "blur"}
        ],
        cateringUsage: [
          {required: true, message: "用法不能为空", trigger: "blur"}
        ],
        frequency: [
          {required: true, message: "频次不能为空", trigger: "blur"}
        ],
        number: [
          {required: true, message: "配餐号不能为空", trigger: "blur"}
        ],
      }
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
    listNutritionFood().then(response => {
      console.log("yyc----", response)
      this.numberOptions = response.rows;
    })
  },
  methods: {
    // 格式化数据列“用法”显示文本
    cateringUsageFormat(row) {
      if (row.cateringUsage === 1 || row.cateringUsage === '1') {
        return "鼻饲";
      } else if (row.cateringUsage === 2 || row.cateringUsage === '2') {
        return "口服";
      } else {
        return " ";
      }
    },

    // 格式化数据列“启用”显示文本
    formatFlag(row) {
      if (row.flag === true) {
        return "启用";
      } else if (row.flag === false) {
        return "禁用";
      }
    },
    // 控制数据合并列
    objectSpanMethod({rowIndex, columnIndex}) {
      if (columnIndex === 1) {
        if (rowIndex % 4 === 0) {
          return {
            rowspan: 4,
            colspan: 1
          };
        } else {
          return {
            rowspan: 0,
            colspan: 0
          };
        }
      } else if (columnIndex === 2) {
        if (rowIndex % 4 === 0) {
          return {
            rowspan: 4,
            colspan: 1
          };
        } else {
          return {
            rowspan: 0,
            colspan: 0
          };
        }

      } else if (columnIndex === 3) {
        if (rowIndex % 4 === 0) {
          return {
            rowspan: 4,
            colspan: 1
          };
        } else {
          return {
            rowspan: 0,
            colspan: 0
          };
        }
      } else if (columnIndex === 4) {
        if (rowIndex % 4 === 0) {
          return {
            rowspan: 4,
            colspan: 1
          };
        } else {
          return {
            rowspan: 0,
            colspan: 0
          };
        }
      } else if (columnIndex === 5) {
        if (rowIndex % 4 === 0) {
          return {
            rowspan: 4,
            colspan: 1
          };
        } else {
          return {
            rowspan: 0,
            colspan: 0
          };
        }

      } else if (columnIndex === 0) {
        if (rowIndex % 4 === 0) {
          return {
            rowspan: 4,
            colspan: 1
          };
        } else {
          return {
            rowspan: 0,
            colspan: 0
          };
        }
      }
    },

    // 控制弹出层“拷贝和新增”数据合并列
    objectSpanMethodForCopyAndCreate({rowIndex, columnIndex}) {
      if (columnIndex === 0) {
        if (rowIndex % 4 === 0) {
          return {
            rowspan: 4,
            colspan: 1
          };
        } else {
          return {
            rowspan: 0,
            colspan: 0
          };
        }
      }
    },



    // 响应营养配餐用餐安排多选列表
    changeDinnerType(value) {
      if (value.length === 1) {
        this.form.frequency = 'qd';
        this.copyItemForm.frequency = 'qd';
      } else if (value.length === 2) {
        this.form.frequency = 'bid';
        this.copyItemForm.frequency = 'bid';
      } else if (value.length === 3) {
        this.form.frequency = 'tid';
        this.copyItemForm.frequency = 'tid';
      } else if (value.length === 4) {
        this.form.frequency = 'qn';
        this.copyItemForm.frequency = 'qn';
      }

    },
    // 响应科室信息切换
    changeDepart(value) {
      const _this = this;
      selectNoCateringByDepartId(value).then(response => {
        _this.patientOptions = response.data;
        _this.form.patientId = null;
      });
    },
    // 相应病人信息切换
    changePatient(value) {
      setTimeout(() => {
        getBedIdById(value).then(response => {
          console.log("aaaaaaa", response);
          console.log("value", value);
          this.form.bedId = response.msg;
          console.log('this.form.patientid', this.form.patientId)
        })
      }, 200);

      console.log('this.form.patientid', this.form.patientId)
    },
    /** 查询配餐功能列表 */
    getList() {
      this.loading = true;
      listCatering(this.queryParams).then(response => {
        this.cateringList = response.rows;
        console.log(response);
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
      this.modifyItem = false;
      this.copyItem = false;
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
        cateringUsage: null,
        isReplace: null,
        flag: null,
        updateAt: null,
        updateBy: null,
        createAt: null,
        createBy: null,
        cateringDescribe: null
      };
      this.resetForm("form");

      this.copyItemForm = {
        id: null,
        data: null,
        patientId: null,
        cateringDescribe: null
      };
      this.resetForm("copyItemForm");
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
      this.ids = selection.map(item => item.patientId)
      this.names = selection.map(item => item.name);
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
        console.log(response.data);
        this.modifyItem = true;
        this.title = "修改配餐";
      });
    },
    // 拷贝并新增
    handleCopyAndAdd(row) {
      this.reset();
      const id = row.id || this.ids;
      if (this.ids.length > 1) {
        this.msgError("只能选择一条记录进行拷贝并新增")
      } else {
        this.copyItem = true;
        getAllByPatient(id).then(response => {
          this.copyItemForm.data =  response.data;
          this.copyItemForm.departId =null;
          this.copyItemForm.patientIds = [];
          console.log('cateringList-->', this.copyItemForm);
        })
      }
    },
    /** 拷贝并新增提交按钮 */
    submitCopyForm() {
      this.$refs["copyItemForm"].validate(valid => {
        if (valid) {
          console.log(this.copyItemForm);
          copyAndAdd(this.copyItemForm).then(response => {
            this.msgSuccess("新增成功");
            this.copyItem = false;
            this.getList();
          })
        }
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCatering(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.modifyItem = false;
              this.getList();
            });
          } else {
            console.log(this.form);
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
      const ids = row.patientId || this.ids;
      console.log("ids-----", ids);
      this.$confirm('是否确认删除 “' + this.names + '” 的营养配餐数据?', "警告", {
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

    // 更新指定病患的暂停营养配餐数据
    changeSuspend(row, e) {
      if (e){
        this.$confirm('是否暂停 “' + row.name + '” 的营养配餐数据?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return cancelCatering(row.patientId);
        }).then(() => {
          this.getList();
          this.msgSuccess("已暂停");
        })
      } else {
        this.$confirm('是否恢复 “' + row.name + '” 的营养配餐数据?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return restoreCatering(row.patientId);
        }).then(() => {
          this.getList();
          this.msgSuccess("已恢复");
        })
      }


    },
    // 作废按钮
    handleCancel(row) {
      const ids = row.patientId || this.ids;
      console.log("ids-----", row);
      this.$confirm('是否作废 “' + this.names + '” 的营养配餐数据?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return cancelCatering(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("已作废");
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
