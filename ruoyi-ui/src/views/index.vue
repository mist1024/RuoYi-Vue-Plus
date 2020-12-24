<template>
  <div class="app-container home">
    <!--    <el-divider />-->

    <el-row :gutter="10">
      <el-col :span="10">
        <el-table v-loading="loading" :data="notifyList" border>
          <!--      <el-table-column type="selection" width="55" align="center"/>-->
          <el-table-column label="阅读标志" align="center" prop="id" v-if="false"/>
          <el-table-column label="消息类型" align="center" prop="messageType"/>
          <!--      <el-table-column label="消息范围" align="center" prop="scope"/>-->
          <el-table-column label="消息内容" align="center" prop="messageBody"/>
          <!--      <el-table-column label="创建时间" align="center" prop="createAt" width="180">-->
          <!--        <template slot-scope="scope">-->
          <!--          <span>{{ parseTime(scope.row.createAt, '{y}-{m}-{d}') }}</span>-->
          <!--        </template>-->
          <!--      </el-table-column>-->
          <!--      <el-table-column label="阅读标志" align="center" prop="readFlag" />-->
          <!--      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">-->
          <!--        <template slot-scope="scope">-->
          <!--          <el-button-->
          <!--            size="mini"-->
          <!--            type="text"-->
          <!--            icon="el-icon-edit"-->
          <!--            @click="handleUpdate(scope.row)"-->
          <!--            v-hasPermi="['fantang:notify:edit']"-->
          <!--          >修改-->
          <!--          </el-button>-->
          <!--          <el-button-->
          <!--            size="mini"-->
          <!--            type="text"-->
          <!--            icon="el-icon-delete"-->
          <!--            @click="handleDelete(scope.row)"-->
          <!--            v-hasPermi="['fantang:notify:remove']"-->
          <!--          >删除-->
          <!--          </el-button>-->
          <!--        </template>-->
          <!--      </el-table-column>-->
        </el-table>
      </el-col>
      <el-col :span="14">
        <el-table v-loading="loading" :data="syncConflictList" border>
          <!--          <el-table-column type="selection" width="55" align="center" />-->
          <!--          <el-table-column label="${comment}" align="center" prop="id" v-if="false"/>-->
          <el-table-column label="住院号" align="center" prop="hospitalId"/>
          <el-table-column label="姓名" align="center" prop="name"/>
          <el-table-column label="科室" align="center" prop="departName"/>
          <el-table-column label="床号" align="center" prop="bedId"/>
          <el-table-column label="本地住院号" align="center" prop="oldHospitalId"/>
          <el-table-column label="本地姓名" align="center" prop="oldName"/>
          <el-table-column label="本地科室" align="center" prop="oldDepartName"/>
          <el-table-column label="本地床号" align="center" prop="oldBedId"/>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['fantang:syncConflict:edit']"
              >处理冲突
              </el-button>
              <!--              <el-button-->
              <!--                size="mini"-->
              <!--                type="text"-->
              <!--                icon="el-icon-delete"-->
              <!--                @click="handleDelete(scope.row)"-->
              <!--                v-hasPermi="['fantang:syncConflict:remove']"-->
              <!--              >删除-->
              <!--              </el-button>-->
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <!-- 添加或修改同步冲突对话框 -->
    <el-dialog title="处理冲突" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="ConflictForm" :model="ConflictForm" label-width="80px">
        <el-row :gutter="10">
          <el-col :span="8">
            <el-form-item label="住院号" prop="hospitalId">
              <el-input v-model="ConflictForm.hospitalId" :disabled="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="9">
            <el-form-item label="本地住院号" prop="oldHospitalId" label-width="100px">
              <el-input v-model="ConflictForm.oldHospitalId" :disabled="true"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="8">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="ConflictForm.name" :disabled="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="9">
            <el-form-item label="本地姓名" prop="oldName" label-width="100px">
              <el-input v-model="ConflictForm.oldName" :disabled="true"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="8">
            <el-form-item label="科室" prop="departName">
              <el-input v-model="ConflictForm.departName" :disabled="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="9">
            <el-form-item label="本地科室" prop="oldDepartName" label-width="100px">
              <el-input v-model="ConflictForm.oldDepartName" :disabled="true"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="8">
            <el-form-item label="床号" prop="bedId">
              <el-input v-model="ConflictForm.bedId" :disabled="true"/>
            </el-form-item>
          </el-col>
          <el-col :span="9">
            <el-form-item label="本地床号" prop="oldBedId" label-width="100px">
              <el-input v-model="ConflictForm.oldBedId" :disabled="true"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="科室 id" prop="departId" v-if="false">
          <el-input v-model="ConflictForm.departId"/>
        </el-form-item>
        <el-form-item label="本地科室 id" prop="oldDepartId" v-if="false">
          <el-input v-model="ConflictForm.oldDepartId"/>
        </el-form-item>
        <el-form-item label="病人 id" prop="patientId" v-if="false">
          <el-input v-model="ConflictForm.patientId"/>
        </el-form-item>
        <el-form-item label="解决方式" prop="patientFlag">
          <el-select v-model="ConflictForm.patientFlag" placeholder="请选择正确的数据来源">
            <el-option
              v-for="item in patientFlagOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
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
import {isHaveNewMsg, listNotify, solveConflict} from "@/api/fantang/notify";
import {getSyncConflict, listSyncConflict} from "@/api/fantang/syncConflict";

export default {
  name: "index",
  data() {
    return {
      patientFlagOptions: [{
        value: 1,
        label: '以本系统为准'
      }, {
        value: 2,
        label: '以 his 数据为准'
      }],
      open: false,
      ConflictForm: {},
      // 遮罩层
      loading: true,
      // 版本号
      version: "3.2.1",
      timer: '',
      value: 0,
      // 病患冲突列表
      syncConflictList: [],
      //病患冲突消息列表
      notifyList: [],
      msgListSize: 0,
      msgBody: null,

      queryParams: {
        messageType: 1,
      },

      queryConflictParams: {
        isSolve: 0,
      }
    };
  },
  created() {
    listNotify(this.queryParams).then(response => {
      this.notifyList = response.rows;
      this.loading = false;
    });
    listSyncConflict(this.queryConflictParams).then(response => {
      this.syncConflictList = response.rows;
      this.loading = false;
    });
    this.timer = setInterval(this.getNewMsg, 5000);
  },
  beforeDestroy() {
    clearInterval(this.timer);
  },
  methods: {
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSyncConflict(id).then(response => {
        this.ConflictForm = response.data;
        this.open = true;
        this.title = "修改同步冲突";
      });
    },

    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.ConflictForm.id != null && this.ConflictForm.patientFlag != null) {
            solveConflict(this.ConflictForm).then(response => {
              this.msgSuccess("处理成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

    cancel() {
      this.open = false;
      this.reset();
    },

    getNewMsg() {
      isHaveNewMsg().then(response => {
        console.log(response.data);
        this.msgListSize = response.data.size;
        this.msgBody = response.data.msgBody;

        if (response.data.size > 0) {
          this.$notify({
            title: response.data.msgBody,
            dangerouslyUseHTMLString: true,
            message: '<a href="/">点击处理</a>'
          });
        }
      })

    },
    reset() {
      this.ConflictForm = {
        id: undefined,
        hospitalId: undefined,
        name: undefined,
        departName: undefined,
        bedId: undefined,
        oldHospitalId: undefined,
        oldName: undefined,
        oldDepartName: undefined,
        oldBedId: undefined,
        departId: undefined,
        oldDepartId: undefined,
        patientFlag: undefined,
        patientId: undefined,
      };
      this.resetForm("form");
    },
  },
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }

  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }

  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

