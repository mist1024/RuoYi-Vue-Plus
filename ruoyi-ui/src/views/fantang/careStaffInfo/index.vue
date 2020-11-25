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
      <!--      <el-form-item label="员工类别" prop="staffType">-->
      <!--        <el-select v-model="queryParams.staffType" placeholder="请选择员工类别" clearable size="small">-->
      <!--          <el-option label="请选择字典生成" value="" />-->
      <!--        </el-select>-->
      <!--      </el-form-item>-->
      <el-form-item label="所属公司" prop="corpName">
        <el-input
          v-model="queryParams.corpName"
          placeholder="请输入所属公司"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="报餐科室" prop="deptList">
        <el-select v-model="queryParams.deptList" placeholder="请选择报餐科室" clearable size="small">
          <el-option
            v-for="item in deptListOptions"
            :key="item.departName"
            :label="item.departName"
            :value="item.departName">
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
      <el-table-column label="所属公司" align="center" prop="corpName"/>
      <!--      <el-table-column label="员工类别" align="center" prop="staffType"/>-->
      <el-table-column label="报餐科室" align="center" prop="deptList"/>
      <el-table-column label="补贴余额" align="center" prop="balance"/>
      <el-table-column label="创建日期" align="center" prop="createAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="二维码" align="center" prop="qrCode"/>
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
        <!--        <el-form-item label="员工类别" prop="staffType">-->
        <!--          <el-select v-model="form.staffType" placeholder="请选择员工类别">-->
        <!--            <el-option label="请选择字典生成" value="" />-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
        <el-form-item label="所属公司" prop="corpName">
          <el-input v-model="form.corpName" placeholder="请输入所属公司"/>
        </el-form-item>
        <el-form-item label="照片">
          <uploadImage v-model="form.pictureUrl"/>
        </el-form-item>
        <el-form-item label="报餐科室" prop="deptList">
          <el-select v-model="form.deptList" placeholder="请选择报餐科室">
            <el-option
              v-for="item in deptListOptions"
              :key="item.departName"
              :label="item.departName"
              :value="item.departId">
            </el-option>
          </el-select>
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
  careStaffList,
  delStaffInfo,
  exportStaffInfo,
  getStaffInfo,
  updateStaffInfo
} from "@/api/fantang/staffInfo";
import {listDepart} from "@/api/fantang/depart";
import UploadImage from '@/components/UploadImage';

export default {
  name: "StaffInfo",
  components: {
    UploadImage,
  },
  data() {
    return {
      sexOptions: [],
      deptListOptions: [],
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
        staffType: null,
        corpName: null,
        deptList: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "姓名不能为空", trigger: "blur"}
        ],
        corpName: [
          {required: true, message: "所属公司不能为空", trigger: "blur"}
        ],
        sex: [
          {required: true, message: "性别不能为空", trigger: "blur"}
        ],
        deptList: [
          {required: true, message: "报餐科室不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_user_sex").then(response => {
      this.sexOptions = response.data;
    });
  },
  methods: {
    /** 查询员工管理列表 */
    getList() {
      this.loading = true;
      careStaffList(this.queryParams).then(response => {
        this.staffInfoList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      listDepart(this.queryParams).then(response => {
        // console.log("depart", response);
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
        flag: null,
        balance: null,
        staffType: null,
        corpName: null,
        pictureUrl: null,
        deptList: null,
        qrCode: null
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
            updateStaffInfo(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
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
