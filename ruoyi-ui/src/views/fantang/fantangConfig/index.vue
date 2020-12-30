<template>
  <div class="app-container">
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="最大补贴金额">
        <el-input v-model="formInline.price" placeholder="请输入最大补贴金额"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
      </el-form-item>
    </el-form>
    <el-form :inline="true" :model="dinnerForm">
      <el-row>
        <el-col>
          <el-form-item label="早餐时间段">
            <el-time-picker
              is-range
              v-model="dinnerForm.breakfast"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              placeholder="选择时间范围">
            </el-time-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <el-form-item label="午餐时间段">
            <el-time-picker
              is-range
              v-model="dinnerForm.lunch"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              placeholder="选择时间范围">
            </el-time-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <el-form-item label="晚餐时间段">
            <el-time-picker
              is-range
              v-model="dinnerForm.dinner"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              placeholder="选择时间范围">
            </el-time-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" @click="submitDinnerForm">提交</el-button>
      </el-form-item>

    </el-form>

  </div>
</template>

<script>
import {
  addFantangConfig,
  delFantangConfig,
  exportFantangConfig,
  getFantangConfig,
  listFantangConfig,
  updateFantangConfig
} from "@/api/fantang/fantangConfig";

export default {
  name: "FantangConfig",
  components: {},
  data() {
    return {
      formInline: {
        price: null,
      },
      dinnerForm: {
        breakfast: null,
        lunch: null,
        dinner: null,
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询饭堂参数列表 */
    getList() {
      this.loading = true;
      listFantangConfig(this.queryParams).then(response => {
        this.fantangConfigList = response.rows;
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
        corpId: undefined,
        configKey: undefined,
        configValue: undefined,
        flag: undefined
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
      this.title = "添加饭堂参数";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getFantangConfig(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改饭堂参数";
      });
    },

    // 用餐时间提交
    submitDinnerForm(){
      console.log(this.dinnerForm)

    },

    // 补贴金额
    onSubmit(){
      console.log(this.formInline)
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateFantangConfig(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFantangConfig(this.form).then(response => {
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
      this.$confirm('是否确认删除饭堂参数编号为"' + ids + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delFantangConfig(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('是否确认导出所有饭堂参数数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return exportFantangConfig(queryParams);
      }).then(response => {
        this.download(response.msg);
      })
    }
  }
};
</script>
