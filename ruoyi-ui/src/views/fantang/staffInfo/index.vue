<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="岗位" prop="post">
        <el-select v-model="queryParams.post" placeholder="请选择岗位" clearable size="small">
          <el-option
            v-for="dict in postOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          ></el-option>
        </el-select>
      </el-form-item>
      <!--      <el-form-item label="角色" prop="role">-->
      <!--        <el-select v-model="queryParams.role" placeholder="请选择角色" clearable size="small">-->
      <!--          <el-option label="请选择字典生成" value=""/>-->
      <!--        </el-select>-->
      <!--      </el-form-item>-->
      <el-form-item label="是否启用" prop="flag">
        <el-select v-model="queryParams.flag" placeholder="请选择是否启用" clearable size="small">
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['fantang:staffInfo:add']"
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
          v-hasPermi="['fantang:staffInfo:edit']"
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
          v-hasPermi="['fantang:staffInfo:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['fantang:staffInfo:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="staffInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="员工 id" align="center" prop="staffId" v-if="false"/>
      <el-table-column label="姓名" align="center" prop="name"/>
      <el-table-column label="性别" align="center" prop="sex"/>
      <el-table-column label="手机号码" align="center" prop="tel"/>
      <el-table-column label="岗位" align="center" prop="post"/>
      <el-table-column label="补贴余额" align="center" prop="balance"/>
      <el-table-column label="创建日期" align="center" prop="createAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="是否启用" align="center" prop="flag" :formatter="formatFlag"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['fantang:staffInfo:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['fantang:staffInfo:remove']"
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

    <!-- 添加或修改员工管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名"/>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="form.sex" placeholder="请选性别">
            <el-option
              v-for="dict in sexOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="手机号码" prop="tel">
          <el-input v-model="form.tel" placeholder="请输入手机号码"/>
        </el-form-item>
        <el-form-item label="岗位" prop="post">
          <el-select v-model="form.post" placeholder="请选择岗位">
            <el-option
              v-for="dict in postOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="科室" prop="deptList">
          <el-select v-model="form.deptList" placeholder="请选择科室">
            <el-option
              v-for="item in deptListOptions"
              :key="item.departName"
              :label="item.departName"
              :value="item.departId">
            </el-option>
          </el-select>
        </el-form-item>
        <!--        <el-form-item label="角色" prop="role">-->
        <!--          <el-select v-model="form.role" placeholder="请选择角色">-->
        <!--            <el-option label="请选择字典生成" value=""/>-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" placeholder="请输入密码"/>
        </el-form-item>
        <el-form-item label="启用标志" prop="flag">
          <el-switch
            v-model="form.flag"
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-text="启用"
            inactive-text="禁用">
          </el-switch>
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
  addStaffInfo,
  delStaffInfo,
  exportStaffInfo,
  getStaffInfo,
  listStaffInfo,
  updateStaffInfo
} from "@/api/fantang/staffInfo";
import {listDepart} from "@/api/fantang/depart";

export default {
  name: "StaffInfo",
  components: {},
  data() {
    return {
      deptListOptions: [],
      sexOptions: [],
      postOptions: [],
      flagOptions: [{
        value: 1,
        label: '启用'
      }, {
        value: 0,
        label: '禁用'
      }],
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
      // 员工管理表格数据
      staffInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        post: null,
        role: null,
        flag: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "姓名不能为空", trigger: "blur"}
        ],
        post: [
          {required: true, message: "岗位不能为空", trigger: "change"}
        ],
        role: [
          {required: true, message: "角色不能为空", trigger: "change"}
        ],
        flag: [
          {required: true, message: "启用标志不能为空", trigger: "change"}
        ],
        sex: [
          {required: true, message: "性别不能为空", trigger: "change"}
        ],
        deptList: [
          {required: true, message: "性别不能为空", trigger: "change"}
        ],
        tel: [
          {required: true, message: "手机号码不能为空", trigger: "change"}
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_user_sex").then(response => {
      this.sexOptions = response.data;
    });
    this.getDicts("ft_post").then(response => {
      this.postOptions = response.data;
    });
  },
  methods: {
    // 控制员工列表启用状态的回显
    formatFlag(row) {
      if (row.flag)
        return '启用';
      else
        return '禁用';
    },

    /** 查询员工管理列表 */
    getList() {
      this.loading = true;
      listStaffInfo(this.queryParams).then(response => {
        this.staffInfoList = response.rows;
        this.total = response.total;
        this.loading = false;
        console.log("flag-----", response);
      });
      listDepart(this.queryParams).then(response => {
        this.deptListOptions = response.rows;
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        staffId: null,
        departId: null,
        name: null,
        post: null,
        role: null,
        password: null,
        createAt: null,
        createBy: null,
        flag: true,
        balance: null,
        staffType: null,
        corpName: null,
        pictureUrl: null,
        deptList: null,
        qrCode: null,
        sex: null,
        tel: null,
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
      this.ids = selection.map(item => item.staffId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加员工管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const staffId = row.staffId || this.ids
      getStaffInfo(staffId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改员工管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.staffId != null) {
            if (this.form.flag === true) {
              this.form.flag = 1;
            } else {
              this.form.flag = 0;
            }
            updateStaffInfo(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            console.log(this.form);
            if (this.form.flag === true) {
              this.form.flag = 1;
            } else {
              this.form.flag = 0;
            }
            addStaffInfo(this.form).then(response => {
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
      const staffIds = row.staffId || this.ids;
      this.$confirm('是否确认删除员工管理编号为"' + staffIds + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delStaffInfo(staffIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有员工管理数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportStaffInfo(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
