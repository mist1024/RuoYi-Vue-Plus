<template>
  <div class="app-container">
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
      <el-form-item label="结算标志" prop="settlementFlag">
        <el-select v-model="queryParams.settlementFlag" @change="selectSettlementFlag" placeholder="请选择">
          <el-option
            v-for="item in settlementFlagOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="预付费时间" prop="prepaidAt">
        <el-date-picker clearable size="small" style="width: 200px"
                        v-model="queryParams.prepaidAt"
                        type="date"
                        value-format="yyyy-MM-dd"
                        placeholder="选择预付费时间">
        </el-date-picker>
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
          v-hasPermi="['fantang:prepayment:add']"
        >预付收款
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['fantang:prepayment:edit']"
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
          v-hasPermi="['fantang:prepayment:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['fantang:prepayment:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="prepaymentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="预付费id" align="center" prop="prepaymentId" v-if="false"/>
      <el-table-column label="住院号" align="center" prop="hospitalId" width="180">
      </el-table-column>
      <el-table-column label="姓名" align="center" prop="name"/>
      <el-table-column label="科室" align="center" prop="departName" width="180">
      </el-table-column>
      <el-table-column label="床号" align="center" prop="bedId"/>
      <el-table-column label="结算" align="center" prop="settlementFlag" :formatter="formatSettlementFlag"/>
      <el-table-column label="结算时间" align="center" prop="settlementAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.settlementAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['fantang:prepayment:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['fantang:prepayment:remove']"
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

    <!-- 添加或修改收费管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="formAddPrepayment" :model="formAddPrepayment" :rules="rules" label-width="80px">
        <el-form-item label="住院号" prop="hospitalId">
          <el-autocomplete
            popper-class="my-autocomplete"
            v-model="state"
            :fetch-suggestions="querySearch"
            placeholder="请输入住院号"
            @select="handleSelect">
            <i
              class="el-icon-edit el-input__icon"
              slot="suffix"
              @click="handleIconClick">
            </i>
            <template slot-scope="{ item }">
              <div class="name">{{ item.name }}</div>
              <span class="addr">
                {{ item.departName }}
                <el-divider direction="vertical"></el-divider>
                {{ item.bedId}}
              </span>
            </template>
          </el-autocomplete>
        </el-form-item>
        <el-form-item label="病人id" prop="patientId">
          <el-input v-model="formAddPrepayment.patientId" placeholder="请输入病人id"/>
        </el-form-item>
        <el-form-item label="预付费金额" prop="prepaid">
          <el-input v-model="formAddPrepayment.prepaid" placeholder="请输入预付费金额"/>
        </el-form-item>
        <el-form-item label="预付费时间" prop="prepaidAt">
          <el-date-picker clearable size="small" style="width: 200px"
                          v-model="formAddPrepayment.prepaidAt"
                          type="date"
                          value-format="yyyy-MM-dd"
                          placeholder="选择预付费时间">
          </el-date-picker>
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
    addPrepayment,
    delPrepayment,
    exportPrepayment,
    getPrepayment,
    listPrepayment,
    listNoPrepayment,
    updatePrepayment
  } from "@/api/fantang/prepayment";
  import {listAllPrepay, listPrepay} from "../../../api/fantang/prepayment";

  export default {
    name: "Prepayment",
    components: {},
    data() {
      return {
        settlementFlagOptions: [{
          value: null,
          label: '未交费'
        }, {
          value: 0,
          label: '未结算'
        }, {
          value: 1,
          label: '已结算'
        }, ],
        suggestionList: [],
        NoPrepayments: [],
        state: '',
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
        // 收费管理表格数据
        prepaymentList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          collectAt: null,
          settlementAt: null,
          settlementFlag: null,
          prepaid: null,
          prepaidAt: null,
          hospitalId: null,
          name: null,
        },
        // 表单参数
        formAddPrepayment: {},
        // 表单校验
        rules: {
          patientId: [
            {required: true, message: "病人id不能为空", trigger: "blur"}
          ],
          prepaid: [
            {required: true, message: "预付费金额不能为空", trigger: "blur"}
          ],
          prepaidAt: [
            {required: true, message: "预付费时间不能为空", trigger: "blur"}
          ]
        }
      };
    },
    created() {
      this.getDefaultNoPrepayment();
    },
    mounted() {
    },

    methods: {
      // 处理筛选结算标志
      selectSettlementFlag(value) {
        console.log("value", value)
        if (value === null) {
          this.getDefaultNoPrepayment();
        } else if (value === 0) {
          this.loading = true;
          listPrepay().then(response => {
            this.prepaymentList = response.rows;
            this.loading = false;});
        } else {
          this.loading = true;
          listAllPrepay().then(response => {
            this.prepaymentList = response.rows;
            this.loading = false;
          });
        }
      },

      // 格式化结算标志回显
      formatSettlementFlag(row) {
        if (row.settlementFlag === null)
          return "未交费";
        if (row.settlementFlag === 1)
          return "已结算";
        return "未结算";
      },
      // 响应自动查询回显
      querySearch(queryString, cb) {
        var restaurants = this.suggestionList;
        console.log("restaurants-->", restaurants);
        console.log("queryString", queryString)
        console.log("cb-->", cb)
        var results = queryString ? restaurants.filter(this.createFilter(queryString)) : restaurants;
        // 调用 callback 返回建议列表的数据
        cb(results);
      },

      // 处理过滤器
      createFilter(queryString) {
        return (restaurant) => {
          return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
      },

      // 填充所有待缴预付伙食费的病人清单
      getDefaultNoPrepayment() {
        this.loading = true;
        listNoPrepayment().then(response => {
          this.NoPrepayments = response.rows;
          this.prepaymentList = response.rows;
          this.suggestionList = this.NoPrepayments.map(item => {
            return {
              "value": item.hospitalId,
              "departName": item.departName,
              "name": item.name,
              "bedId": item.bedId,
            }
          });
          this.loading = false;
          console.log("this.prepaymentList",this.prepaymentList);
          return response.rows;
        });
      },

      // 处理自动查询列表选择的事件
      handleSelect(item) {
        console.log(item);
      },

      // 处理点击查询图标的事件
      handleIconClick(ev) {
        console.log(ev);
      },
      /** 查询收费管理列表 */
      getList() {
        this.loading = true;
        listPrepayment(this.queryParams).then(response => {
          this.prepaymentList = response.rows;
          this.total = response.total;
          this.loadAll();
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
        this.formAddPrepayment = {
          prepaymentId: null,
          patientId: null,
          collectAt: null,
          collectBy: null,
          settlementAt: null,
          settlementBy: null,
          settlementId: null,
          settlementFlag: null,
          prepaid: null,
          prepaidAt: null
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
        this.ids = selection.map(item => item.prepaymentId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加收费管理";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const prepaymentId = row.prepaymentId || this.ids
        getPrepayment(prepaymentId).then(response => {
          this.formAddPrepayment = response.data;
          this.open = true;
          this.title = "修改收费管理";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.formAddPrepayment.prepaymentId != null) {
              updatePrepayment(this.formAddPrepayment).then(response => {
                this.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addPrepayment(this.formAddPrepayment).then(response => {
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
        const prepaymentIds = row.prepaymentId || this.ids;
        this.$confirm('是否确认删除收费管理编号为"' + prepaymentIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return delPrepayment(prepaymentIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        const queryParams = this.queryParams;
        this.$confirm('是否确认导出所有收费管理数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function () {
          return exportPrepayment(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
      }
    }
  };
</script>

<style lang="scss">
  .my-autocomplete {
    li {
      line-height: normal;
      padding: 7px;

      .name {
        text-overflow: ellipsis;
        overflow: hidden;
      }

      .addr {
        font-size: 12px;
        color: #b4b4b4;
      }

      .highlighted .addr {
        color: #ddd;
      }
    }
  }
</style>
