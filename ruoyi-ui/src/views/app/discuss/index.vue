<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="150px">
<!--      <el-form-item label="部门id" prop="deptId">-->
<!--        <el-input-->
<!--          v-model="queryParams.deptId"-->
<!--          placeholder="请输入部门id"-->
<!--          clearable-->
<!--          size="small"-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->


      <el-form-item label="数据状态" prop="state">
        <el-select
          v-model="queryParams.state"
          placeholder="请输入数据状态，ON为数据启用，OFF为数据停用但仍在前端显示，DEL对数据用户来说已经删除"
          clearable
          size="small"
         @keyup.enter.native="handleQuery"
        >
          <el-option label="ON" value="0" />
          <el-option label="OFF" value="1" />

        </el-select>
      </el-form-item>
      <el-form-item label="创建数据的用户id" prop="createUser">
        <el-input
          v-model="queryParams.createUser"
          placeholder="请输入创建数据的用户id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="修改数据的用户id" prop="updateUser">
        <el-input
          v-model="queryParams.updateUser"
          placeholder="请输入修改数据的用户id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="回复评论的id" prop="appDiscussReplyId">
        <el-input
          v-model="queryParams.appDiscussReplyId"
          placeholder="请输入回复评论的id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评论内容" prop="appDiscussText">
        <el-input
          v-model="queryParams.appDiscussText"
          placeholder="请输入评论内容"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评论的内容id" prop="appAssociationId">
        <el-input
          v-model="queryParams.appAssociationId"
          placeholder="请输入评论的内容id"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="评论人姓名" prop="appDiscussExtraUsername">
        <el-input
          v-model="queryParams.appDiscussExtraUsername"
          placeholder="请输入评论人姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评论人手机号码" prop="appDiscussExtraMobile">
        <el-input
          v-model="queryParams.appDiscussExtraMobile"
          placeholder="请输入评论人手机号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评论推荐" prop="appDiscussRecommend">
        <el-input
          v-model="queryParams.appDiscussRecommend"
          placeholder="请输入评论推荐"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="评论类型" prop="appDiscussType">
        <el-select v-model="queryParams.appDiscussType" placeholder="请选择评论类型" clearable size="small">
          <el-option label="ON " value="0" />
          <el-option label="OFF" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item label="评论回复人" prop="appDiscussReplyUser">
        <el-input
          v-model="queryParams.appDiscussReplyUser"
          placeholder="请输入评论回复人"
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
          v-hasPermi="['system:discuss:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:discuss:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:discuss:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:discuss:export']"
        >导出</el-button>
      </el-col>
	  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="discussList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="表主键" align="center" prop="id" v-if="false"/>
<!--      <el-table-column label="部门id" align="center" prop="deptId" />-->
      <el-table-column label="数据状态"  align="center" prop="state" width="75" />
      <el-table-column label="创建数据的用户id" align="center" prop="createUser" width="125" />
      <el-table-column label="修改数据的用户id" align="center" prop="updateUser" width="125" />
      <el-table-column label="回复评论的id" align="center" prop="appDiscussReplyId"width="100" />
      <el-table-column label="评论内容" align="center" prop="appDiscussText"  />
      <el-table-column label="评论的内容id" align="center" prop="appAssociationId"width="105" />
      <el-table-column label="评论的图片" align="center" prop="appDiscussImage" width="105" />

      <el-table-column label="评论人姓名" align="center" prop="appDiscussExtraUsername" width="105"/>
      <el-table-column label="评论人手机号码" align="center" prop="appDiscussExtraMobile" width="115" />
      <el-table-column label="评论推荐" align="center" prop="appDiscussRecommend" />
      <el-table-column label="评论类型" align="center" prop="appDiscussType" />
      <el-table-column label="评论回复人" align="center" prop="appDiscussReplyUser" width="95" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:discuss:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:discuss:remove']"
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

    <!-- 添加或修改app评论对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="135px">
<!--        <el-form-item label="部门id" prop="deptId">-->
<!--          <el-input v-model="form.deptId" placeholder="请输入部门id" />-->
<!--        </el-form-item>-->
        <el-form-item label="数据状态"  prop="state">
        <el-select v-model="form.state" placeholder="请输入数据状态，ON为数据启用，OFF为数据停用但仍在前端显示，DEL对数据用户来说已经删除">
          <el-option label="ON" value="0" />
          <el-option label="OFF" value="1" />
        </el-select>
        </el-form-item>

        <el-form-item label="创建数据的用户id" prop="createUser">
          <el-input v-model="form.createUser" placeholder="请输入创建数据的用户id" />
        </el-form-item>
        <el-form-item label="修改数据的用户id" prop="updateUser">
          <el-input v-model="form.updateUser" placeholder="请输入修改数据的用户id" />
        </el-form-item>
        <el-form-item label="回复评论的id" prop="appDiscussReplyId">
          <el-input v-model="form.appDiscussReplyId" placeholder="请输入回复评论的id" />
        </el-form-item>
        <el-form-item label="评论内容" prop="appDiscussText">
          <el-input v-model="form.appDiscussText" placeholder="请输入评论内容" />
        </el-form-item>
        <el-form-item label="评论的内容id" prop="appAssociationId">
          <el-input v-model="form.appAssociationId" placeholder="请输入评论的内容id" />
        </el-form-item>
        <el-form-item label="评论的图片">
          <uploadImage v-model="form.appDiscussImage"/>
        </el-form-item>
<!--        <el-form-item label="评论视频" prop="appDiscussVideo">-->
<!--          <el-input v-model="form.appDiscussVideo" placeholder="请输入评论视频" />-->
<!--        </el-form-item>-->
        <el-form-item label="评论人姓名" prop="appDiscussExtraUsername">
          <el-input v-model="form.appDiscussExtraUsername" placeholder="请输入评论人姓名" />
        </el-form-item>
        <el-form-item label="评论人手机号码" prop="appDiscussExtraMobile">
          <el-input v-model="form.appDiscussExtraMobile" placeholder="请输入评论人手机号码" />
        </el-form-item>
        <el-form-item label="评论推荐" prop="appDiscussRecommend">
          <el-input v-model="form.appDiscussRecommend" placeholder="请输入评论推荐" />
        </el-form-item>
        <el-form-item label="评论类型" prop="appDiscussType">
          <el-select v-model="form.appDiscussType" placeholder="请选择评论类型">
            <el-option label="ON" value="0" />
            <el-option label="OFF" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label="评论回复人" prop="appDiscussReplyUser">
          <el-input v-model="form.appDiscussReplyUser" placeholder="请输入评论回复人" />
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
import { listDiscuss, getDiscuss, delDiscuss, addDiscuss, updateDiscuss, exportDiscuss } from "@/api/appdiscuss/discuss";
import UploadImage from '@/components/UploadImage';

export default {
  name: "Discuss",
  components: {
    UploadImage,
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
      // app评论表格数据
      discussList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptId: undefined,
        state: undefined,
        createUser: undefined,
        updateUser: undefined,
        appDiscussReplyId: undefined,
        appDiscussText: undefined,
        appAssociationId: undefined,
        appDiscussImage: undefined,
        appDiscussVideo: undefined,
        appDiscussExtraUsername: undefined,
        appDiscussExtraMobile: undefined,
        appDiscussRecommend: undefined,
        appDiscussType: undefined,
        appDiscussReplyUser: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        deptId: [
          { required: true, message: "部门id不能为空", trigger: "blur" }
        ],
        createBy: [
          { required: true, message: "创建者不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "数据创建时间不能为空", trigger: "blur" }
        ],
        state: [
          { required: true, message: "数据状态，ON为数据启用，OFF为数据停用但仍在前端显示，DEL对数据用户来说已经删除不能为空", trigger: "blur" }
        ],
        createUser: [
          { required: true, message: "创建数据的用户id不能为空", trigger: "blur" }
        ],
        updateUser: [
          { required: true, message: "修改数据的用户id不能为空", trigger: "blur" }
        ],
        appDiscussReplyId: [
          { required: true, message: "回复评论的id不能为空", trigger: "blur" }
        ],
        appDiscussText: [
          { required: true, message: "评论内容不能为空", trigger: "blur" }
        ],
        appAssociationId: [
          { required: true, message: "评论的内容id不能为空", trigger: "blur" }
        ],
        appDiscussExtraUsername: [
          { required: true, message: "评论人姓名不能为空", trigger: "blur" }
        ],
        appDiscussExtraMobile: [
          { required: true, message: "评论人手机号码不能为空", trigger: "blur" }
        ],
        appDiscussReplyUser: [
          { required: true, message: "评论回复人不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询app评论列表 */
    getList() {
      this.loading = true;
      listDiscuss(this.queryParams).then(response => {
        this.discussList = response.rows;
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
        deptId: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined,
        state: undefined,
        createUser: undefined,
        updateUser: undefined,
        appDiscussReplyId: undefined,
        appDiscussText: undefined,
        appAssociationId: undefined,
        appDiscussImage: undefined,
        appDiscussVideo: undefined,
        appDiscussExtraUsername: undefined,
        appDiscussExtraMobile: undefined,
        appDiscussRecommend: undefined,
        appDiscussType: undefined,
        appDiscussReplyUser: undefined
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
      this.title = "添加app评论";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDiscuss(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改app评论";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDiscuss(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDiscuss(this.form).then(response => {
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
      this.$confirm('是否确认删除app评论编号为"' + ids + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return delDiscuss(ids);
        }).then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有app评论数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(function() {
          return exportDiscuss(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
