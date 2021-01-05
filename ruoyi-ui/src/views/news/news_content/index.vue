<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
<!--      <el-form-item label="部门ID" prop="deptId">-->
<!--        <el-input-->
<!--          v-model="queryParams.deptId"-->
<!--          placeholder="请输入部门ID"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="新闻标题" prop="newsTitle">
        <el-input
          v-model="queryParams.newsTitle"
          placeholder="请输入新闻标题"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!--      <el-form-item label="新闻详情" prop="newsBody">-->
      <!--        <el-input-->
      <!--          v-model="queryParams.newsBody"-->
      <!--          placeholder="请输入新闻详情"-->
      <!--          clearable-->
      <!--          size="small"-->
      <!--          @keyup.enter.native="handleQuery"-->
      <!--        />-->
      <!--      </el-form-item>-->
      <el-form-item label="新闻类型" prop="newsType">
        <el-select v-model="queryParams.newsType" placeholder="请选择新闻类型" clearable size="small">
          <el-option
            v-for="dict in newsTypeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-select v-model="queryParams.state" placeholder="请选择状态" clearable size="small">
          <el-option
            v-for="dict in stateOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
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
          v-hasPermi="['news:news_content:add']"
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
          v-hasPermi="['news:news_content:edit']"
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
          v-hasPermi="['news:news_content:remove']"
        >删除
        </el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="handleExport"-->
<!--          v-hasPermi="['news:news_content:export']"-->
<!--        >导出-->
<!--        </el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="news_contentList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="规格ID" align="center" prop="id" v-if="false"/>
<!--      <el-table-column label="部门ID" align="center" prop="deptId"/>-->
      <el-table-column label="新闻标题" align="center" prop="newsTitle"/>
<!--      <el-table-column label="新闻详情" align="center" prop="newsBody"/>-->
      <el-table-column label="新闻封面图" align="center" prop="newsImage" width="100px">
        <template slot-scope="scope">
          <el-image :src="scope.row.newsImage|getImageForKey" style="width: 60px; height: 60px"/>
        </template>

      </el-table-column>
      <el-table-column label="新闻类型" align="center" prop="newsType" :formatter="newsTypeFormat"/>
      <el-table-column label="状态" align="center" prop="state" :formatter="stateFormat" width="100px">
        <template slot-scope="scope">
          <el-tag :type="scope.row.state === 1 ? 'success' : 'danger'">
            {{scope.row.state | getStateName(stateOptions)}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['news:news_content:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['news:news_content:remove']"
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

    <!-- 添加或修改新闻资讯对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <!--        <el-form-item label="部门ID" prop="deptId">-->
        <!--          <el-input v-model="form.deptId" placeholder="请输入部门ID"/>-->
        <!--        </el-form-item>-->
        <el-form-item label="新闻标题" prop="newsTitle">
          <el-input v-model="form.newsTitle" placeholder="请输入新闻标题"/>
        </el-form-item>

        <el-form-item label="新闻封面图">
          <uploadImage v-model="form.newsImage"/>
        </el-form-item>
        <el-form-item label="新闻类型" prop="newsType">
          <!--          <el-select v-model="form.newsType" placeholder="请选择新闻类型">-->
          <!--            <el-option-->
          <!--              v-for="dict in newsTypeOptions"-->
          <!--              :key="dict.dictValue"-->
          <!--              :label="dict.dictLabel"-->
          <!--              :value="parseInt(dict.dictValue)"-->
          <!--            ></el-option>-->
          <!--          </el-select>-->
          <el-radio-group v-model="form.newsType">
            <el-radio v-for="(dict, index) in newsTypeOptions" :key="index" :label="parseInt(dict.dictValue)">
              {{dict.dictLabel}}
            </el-radio>
          </el-radio-group>

        </el-form-item>
        <el-form-item label="状态" prop="state">
          <!--          <el-select v-model="form.state" placeholder="请选择状态">-->
          <!--            <el-option-->
          <!--              v-for="dict in stateOptions"-->
          <!--              :key="dict.dictValue"-->
          <!--              :label="dict.dictLabel"-->
          <!--              :value="parseInt(dict.dictValue)"-->
          <!--            ></el-option>-->
          <!--          </el-select>-->

          <el-radio-group v-model="form.state">
            <el-radio v-for="(dict, index) in stateOptions" :key="index" :label="parseInt(dict.dictValue)">{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="新闻详情" prop="newsBody">
          <!--  <el-input v-model="form.newsBody" placeholder="请输入新闻详情" />-->
          <editor :value="form.newsBody" :height="400" :min-height="400" @on-change="onChangeNewsBody"/>
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
  listNews_content,
  getNews_content,
  delNews_content,
  addNews_content,
  updateNews_content,
  exportNews_content
} from "@/api/news/news_content";
import UploadImage from '@/components/UploadImage';
import CommonMixin from "@/mixin/common";
import Editor from '@/components/Editor';
import {getDictName} from "@/utils/utils";
import {delTable} from "@/api/tool/gen";

export default {
  name: "News_content",
  mixins: [CommonMixin],
  components: {
    UploadImage,
    Editor
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
      // 新闻资讯表格数据
      news_contentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 新闻类型字典
      newsTypeOptions: [],
      // 状态字典
      stateOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: undefined,
        newsTitle: undefined,
        newsBody: undefined,
        newsImage: undefined,
        newsType: undefined,
        state: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        deptId: [
          {required: true, message: "部门ID不能为空", trigger: "blur"}
        ],
        createBy: [
          {required: true, message: "创建者不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
      }
    };
  },
  filters: {
    getStateName(value, options) {
      return getDictName(value, options)
    }
  },
  created() {
    this.getList();
    this.getDicts("news_type").then(response => {
      this.newsTypeOptions = response.data;
    });
    this.getDicts("sys_release_status").then(response => {
      this.stateOptions = response.data;
    });
  },
  methods: {
    /** 查询新闻资讯列表 */
    getList() {
      this.loading = true;
      listNews_content(this.queryParams).then(response => {
        this.news_contentList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 新闻类型字典翻译
    newsTypeFormat(row, column) {
      return this.selectDictLabel(this.newsTypeOptions, row.newsType);
    },
    // 状态字典翻译
    stateFormat(row, column) {
      return this.selectDictLabel(this.stateOptions, row.state);
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
        deptId: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
        newsTitle: undefined,
        newsBody: undefined,
        newsImage: undefined,
        newsType: 0,
        state: 0
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
      this.title = "添加新闻资讯";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getNews_content(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改新闻资讯";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateNews_content(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addNews_content(this.form).then(response => {
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
      this.$confirm('是否确认删除新闻资讯编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delNews_content(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有新闻资讯数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportNews_content(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    },
    onChangeNewsBody(value) {

      console.log(value)
      this.form.newsBody = value.html
    }
  }
};
</script>
