<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学生id" prop="studentId">
        <el-input
          v-model="queryParams.studentId"
          placeholder="请输入学生id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="考试id" prop="examId">
        <el-input
          v-model="queryParams.examId"
          placeholder="请输入考试id"
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
          v-hasPermi="['edu:score:add']"
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
          v-hasPermi="['edu:score:edit']"
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
          v-hasPermi="['edu:score:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['edu:score:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="scoreList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="考试id" align="center" prop="id" v-if="false"/>
      <el-table-column label="语文分数" align="center" prop="chineseScore" />
      <el-table-column label="数学分数" align="center" prop="mathScore" />
      <el-table-column label="英语分数" align="center" prop="englishScore" />
      <el-table-column label="化学分数" align="center" prop="chemistryScore" />
      <el-table-column label="物理分数" align="center" prop="physicsScore" />
      <el-table-column label="生物分数" align="center" prop="biologyScore" />
      <el-table-column label="政治分数" align="center" prop="politicsScore" />
      <el-table-column label="历史分数" align="center" prop="historyScore" />
      <el-table-column label="地理分数" align="center" prop="geographyScore" />
      <el-table-column label="思想品德分数" align="center" prop="ethicScore" />
      <el-table-column label="班级排名" align="center" prop="classRank" />
      <el-table-column label="级部排名" align="center" prop="gradeRank" />
      <!-- 关联表 eduStudent 字段 -->
    <el-table-column label="身份证号" align="center" prop="eduStudentIdCardNo" />
    <el-table-column label="班级" align="center" prop="eduStudentCurrentClass" :formatter="eduStudentCurrentClassFormat" />
    <el-table-column label="年级" align="center" prop="eduStudentCurrentGrade" :formatter="eduStudentCurrentGradeFormat" />
    <el-table-column label="学生姓名" align="center" prop="eduStudentStudentName" />
    <el-table-column label="手机号" align="center" prop="eduStudentMobile" />
    <el-table-column label="父亲手机" align="center" prop="eduStudentFatherMobile" />
    <el-table-column label="母亲手机" align="center" prop="eduStudentMotherMobile" />
    <el-table-column label="学校" align="center" prop="eduStudentCurrentSchool" :formatter="eduStudentCurrentSchoolFormat" />
      <!-- 关联表 eduExam 字段 -->
    <el-table-column label="考试日期" align="center" prop="eduExamExamDate" />
    <el-table-column label="考试编号" align="center" prop="eduExamExamNo" />
    <el-table-column label="考试名称" align="center" prop="eduExamExamName" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['edu:score:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['edu:score:remove']"
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

    <!-- 添加或修改学生考试信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

    <el-form-item label="学生id" prop="studentId">
        <el-select
            v-model="form.studentId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入身份证号搜索"
            :remote-method="remoteFetchEduStudent"
            value-key="id"
        >
            <el-option
                v-for="item in eduStudentOptions"
                :key="item.id"
                :label="item.idCardNo"
                :value="item.id"
            />
        </el-select>
    </el-form-item>

    <el-form-item label="考试id" prop="examId">
        <el-select
            v-model="form.examId"
            filterable
            remote
            reserve-keyword
            placeholder="请输入考试日期搜索"
            :remote-method="remoteFetchEduExam"
            value-key="id"
        >
            <el-option
                v-for="item in eduExamOptions"
                :key="item.id"
                :label="item.examDate"
                :value="item.id"
            />
        </el-select>
    </el-form-item>


        <el-form-item label="语文分数" prop="chineseScore">
          <el-input v-model="form.chineseScore" placeholder="请输入语文分数" />
        </el-form-item>
        <el-form-item label="数学分数" prop="mathScore">
          <el-input v-model="form.mathScore" placeholder="请输入数学分数" />
        </el-form-item>
        <el-form-item label="英语分数" prop="englishScore">
          <el-input v-model="form.englishScore" placeholder="请输入英语分数" />
        </el-form-item>
        <el-form-item label="化学分数" prop="chemistryScore">
          <el-input v-model="form.chemistryScore" placeholder="请输入化学分数" />
        </el-form-item>
        <el-form-item label="物理分数" prop="physicsScore">
          <el-input v-model="form.physicsScore" placeholder="请输入物理分数" />
        </el-form-item>
        <el-form-item label="生物分数" prop="biologyScore">
          <el-input v-model="form.biologyScore" placeholder="请输入生物分数" />
        </el-form-item>
        <el-form-item label="政治分数" prop="politicsScore">
          <el-input v-model="form.politicsScore" placeholder="请输入政治分数" />
        </el-form-item>
        <el-form-item label="历史分数" prop="historyScore">
          <el-input v-model="form.historyScore" placeholder="请输入历史分数" />
        </el-form-item>
        <el-form-item label="地理分数" prop="geographyScore">
          <el-input v-model="form.geographyScore" placeholder="请输入地理分数" />
        </el-form-item>
        <el-form-item label="思想品德分数" prop="ethicScore">
          <el-input v-model="form.ethicScore" placeholder="请输入思想品德分数" />
        </el-form-item>
        <el-form-item label="班级排名" prop="classRank">
          <el-input v-model="form.classRank" placeholder="请输入班级排名" />
        </el-form-item>
        <el-form-item label="级部排名" prop="gradeRank">
          <el-input v-model="form.gradeRank" placeholder="请输入级部排名" />
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
import { listScore, getScore, delScore, addScore, updateScore, exportScore } from "@/api/edu/score";
import { listStudent } from "@/api/edu/student";
import { listExam } from "@/api/edu/exam";


export default {
  name: "Score",
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
      // 学生考试信息表格数据
      scoreList: [],
      eduStudentOptions: [],
      eduExamOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 修改人id字典
      eduStudentCurrentClassOptions: [],
      // 修改人id字典
      eduStudentCurrentGradeOptions: [],
      // 修改人id字典
      eduStudentCurrentSchoolOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        studentId: undefined,
        examId: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        studentId: [
          { required: true, message: "学生id不能为空", trigger: "blur" }
        ],
        examId: [
          { required: true, message: "考试id不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();

    this.getDicts("edu_class").then(response => {
        this.eduStudentCurrentClassOptions = response.data;
    });
    this.getDicts("edu_grade").then(response => {
        this.eduStudentCurrentGradeOptions = response.data;
    });
    this.getDicts("edu_school").then(response => {
        this.eduStudentCurrentSchoolOptions = response.data;
    });
  },
  methods: {
    /** 查询学生考试信息列表 */
    getList() {
      this.loading = true;
      listScore(this.queryParams).then(response => {
        this.scoreList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    remoteFetchEduStudent(keyword) {
        let that = this;
        listStudent({
             pageNum: 1,
             pageSize: 10,
             idCardNo: keyword
         }).then(response => {
            that.eduStudentOptions = response.rows
        });
    },
    remoteFetchEduExam(keyword) {
        let that = this;
        listExam({
             pageNum: 1,
             pageSize: 10,
             examDate: keyword
         }).then(response => {
            that.eduExamOptions = response.rows
        });
    },
    // 考试id字典翻译
    eduStudentCurrentClassFormat(row, column) {
        return this.selectDictLabel(this.eduStudentCurrentClassOptions, row.eduStudentCurrentClass);
    },
    // 考试id字典翻译
    eduStudentCurrentGradeFormat(row, column) {
        return this.selectDictLabel(this.eduStudentCurrentGradeOptions, row.eduStudentCurrentGrade);
    },
    // 考试id字典翻译
    eduStudentCurrentSchoolFormat(row, column) {
        return this.selectDictLabel(this.eduStudentCurrentSchoolOptions, row.eduStudentCurrentSchool);
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
        studentId: undefined,
        examId: undefined,
        chineseScore: undefined,
        mathScore: undefined,
        englishScore: undefined,
        chemistryScore: undefined,
        physicsScore: undefined,
        biologyScore: undefined,
        politicsScore: undefined,
        historyScore: undefined,
        geographyScore: undefined,
        ethicScore: undefined,
        classRank: undefined,
        gradeRank: undefined,
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
      this.title = "添加学生考试信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getScore(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改学生考试信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateScore(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addScore(this.form).then(response => {
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
      this.$confirm('是否确认删除学生考试信息编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delScore(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有学生考试信息数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportScore(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
