<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="130px">
      <el-form-item label="客户编号" prop="downstreamCode">
        <el-input v-model="queryParams.downstreamCode" style="width: 240px" placeholder="请输入客户编号" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="客户名称" prop="downstreamName">
        <el-input v-model="queryParams.downstreamName" style="width: 240px" placeholder="请输入客户名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="客户别名" prop="downstreamAlias">
        <el-input v-model="queryParams.downstreamAlias" style="width: 240px" placeholder="请输入客户别名" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <!-- <el-form-item label="客户地址" prop="downstreamAddress">
        <el-input v-model="queryParams.downstreamAddress" placeholder="请输入客户地址" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item> -->
      <el-form-item label="送货方式" prop="deliveryMethod">
        <el-select v-model="queryParams.deliveryMethod" style="width: 240px" placeholder="请输入送货方式" clearable size="small">
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
      <!-- <el-form-item label="送货顺序" prop="deliverySort">
        <el-input v-model="queryParams.deliverySort" placeholder="请输入送货顺序" clearable @keyup.enter.native="handleQuery"/>
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
                   v-hasPermi="['distribution:downstream-info:create']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['distribution:downstream-info:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table border height="680" header-align="center" :row-class-name="tableRowClassName" v-loading="loading" :data="list">
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <el-table-column label="序号" align="center" min-width="50px" fixed="left" type="index"/>
      <el-table-column label="客户名称" align="center" prop="downstreamName" min-width="80px" fixed="left"/>
      <el-table-column label="客户别名" align="center" prop="downstreamAlias" min-width="80px" fixed="left"/>
      <el-table-column label="送货方式" align="center" prop="deliveryMethod" min-width="80px" fixed="left">
        <template v-slot="scope">
          <dict-tag :type="DICT_TYPE.DISTRIBUTION_DELIVERY_METHOD" :value="scope.row.deliveryMethod" />
        </template>
      </el-table-column>
      <el-table-column label="客户地址" align="center" prop="downstreamAddress" min-width="250px"/>
      <el-table-column label="送货顺序" align="center" prop="deliverySort" min-width="80px"/>
      <el-table-column label="客户编号" align="center" prop="downstreamCode" min-width="150px"/>
      <el-table-column label="备注" align="center" prop="note" min-width="150px"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" min-width="150px" fixed="right">
        <template v-slot="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['distribution:downstream-info:update']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['distribution:downstream-info:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" v-dialogDrag append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="客户编号" prop="downstreamCode">
          <el-input v-model="form.downstreamCode" placeholder="请输入客户编号" />
        </el-form-item>
        <el-form-item label="客户名称" prop="downstreamName">
          <el-input v-model="form.downstreamName" placeholder="请输入客户名称" />
        </el-form-item>
        <el-form-item label="客户别名" prop="downstreamAlias">
          <el-input v-model="form.downstreamAlias" placeholder="请输入客户别名" />
        </el-form-item>
        <el-form-item label="客户地址" prop="downstreamAddress">
          <el-input v-model="form.downstreamAddress" placeholder="请输入客户地址" />
        </el-form-item>
        <el-form-item label="送货方式" prop="deliveryMethod">
          <el-select v-model="form.deliveryMethod" placeholder="请输入送货方式">
            <el-option v-for="dict in deliveryMethodDictDatas" :key="parseInt(dict.value)" :label="dict.label" :value="parseInt(dict.value)"/>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="note">
          <el-input v-model="form.note" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="送货顺序" prop="deliverySort">
          <el-input v-model="form.deliverySort" placeholder="请输入送货顺序" />
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
import { createDownstreamInfo, updateDownstreamInfo, deleteDownstreamInfo, getDownstreamInfo, getDownstreamInfoPage, exportDownstreamInfoExcel } from "@/api/distribution/downstreamInfo";
import {DICT_TYPE, getDictDatas} from "@/utils/dict";

export default {
  name: "DownstreamInfo",
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
      // 配货客户信息列表
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        downstreamCode: null,
        downstreamName: null,
        downstreamAlias: null,
        downstreamAddress: null,
        deliveryMethod: null,
        note: null,
        createTime: [],
        deliverySort: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        downstreamCode: [{ required: true, message: "客户编号不能为空", trigger: "blur" }],
        downstreamName: [{ required: true, message: "客户名称不能为空", trigger: "blur" }],
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
      getDownstreamInfoPage(this.queryParams).then(response => {
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
        downstreamCode: undefined,
        downstreamName: undefined,
        downstreamAlias: undefined,
        downstreamAddress: undefined,
        deliveryMethod: undefined,
        note: undefined,
        deliverySort: undefined,
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
      this.title = "添加配货客户信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getDownstreamInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改配货客户信息";
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
          updateDownstreamInfo(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        createDownstreamInfo(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除配货客户信息编号为"' + id + '"的数据项?').then(function() {
          return deleteDownstreamInfo(id);
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
      this.$modal.confirm('是否确认导出所有配货客户信息数据项?').then(() => {
          this.exportLoading = true;
          return exportDownstreamInfoExcel(params);
        }).then(response => {
          this.$download.excel(response, '配货客户信息.xls');
          this.exportLoading = false;
        }).catch(() => {});
    },
    tableRowClassName({row, rowIndex}) {
      if (row.deliveryMethod === null) {
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
