<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="130px">
      <el-form-item label="订单所属日期" prop="orderDate">
        <el-date-picker v-model="queryParams.orderDate" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="综合订单编号" prop="comprehensiveOrderCode">
        <el-input v-model="queryParams.comprehensiveOrderCode" style="width: 240px" placeholder="请输入综合订单编号" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="综合订单文件名称" prop="comprehensiveOrderFileName">
        <el-input v-model="queryParams.comprehensiveOrderFileName" style="width: 240px" placeholder="请输入综合订单文件名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <!-- <el-form-item label="综合订单文件URL" prop="comprehensiveOrderFileUrl">
        <el-input v-model="queryParams.comprehensiveOrderFileUrl" placeholder="请输入综合订单文件URL" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="订单总数量" prop="totalOrderNumber">
        <el-input v-model="queryParams.totalOrderNumber" placeholder="请输入订单总数量" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="订单销售总金额" prop="totalOrderSalesAmount">
        <el-input v-model="queryParams.totalOrderSalesAmount" placeholder="请输入订单销售总金额" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="订单成本总金额" prop="totalOrderCostAmount">
        <el-input v-model="queryParams.totalOrderCostAmount" placeholder="请输入订单成本总金额" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="订单计划利润总金额" prop="totalOrderPlanProfitAmount">
        <el-input v-model="queryParams.totalOrderPlanProfitAmount" placeholder="请输入订单计划利润总金额" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item> -->
      <el-form-item label="结算标志" prop="settlementFlag">
        <el-select v-model="queryParams.settlementFlag" style="width: 240px" placeholder="请选择结算标志" clearable size="small">
          <el-option v-for="dict in this.getDictDatas(DICT_TYPE.DISTRIBUTION_SETTLEMENT_FLAG)"
                     :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item label="结算时间" prop="settlementTime">
        <el-date-picker v-model="queryParams.settlementTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
     <!-- <el-form-item label="订单成交总数量" prop="totalOrderTransactionsNumber">
        <el-input v-model="queryParams.totalOrderTransactionsNumber" placeholder="请输入订单成交总数量" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="订单退货总数量" prop="totalOrderReturnNumber">
        <el-input v-model="queryParams.totalOrderReturnNumber" placeholder="请输入订单退货总数量" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="订单实际利润总金额" prop="totalOrderActualProfitAmount">
        <el-input v-model="queryParams.totalOrderActualProfitAmount" placeholder="请输入订单实际利润总金额" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item> -->
      <!-- <el-form-item label="删除时间" prop="deletedTime">
        <el-date-picker v-model="queryParams.deletedTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item> -->
      <!-- <el-form-item label="备注" prop="note">
        <el-input v-model="queryParams.note" placeholder="请输入备注" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item> -->
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker v-model="queryParams.createTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="info" icon="el-icon-upload2" size="mini" @click="handleImport"
                   v-hasPermi="['distribution:comprehensive-order-info:import']">上传综合订单</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" icon="el-icon-video-play" size="mini" @click="handleAssignOrderInfoCode"
                   v-hasPermi="['distribution:comprehensive-order-info:assigncode']">生成订单编码</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['distribution:comprehensive-order-info:create']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['distribution:comprehensive-order-info:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 综合订单导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport" :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress" :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否覆盖今日其他订单
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 列表 -->
    <el-table border height="680" header-align="center" v-loading="loading" ref="tables" :data="list">
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <el-table-column type="selection" width="55px"/>
      <el-table-column label="序号" align="center" min-width="50px" fixed="left" type="index"/>
      <el-table-column label="订单所属日期" align="center" prop="orderDate" min-width="100px" fixed="left"/>
      <el-table-column label="综合订单原始文件" align="center" prop="comprehensiveOrderFileName" min-width="150px" fixed="left">
        <template v-slot="scope">
          <i>
            <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" target="_blank"
                     :href="scope.row.comprehensiveOrderFileUrl" v-text="scope.row.comprehensiveOrderFileName">
            </el-link>
          </i>
        </template>
      </el-table-column>
      <el-table-column label="综合订单编号" align="center" prop="comprehensiveOrderCode" min-width="150px" fixed="left"/>
      <!-- <el-table-column label="综合订单文件URL" align="center" prop="content"> -->
      </el-table-column>
      <el-table-column label="订单总数量" align="center" prop="totalOrderNumber" min-width="150px"/>
      <el-table-column label="订单销售总金额" align="center" prop="totalOrderSalesAmount" min-width="150px"/>
      <el-table-column label="订单成本总金额" align="center" prop="totalOrderCostAmount" min-width="150px"/>
      <el-table-column label="订单计划利润总金额" align="center" prop="totalOrderPlanProfitAmount" min-width="150px"/>
      <el-table-column label="结算标志" align="center" prop="settlementFlag">
        <template v-slot="scope">
          <dict-tag :type="DICT_TYPE.DISTRIBUTION_SETTLEMENT_FLAG" :value="scope.row.settlementFlag" />
        </template>
      </el-table-column>
      <el-table-column label="结算时间" align="center" prop="settlementTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.settlementTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="订单成交总数量" align="center" prop="totalOrderTransactionsNumber" min-width="150px" />
      <el-table-column label="订单退货总数量" align="center" prop="totalOrderReturnNumber" min-width="150px"/>
      <el-table-column label="订单实际利润总金额" align="center" prop="totalOrderActualProfitAmount" min-width="150px"/>
      <!-- <el-table-column label="删除时间" align="center" prop="deletedTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.deletedTime) }}</span>
        </template>
      </el-table-column> -->
      <el-table-column label="备注" align="center" prop="note" min-width="150px"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" min-width="150px" fixed="right">
        <template v-slot="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['distribution:comprehensive-order-info:update']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['distribution:comprehensive-order-info:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" v-dialogDrag append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单所属日期" prop="orderDate">
          <el-date-picker clearable v-model="form.orderDate" type="date" value-format="timestamp" placeholder="选择订单所属日期" />
        </el-form-item>
        <el-form-item label="综合订单编号" prop="comprehensiveOrderCode">
          <el-input v-model="form.comprehensiveOrderCode" placeholder="请输入综合订单编号" />
        </el-form-item>
        <el-form-item label="综合订单文件名称" prop="comprehensiveOrderFileName">
          <el-input v-model="form.comprehensiveOrderFileName" placeholder="请输入综合订单文件名称" />
        </el-form-item>
        <el-form-item label="综合订单文件URL" prop="comprehensiveOrderFileUrl">
          <el-input v-model="form.comprehensiveOrderFileUrl" placeholder="请输入综合订单文件URL" />
        </el-form-item>
        <el-form-item label="订单总数量" prop="totalOrderNumber">
          <el-input v-model="form.totalOrderNumber" placeholder="请输入订单总数量" />
        </el-form-item>
        <el-form-item label="订单销售总金额" prop="totalOrderSalesAmount">
          <el-input v-model="form.totalOrderSalesAmount" placeholder="请输入订单销售总金额" />
        </el-form-item>
        <el-form-item label="订单成本总金额" prop="totalOrderCostAmount">
          <el-input v-model="form.totalOrderCostAmount" placeholder="请输入订单成本总金额" />
        </el-form-item>
        <el-form-item label="订单计划利润总金额" prop="totalOrderPlanProfitAmount">
          <el-input v-model="form.totalOrderPlanProfitAmount" placeholder="请输入订单计划利润总金额" />
        </el-form-item>
        <el-form-item label="结算标志" prop="settlementFlag">
          <el-input v-model="form.settlementFlag" placeholder="请输入结算标志" />
        </el-form-item>
        <el-form-item label="结算时间" prop="settlementTime">
          <el-date-picker clearable v-model="form.settlementTime" type="date" value-format="timestamp" placeholder="选择结算时间" />
        </el-form-item>
        <el-form-item label="订单成交总数量" prop="totalOrderTransactionsNumber">
          <el-input v-model="form.totalOrderTransactionsNumber" placeholder="请输入订单成交总数量" />
        </el-form-item>
        <el-form-item label="订单退货总数量" prop="totalOrderReturnNumber">
          <el-input v-model="form.totalOrderReturnNumber" placeholder="请输入订单退货总数量" />
        </el-form-item>
        <el-form-item label="订单实际利润总金额" prop="totalOrderActualProfitAmount">
          <el-input v-model="form.totalOrderActualProfitAmount" placeholder="请输入订单实际利润总金额" />
        </el-form-item>
        <el-form-item label="删除时间" prop="deletedTime">
          <el-date-picker clearable v-model="form.deletedTime" type="date" value-format="timestamp" placeholder="选择删除时间" />
        </el-form-item>
        <el-form-item label="备注" prop="note">
          <el-input v-model="form.note" placeholder="请输入备注" />
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
   createComprehensiveOrderInfo,
   updateComprehensiveOrderInfo,
   deleteComprehensiveOrderInfo,
   getComprehensiveOrderInfo,
   getComprehensiveOrderInfoPage,
   exportComprehensiveOrderInfoExcel,
   exportComprehensiveOrderInfoImportTemplateExcel,
   assignOrderInfoCode
  } from "@/api/distribution/comprehensiveOrderInfo";
import {getBaseHeader} from "@/utils/request";

export default {
  name: "ComprehensiveOrderInfo",
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
      // 配货综合订单信息列表
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: getBaseHeader(),
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + '/admin-api/distribution/comprehensive-order-info/import'
      },
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        orderDate: [],
        comprehensiveOrderCode: null,
        comprehensiveOrderFileName: null,
        comprehensiveOrderFileUrl: null,
        totalOrderNumber: null,
        totalOrderSalesAmount: null,
        totalOrderCostAmount: null,
        totalOrderPlanProfitAmount: null,
        settlementFlag: null,
        settlementTime: [],
        totalOrderTransactionsNumber: null,
        totalOrderReturnNumber: null,
        totalOrderActualProfitAmount: null,
        deletedTime: [],
        note: null,
        createTime: [],
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderDate: [{ required: true, message: "订单所属日期不能为空", trigger: "blur" }],
        comprehensiveOrderCode: [{ required: true, message: "综合订单编号不能为空", trigger: "blur" }],
        comprehensiveOrderFileName: [{ required: true, message: "综合订单文件名称不能为空", trigger: "blur" }],
        comprehensiveOrderFileUrl: [{ required: true, message: "综合订单文件URL不能为空", trigger: "blur" }],
        totalOrderNumber: [{ required: true, message: "订单总数量不能为空", trigger: "blur" }],
        totalOrderSalesAmount: [{ required: true, message: "订单销售总金额不能为空", trigger: "blur" }],
        totalOrderCostAmount: [{ required: true, message: "订单成本总金额不能为空", trigger: "blur" }],
        totalOrderPlanProfitAmount: [{ required: true, message: "订单计划利润总金额不能为空", trigger: "blur" }],
        settlementFlag: [{ required: true, message: "结算标志不能为空", trigger: "blur" }],
      }
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
      getComprehensiveOrderInfoPage(this.queryParams).then(response => {
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
        orderDate: undefined,
        comprehensiveOrderCode: undefined,
        comprehensiveOrderFileName: undefined,
        comprehensiveOrderFileUrl: undefined,
        totalOrderNumber: undefined,
        totalOrderSalesAmount: undefined,
        totalOrderCostAmount: undefined,
        totalOrderPlanProfitAmount: undefined,
        settlementFlag: undefined,
        settlementTime: undefined,
        totalOrderTransactionsNumber: undefined,
        totalOrderReturnNumber: undefined,
        totalOrderActualProfitAmount: undefined,
        deletedTime: undefined,
        note: undefined,
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
      this.title = "添加配货综合订单信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getComprehensiveOrderInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改配货综合订单信息";
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
          updateComprehensiveOrderInfo(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        createComprehensiveOrderInfo(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除配货综合订单编号为"' + row.comprehensiveOrderCode + '"的数据项?').then(function() {
          return deleteComprehensiveOrderInfo(id);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
    },
    /** 生成编码按钮操作 */
    handleAssignOrderInfoCode() {
      let list = this.$refs["tables"].selection;
      if (list.length === 0) {
        this.$modal.msgWarning("请选择综合订单条目");
        return;
      }
      this.$modal.confirm('是否确认生成配货综合订单编号为"' + list.map(v => v.comprehensiveOrderCode) + '"的订单明细编号?').then(function() {
          return assignOrderInfoCode(list.map(v => v.id));
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("获取成功");
        }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      // 处理查询参数
      let params = {...this.queryParams};
      params.pageNo = undefined;
      params.pageSize = undefined;
      this.$modal.confirm('是否确认导出所有配货综合订单信息数据项?').then(() => {
          this.exportLoading = true;
          return exportComprehensiveOrderInfoExcel(params);
        }).then(response => {
          this.$download.excel(response, '配货综合订单信息.xlsx');
          this.exportLoading = false;
        }).catch(() => {});
    },

    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "配货综合订单导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      exportComprehensiveOrderInfoImportTemplateExcel().then(response => {
        this.$download.excel(response, '配货综合订单导入模板.xlsx');
      });
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      console.log(response);
      if (response.code !== 0) {
        this.$modal.msgError(response.msg)
        return;
      }
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      // 拼接提示语
      let data = response.data;
      let text = '<br />导入失败数量：' + Object.keys(data.failureOrders).length;
      for (const orderNumber in data.failureOrders) {
        text += '<br />&nbsp;&nbsp;&nbsp;&nbsp;行号：' + orderNumber + '，' + data.failureOrders[orderNumber];
      }
      text += '<br />导入成功数量：' + Object.keys(data.createOrders).length;
      for (const orderNumber in data.createOrders) {
        text += '<br />&nbsp;&nbsp;&nbsp;&nbsp;行号：' + orderNumber + '，' + data.createOrders[orderNumber];
      }
      this.$alert(text, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }

  }
};
</script>
