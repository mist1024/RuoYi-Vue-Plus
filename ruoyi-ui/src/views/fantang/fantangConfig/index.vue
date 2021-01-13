<template>
  <div class="app-container">
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="最大补贴金额">
        <el-input v-model="formInline.configValue" placeholder="请输入最大补贴金额"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
      </el-form-item>
    </el-form>
    <el-form :inline="true" :model="dinnerForm">
      <el-row>
        <el-col>
          <el-form-item label="早餐时间段" label-width="97px">
            <el-time-picker
              is-range
              ref="breakfastPick"
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
          <el-form-item label="午餐时间段" label-width="97px">
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
          <el-form-item label="晚餐时间段" label-width="97px">
            <el-time-picker
              is-range
              v-model="dinnerForm.dinner"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              placeholder="选择时间范围">
            </el-time-picker>
          </el-form-item>
          <el-button type="primary" @click="submitDinnerForm">提交</el-button>
        </el-col>
      </el-row>
    </el-form>

    <el-form :inline="true" :model="faceDeviceForm">
      <el-form-item label="人脸识别设备" label-width="97px">
        <el-input v-model="faceDeviceForm.deviceId" placeholder="请输入设备 id"></el-input>
      </el-form-item>
      <el-form-item label="是否开启">
        <el-select v-model="faceDeviceForm.deviceFlag" placeholder="请选择是否开启">
          <el-option
            v-for="item in deviceFlagOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-button type="primary" @click="submitFaceDeviceForm">提交</el-button>
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
  updateDinnerTime,
  updateFantangConfig
} from "@/api/fantang/fantangConfig";
import {updateFaceDevice} from "../../../api/fantang/fantangConfig";

export default {
  name: "FantangConfig",
  components: {},
  data() {
    return {
      deviceFlagOptions: [{
        value: '1',
        label: '开启'
      }, {
        value: '0',
        label: '关闭'
      }],
      timeArr: [],
      formInline: {
        configValue: null,
        id: null,
      },
      dinnerForm: {
        breakfast: [],
        lunch: [],
        dinner: [],
        id: 7,
      },
      faceDeviceForm: {
        deviceId: null,
        deviceConfigId: 11,
        deviceFlag: null,
        flagConfigId: 12,
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
      listFantangConfig().then(response => {
        this.formInline.configValue = response.rows[7].configValue
        this.formInline.id = response.rows[7].id
        this.timeArr = response.rows[6].configValue.split(',')
        this.dinnerForm.breakfast = [new Date(2016, 9, 1, this.timeArr[0].split(':')[0], this.timeArr[0].split(':')[1]), new Date(2016, 9, 1, this.timeArr[1].split(':')[0], this.timeArr[1].split(':')[1])];
        this.dinnerForm.lunch = [new Date(2016, 9, 1, this.timeArr[2].split(':')[0], this.timeArr[2].split(':')[1]), new Date(2016, 9, 1, this.timeArr[3].split(':')[0], this.timeArr[3].split(':')[1])];
        this.dinnerForm.dinner = [new Date(2016, 9, 1, this.timeArr[4].split(':')[0], this.timeArr[4].split(':')[1]), new Date(2016, 9, 1, this.timeArr[5].split(':')[0], this.timeArr[5].split(':')[1])];
        this.faceDeviceForm.deviceId = response.rows[10].configValue;
        this.faceDeviceForm.deviceFlag = response.rows[11].configValue;
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

    // 人脸识别设备
    submitFaceDeviceForm() {
      if (this.faceDeviceForm.deviceId != null && this.faceDeviceForm.deviceId !== ''
        && this.faceDeviceForm.deviceFlag != null && this.faceDeviceForm.deviceFlag !== '') {
        console.log(this.faceDeviceForm)
        updateFaceDevice(this.faceDeviceForm).then(response => {
            this.msgSuccess("修改成功")
        })

      }
    },

    // 用餐时间提交
    submitDinnerForm() {
      updateDinnerTime(this.dinnerForm).then(response => {
        this.msgSuccess("修改成功")
      })
    },

    // 补贴金额
    onSubmit() {
      if (this.formInline.configValue != null || this.formInline.configValue !== '')
        updateFantangConfig(this.formInline).then(response => {
          this.msgSuccess("修改成功")
        })
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
