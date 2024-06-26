<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="130px">
      <el-form-item label="供应商编号" prop="upstreamCode">
        <el-input v-model="queryParams.upstreamCode" style="width: 240px" placeholder="请输入供应商编号" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="供应商名称" prop="upstreamName">
        <el-input v-model="queryParams.upstreamName" style="width: 240px" placeholder="请输入供应商名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="供应商别名" prop="upstreamAlias">
        <el-input v-model="queryParams.upstreamAlias" style="width: 240px" placeholder="请输入供应商别名" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <!-- <el-form-item label="供应商地址" prop="upstreamAddress">
        <el-input v-model="queryParams.upstreamAddress" placeholder="请输入供应商地址" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item> -->
      <el-form-item label="取货方式" prop="pickupMethod">
        <el-select v-model="queryParams.pickupMethod" style="width: 240px" placeholder="请输入取货方式" clearable size="small">
          <el-option v-for="dict in this.getDictDatas(DICT_TYPE.DISTRIBUTION_DELIVERY_METHOD)"
                     :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="备注" prop="note">
        <el-input v-model="queryParams.note" placeholder="请输入备注" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item> -->
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker v-model="queryParams.createTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <!-- <el-form-item label="取货顺序" prop="pickupSort">
        <el-input v-model="queryParams.pickupSort" placeholder="请输入取货顺序" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['distribution:upstream-info:create']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['distribution:upstream-info:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table border height="680" header-align="center" :row-class-name="tableRowClassName" v-loading="loading" :data="list">
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <el-table-column label="序号" align="center" min-width="50px" fixed="left" type="index"/>
      <el-table-column label="供应商名称" align="center" prop="upstreamName" min-width="80px" fixed="left"/>
      <el-table-column label="供应商别名" align="center" prop="upstreamAlias" min-width="80px" fixed="left"/>
      <el-table-column label="取货方式" align="center" prop="pickupMethod" min-width="80px" fixed="left">
        <template v-slot="scope">
          <dict-tag :type="DICT_TYPE.DISTRIBUTION_DELIVERY_METHOD" :value="scope.row.pickupMethod" />
        </template>
      </el-table-column>
      <el-table-column label="供应商地址" align="center" prop="upstreamAddress" min-width="250px"/>
      <el-table-column label="取货顺序" align="center" prop="pickupSort" min-width="80px"/>
      <el-table-column label="供应商编号" align="center" prop="upstreamCode" min-width="150px"/>
      <el-table-column label="备注" align="center" prop="note" min-width="150px"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" min-width="150px" fixed="right">
        <template v-slot="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['distribution:upstream-info:update']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['distribution:upstream-info:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" v-dialogDrag append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="供应商编号" prop="upstreamCode">
          <el-input v-model="form.upstreamCode" placeholder="请输入供应商编号" />
        </el-form-item>
        <el-form-item label="供应商名称" prop="upstreamName">
          <el-input v-model="form.upstreamName" placeholder="请输入供应商名称" />
        </el-form-item>
        <el-form-item label="供应商别名" prop="upstreamAlias">
          <el-input v-model="form.upstreamAlias" placeholder="请输入供应商别名" />
        </el-form-item>
        <el-form-item label="供应商地址" prop="upstreamAddress">
          <el-input v-model="form.upstreamAddress" placeholder="请输入供应商地址" />
        </el-form-item>
        <el-form-item label="取货方式" prop="pickupMethod">
          <el-select v-model="form.pickupMethod" placeholder="请输入取货方式">
            <el-option v-for="dict in deliveryMethodDictDatas" :key="parseInt(dict.value)" :label="dict.label" :value="parseInt(dict.value)"/>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="note">
          <el-input v-model="form.note" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="取货顺序" prop="pickupSort">
          <el-input v-model="form.pickupSort" placeholder="请输入取货顺序" />
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
import { createUpstreamInfo, updateUpstreamInfo, deleteUpstreamInfo, getUpstreamInfo, getUpstreamInfoPage, exportUpstreamInfoExcel } from "@/api/distribution/upstreamInfo";
import {DICT_TYPE, getDictDatas} from "@/utils/dict";

export default {
  name: "UpstreamInfo",
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 配货供应商信息列表
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        upstreamCode: null,
        upstreamName: null,
        upstreamAlias: null,
        upstreamAddress: null,
        pickupMethod: null,
        note: null,
        createTime: [],
        pickupSort: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        upstreamCode: [{ required: true, message: "供应商编号不能为空", trigger: "blur" }],
        upstreamName: [{ required: true, message: "供应商名称不能为空", trigger: "blur" }],
      },
      deliveryMethodDictDatas: getDictDatas(DICT_TYPE.DISTRIBUTION_DELIVERY_METHOD),
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      // 执行查询
      getUpstreamInfoPage(this.queryParams).then(response => {
        this.list = response.data.list;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    /** 取消按钮 */
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 表单重置 */
    reset() {
      this.form = {
        id: undefined,
        upstreamCode: undefined,
        upstreamName: undefined,
        upstreamAlias: undefined,
        upstreamAddress: undefined,
        pickupMethod: undefined,
        note: undefined,
        pickupSort: undefined,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNo = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加配货供应商信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getUpstreamInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改配货供应商信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (!valid) {
          return;
        }
        // 修改的提交
        if (this.form.id != null) {
          updateUpstreamInfo(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        createUpstreamInfo(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除配货供应商信息编号为"' + id + '"的数据项?').then(function() {
          return deleteUpstreamInfo(id);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      // 处理查询参数
      let params = {...this.queryParams};
      params.pageNo = undefined;
      params.pageSize = undefined;
      this.$modal.confirm('是否确认导出所有配货供应商信息数据项?').then(() => {
          this.exportLoading = true;
          return exportUpstreamInfoExcel(params);
        }).then(response => {
          this.$download.excel(response, '配货供应商信息.xls');
          this.exportLoading = false;
        }).catch(() => {});
    },
    tableRowClassName({row, rowIndex}) {
      if (row.pickupMethod === null) {
        return 'warning-row';
      }
      return '';
    }
  }
};
</script>
<style>
  .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }
</style>
