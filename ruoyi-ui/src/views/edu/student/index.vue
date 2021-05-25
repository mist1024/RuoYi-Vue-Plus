<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="身份证号" prop="idCardNo">
        <el-input
          v-model="queryParams.idCardNo"
          placeholder="请输入身份证号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="学生姓名" prop="studentName">
        <el-input
          v-model="queryParams.studentName"
          placeholder="请输入学生姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="年级" prop="currentGrade">
        <el-select v-model="queryParams.currentGrade" placeholder="请选择年级" clearable size="small">
          <el-option
            v-for="dict in currentGradeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="班级" prop="currentClass">
        <el-select v-model="queryParams.currentClass" placeholder="请选择班级" clearable size="small">
          <el-option
            v-for="dict in currentClassOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="学校" prop="currentSchool">
        <el-select v-model="queryParams.currentSchool" placeholder="请选择学校" clearable size="small">
          <el-option
            v-for="dict in currentSchoolOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="手机号" prop="mobile">
        <el-input
          v-model="queryParams.mobile"
          placeholder="请输入手机号"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="父亲手机" prop="fatherMobile">
        <el-input
          v-model="queryParams.fatherMobile"
          placeholder="请输入父亲手机"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="母亲手机" prop="motherMobile">
        <el-input
          v-model="queryParams.motherMobile"
          placeholder="请输入母亲手机"
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
          v-hasPermi="['edu:student:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['edu:student:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['edu:student:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['edu:student:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="studentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="母亲手机" align="center" prop="id" v-if="false"/>
      <el-table-column label="身份证号" align="center" prop="idCardNo" />
      <el-table-column label="学生姓名" align="center" prop="studentName" />
      <el-table-column label="年级" align="center" prop="currentGrade" :formatter="currentGradeFormat" />
      <el-table-column label="班级" align="center" prop="currentClass" :formatter="currentClassFormat" />
      <el-table-column label="学校" align="center" prop="currentSchool" :formatter="currentSchoolFormat" />
      <el-table-column label="手机号" align="center" prop="mobile" />
      <el-table-column label="父亲手机" align="center" prop="fatherMobile" />
      <el-table-column label="母亲手机" align="center" prop="motherMobile" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['edu:student:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['edu:student:remove']"
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

    <!-- 添加或修改学生信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="身份证号" prop="idCardNo">
          <el-input v-model="form.idCardNo" placeholder="请输入身份证号" />
        </el-form-item>
        <el-form-item label="学生姓名" prop="studentName">
          <el-input v-model="form.studentName" placeholder="请输入学生姓名" />
        </el-form-item>
        <el-form-item label="年级" prop="currentGrade">
          <el-select v-model="form.currentGrade" placeholder="请选择年级">
            <el-option
              v-for="dict in currentGradeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班级" prop="currentClass">
          <el-select v-model="form.currentClass" placeholder="请选择班级">
            <el-option
              v-for="dict in currentClassOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学校" prop="currentSchool">
          <el-select v-model="form.currentSchool" placeholder="请选择学校">
            <el-option
              v-for="dict in currentSchoolOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="手机号" prop="mobile">
          <el-input v-model="form.mobile" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="父亲手机" prop="fatherMobile">
          <el-input v-model="form.fatherMobile" placeholder="请输入父亲手机" />
        </el-form-item>
        <el-form-item label="母亲手机" prop="motherMobile">
          <el-input v-model="form.motherMobile" placeholder="请输入母亲手机" />
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
import { listStudent, getStudent, delStudent, addStudent, updateStudent, exportStudent } from "@/api/edu/student";

export default {
  name: "Student",
  components: {
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
      // 学生信息表格数据
      studentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 年级字典
      currentGradeOptions: [],
      // 班级字典
      currentClassOptions: [],
      // 学校字典
      currentSchoolOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        idCardNo: undefined,
        studentName: undefined,
        currentGrade: undefined,
        currentClass: undefined,
        currentSchool: undefined,
        mobile: undefined,
        fatherMobile: undefined,
        motherMobile: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        idCardNo: [
          { required: true, message: "身份证号不能为空", trigger: "blur" }
        ],
        studentName: [
          { required: true, message: "学生姓名不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("edu_grade").then(response => {
      this.currentGradeOptions = response.data;
    });
    this.getDicts("edu_class").then(response => {
      this.currentClassOptions = response.data;
    });
    this.getDicts("edu_school").then(response => {
      this.currentSchoolOptions = response.data;
    });
  },
  methods: {
    /** 查询学生信息列表 */
    getList() {
      this.loading = true;
      listStudent(this.queryParams).then(response => {
        this.studentList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 年级字典翻译
    currentGradeFormat(row, column) {
      return this.selectDictLabel(this.currentGradeOptions, row.currentGrade);
    },
    // 班级字典翻译
    currentClassFormat(row, column) {
      return this.selectDictLabel(this.currentClassOptions, row.currentClass);
    },
    // 学校字典翻译
    currentSchoolFormat(row, column) {
      return this.selectDictLabel(this.currentSchoolOptions, row.currentSchool);
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
        idCardNo: undefined,
        studentName: undefined,
        currentGrade: undefined,
        currentClass: undefined,
        currentSchool: undefined,
        mobile: undefined,
        fatherMobile: undefined,
        motherMobile: undefined,
        createTime: undefined,
        createBy: undefined,
        updateTime: undefined,
        updateBy: undefined
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
      this.title = "添加学生信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getStudent(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改学生信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStudent(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStudent(this.form).then(response => {
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
      this.$confirm('是否确认删除学生信息编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delStudent(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有学生信息数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return exportStudent(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
