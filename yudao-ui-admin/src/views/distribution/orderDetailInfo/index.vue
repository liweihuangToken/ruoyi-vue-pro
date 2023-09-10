<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="订单所属日期" prop="orderDate">
        <el-date-picker v-model="queryParams.orderDate" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="订单编号" prop="orderCode">
        <el-input v-model="queryParams.orderCode" style="width: 240px" placeholder="请输入订单编号" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="综合订单编号" prop="comprehensiveOrderCode">
        <el-input v-model="queryParams.comprehensiveOrderCode" style="width: 240px" placeholder="请输入综合订单编号" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <!-- <el-form-item label="订单图片URL" prop="orderGoodsPictureUrl">
        <el-input v-model="queryParams.orderGoodsPictureUrl" placeholder="请输入订单图片URL" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="订单一维码图片URL" prop="orderOnedimensionalCodePictureUrl">
        <el-input v-model="queryParams.orderOnedimensionalCodePictureUrl" placeholder="请输入订单一维码图片URL" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="订单销售金额" prop="orderSalesAmount">
        <el-input v-model="queryParams.orderSalesAmount" placeholder="请输入订单销售金额" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="订单成本金额" prop="orderCostAmount">
        <el-input v-model="queryParams.orderCostAmount" placeholder="请输入订单成本金额" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="订单计划利润金额" prop="orderPlanProfitAmount">
        <el-input v-model="queryParams.orderPlanProfitAmount" placeholder="请输入订单计划利润金额" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item> -->
      <el-form-item label="上游名称" prop="upstreamName">
        <el-input v-model="queryParams.upstreamName" style="width: 240px" placeholder="请输入上游名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="下游名称" prop="downstreamName">
        <el-input v-model="queryParams.downstreamName" style="width: 240px" placeholder="请输入下游名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="订单状态" prop="orderStatus">
        <el-select v-model="queryParams.orderStatus" style="width: 240px" placeholder="请选择订单状态" clearable size="small">
          <el-option v-for="dict in this.getDictDatas(DICT_TYPE.DISTRIBUTION_ORDER_STATUS)"
                     :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="订单实际利润金额" prop="orderActualProfitAmount">
        <el-input v-model="queryParams.orderActualProfitAmount" placeholder="请输入订单实际利润金额" clearable @keyup.enter.native="handleQuery"/>
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
      <!-- <el-form-item label="删除时间" prop="deletedTime">
        <el-date-picker v-model="queryParams.deletedTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item> -->
      <!-- <el-form-item label="备注" prop="note">
        <el-input v-model="queryParams.note" placeholder="请输入备注" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item> -->
      <el-form-item label="尺码" prop="size">
        <el-input v-model="queryParams.size" style="width: 240px" placeholder="请输入尺码" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
     <!-- <el-form-item label="创建时间" prop="createTime">
        <el-date-picker v-model="queryParams.createTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="创建顺序" prop="createSort">
        <el-input v-model="queryParams.createSort" placeholder="请输入创建顺序" clearable @keyup.enter.native="handleQuery"/>
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
                   v-hasPermi="['distribution:order-detail-info:create']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['distribution:order-detail-info:export']">导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-download" size="mini" @click="handleLableExport" :loading="exportLoading"
                   v-hasPermi="['distribution:order-detail-info:export-lable-excel']">标签导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-download" size="mini" @click="handleFacingObjectExport(true)" :loading="exportLoading"
                   v-hasPermi="['distribution:order-detail-info:export-facing-excel']">面向下游导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-download" size="mini" @click="handleFacingObjectExport(false)" :loading="exportLoading"
                   v-hasPermi="['distribution:order-detail-info:export-facing-excel']">面向上游导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table border height="680" header-align="center" v-loading="loading" :data="list">
      <!-- <el-table-column label="主键" align="center" prop="id" /> -->
      <el-table-column label="序号" align="center" min-width="50px" fixed="left" type="index"/>
      <el-table-column label="订单所属日期" align="center" prop="orderDate" min-width="100px" fixed="left"/>
      <el-table-column label="下游名称" align="center" prop="downstreamName" min-width="80px" fixed="left"/>
      <el-table-column label="上游名称" align="center" prop="upstreamName" min-width="80px" fixed="left"/>
      <el-table-column label="订单编号" align="center" prop="orderCode" min-width="150px" fixed="left"/>
      <el-table-column label="订单图片" align="center" prop="content" min-width="130px" fixed="left">
        <template v-slot="scope">
          <image-preview v-if="scope.row.orderGoodsPictureUrl" :src="scope.row.orderGoodsPictureUrl" :width="'100px'"></image-preview>
        </template>
      </el-table-column>
      <el-table-column label="订单状态" align="center" prop="orderStatus" min-width="100px" fixed="left">
        <template v-slot="scope">
          <dict-tag :type="DICT_TYPE.DISTRIBUTION_ORDER_STATUS" :value="scope.row.orderStatus" />
        </template>
      </el-table-column>
      <el-table-column label="订单标签图" align="center" prop="content" min-width="150px">
        <template v-slot="scope">
          <image-preview v-if="scope.row.orderOnedimensionalCodePictureUrl" :src="scope.row.orderOnedimensionalCodePictureUrl" :width="'100px'"></image-preview>
        </template>
      </el-table-column>
      <el-table-column label="订单销售金额(单位:元)" align="center" prop="orderSalesAmount" min-width="100px"/>
      <el-table-column label="订单成本金额(单位:元)" align="center" prop="orderCostAmount" min-width="100px"/>
      <el-table-column label="尺码" align="center" prop="size" />
      <el-table-column label="备注" align="center" prop="note" />
      <el-table-column label="订单计划利润金额(单位:元)" align="center" prop="orderPlanProfitAmount" min-width="130px"/>
      <el-table-column label="订单实际利润金额(单位:元)" align="center" prop="orderActualProfitAmount" min-width="130px"/>
      <el-table-column label="综合订单编号" align="center" prop="comprehensiveOrderCode" min-width="150px"/>
      <el-table-column label="结算标志" align="center" prop="settlementFlag" min-width="80px">
        <template v-slot="scope">
          <dict-tag :type="DICT_TYPE.DISTRIBUTION_SETTLEMENT_FLAG" :value="scope.row.settlementFlag" />
        </template>
      </el-table-column>
      <el-table-column label="结算时间" align="center" prop="settlementTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.settlementTime) }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="删除时间" align="center" prop="deletedTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.deletedTime) }}</span>
        </template>
      </el-table-column> -->
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建顺序" align="center" prop="createSort" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" min-width="150px" fixed="right">
        <template v-slot="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                     v-hasPermi="['distribution:order-detail-info:update']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['distribution:order-detail-info:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="580px" v-dialogDrag append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="订单所属日期" prop="orderDate">
          <el-date-picker clearable v-model="form.orderDate" type="date" placeholder="选择订单所属日期" :disabled = "isEdit"/>
        </el-form-item>
        <el-form-item label="上游名称" prop="upstreamName">
          <el-input v-model="form.upstreamName" placeholder="请输入上游名称" />
        </el-form-item>
        <el-form-item label="下游名称" prop="downstreamName">
          <el-input v-model="form.downstreamName" placeholder="请输入下游名称" />
        </el-form-item>
        <el-form-item label="订单编号" prop="orderCode">
          <el-input v-model="form.orderCode" placeholder="请输入订单编号" :disabled="isEdit"/>
        </el-form-item>
        <el-form-item label="订单图片" prop="orderGoodsPictureUrl">
          <!-- <image-preview v-if="form.orderGoodsPictureUrl" :src="form.orderGoodsPictureUrl" :width="'250px'"/> -->
          <div class="order-info-head" @click="editCropper(form.orderGoodsPictureUrl)">
            <img v-bind:src="form.orderGoodsPictureUrl" title="点击上传订单图片" :width="'250px'" :height="'180px'"/>
          </div>
        </el-form-item>
        <el-form-item label="订单标签图" prop="orderOnedimensionalCodePictureUrl">
          <!-- <el-input v-model="form.orderOnedimensionalCodePictureUrl" placeholder="请输入订单一维码图片URL" /> -->
            <image-preview v-if="form.orderOnedimensionalCodePictureUrl" :src="form.orderOnedimensionalCodePictureUrl" :width="'250px'"/>
        </el-form-item>
        <el-form-item label="订单状态" prop="orderStatus">
          <el-select v-model="form.orderStatus" placeholder="请选择订单状态">
            <el-option v-for="dict in this.getDictDatas(DICT_TYPE.DISTRIBUTION_ORDER_STATUS)" :key="parseInt(dict.value)" :label="dict.label" :value="parseInt(dict.value)"/>
          </el-select>
        </el-form-item>
        <el-form-item label="订单销售金额" prop="orderSalesAmount">
          <el-input v-model="form.orderSalesAmount" placeholder="请输入订单销售金额" type="number"/>
        </el-form-item>
        <el-form-item label="订单成本金额" prop="orderCostAmount">
          <el-input v-model="form.orderCostAmount" placeholder="请输入订单成本金额" type="number"/>
        </el-form-item>
        <el-form-item label="尺码" prop="size">
          <el-input v-model="form.size" placeholder="请输入尺码" />
        </el-form-item>
        <el-form-item label="备注" prop="note">
          <el-input v-model="form.note" placeholder="请输入备注" />
        </el-form-item>
        <el-form-item label="综合订单编号" prop="comprehensiveOrderCode">
          <el-input v-model="form.comprehensiveOrderCode" placeholder="请输入综合订单编号" :disabled="isEdit"/>
        </el-form-item>
        <el-form-item label="订单计划利润金额" prop="orderPlanProfitAmount">
          <el-input v-model="form.orderPlanProfitAmount" placeholder="请输入订单计划利润金额" :disabled="isEdit"/>
        </el-form-item>
        <el-form-item label="订单实际利润金额" prop="orderActualProfitAmount">
          <el-input v-model="form.orderActualProfitAmount" placeholder="请输入订单实际利润金额"/>
        </el-form-item>
        <el-form-item label="结算标志" prop="settlementFlag">
          <el-select v-model="form.settlementFlag" placeholder="请选择订单状态" :disabled="isEdit">
            <el-option v-for="dict in this.getDictDatas(DICT_TYPE.DISTRIBUTION_SETTLEMENT_FLAG)" :key="parseInt(dict.value)" :label="dict.label" :value="parseInt(dict.value)"/>
          </el-select>
        </el-form-item>
        <el-form-item label="结算时间" prop="settlementTime">
          <el-date-picker clearable v-model="form.settlementTime" type="date" value-format="timestamp" placeholder="选择结算时间" :disabled="isEdit"/>
        </el-form-item>
        <el-form-item label="删除时间" prop="deletedTime">
          <el-date-picker clearable v-model="form.deletedTime" type="date" value-format="timestamp" placeholder="选择删除时间" :disabled="isEdit"/>
        </el-form-item>
        <el-form-item label="创建顺序" prop="createSort">
          <el-input v-model="form.createSort" placeholder="请输入创建顺序" :disabled="isEdit"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 订单图片上传修改 -->
    <el-dialog :title="editOrderPictruetitle" :visible.sync="openPictrue" width="500px" append-to-body @opened="modalOpened" @close="closeDialog()">
      <el-row>
        <el-col :style="{height: '500px'}">
          <vue-cropper
            ref="cropper"
            :img="options.img"
            :info="true"
            :autoCrop="options.autoCrop"
            :autoCropWidth="options.autoCropWidth"
            :autoCropHeight="options.autoCropHeight"
            :fixedBox="options.fixedBox"
            v-if="visible"
          />
        </el-col>
      </el-row>
      <br />
      <el-row>
        <el-col :lg="1" :md="2">
          <el-upload action="#" :http-request="requestUpload" :show-file-list="false" :before-upload="beforeUpload">
            <el-button size="small">
              选择
              <i class="el-icon-upload el-icon--right"></i>
            </el-button>
          </el-upload>
        </el-col>
        <el-col :lg="{span: 1, offset: 4}" :md="2">
          <el-button icon="el-icon-plus" size="small" @click="changeScale(1)"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 3}" :md="2">
          <el-button icon="el-icon-minus" size="small" @click="changeScale(-1)"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 3}" :md="2">
          <el-button icon="el-icon-refresh-left" size="small" @click="rotateLeft()"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 3}" :md="2">
          <el-button icon="el-icon-refresh-right" size="small" @click="rotateRight()"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 3}" :md="2">
          <el-button type="primary" size="small" @click="uploadImg()">提 交</el-button>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import { createOrderDetailInfo,
        updateOrderDetailInfo,
        deleteOrderDetailInfo,
        getOrderDetailInfo,
        getOrderDetailInfoPage,
        updateOrderPictrue,
        exportOrderDetailInfoExcel,
        exportOrderDetailInfoLableExcel,
        exportOrderDetailInfoFacingObjectExcel,
        } from "@/api/distribution/orderDetailInfo";
import ImagePreview from "@/components/ImagePreview";
import store from "@/store";
import { VueCropper } from "vue-cropper";

export default {
  name: "OrderDetailInfo",
  components: {
    ImagePreview,
    VueCropper
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
      // 配货订单明细列表
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 不可编辑
      isEdit: true,
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        orderDate: [],
        orderCode: null,
        comprehensiveOrderCode: null,
        orderGoodsPictureUrl: null,
        orderOnedimensionalCodePictureUrl: null,
        orderSalesAmount: null,
        orderCostAmount: null,
        orderPlanProfitAmount: null,
        upstreamName: null,
        downstreamName: null,
        orderStatus: null,
        orderActualProfitAmount: null,
        settlementFlag: null,
        settlementTime: [],
        deletedTime: [],
        note: null,
        size: null,
        createTime: [],
        createSort: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderDate: [{ required: true, message: "订单所属日期不能为空", trigger: "blur" }],
        orderCode: [{ required: true, message: "订单编号不能为空", trigger: "blur" }],
        comprehensiveOrderCode: [{ required: true, message: "综合订单编号不能为空", trigger: "blur" }],
        orderGoodsPictureUrl: [{ required: true, message: "订单图片URL不能为空", trigger: "blur" }],
        orderOnedimensionalCodePictureUrl: [{ required: true, message: "订单一维码图片URL不能为空", trigger: "blur" }],
        orderSalesAmount: [{ required: true, message: "订单销售金额不能为空", trigger: "blur" }],
        orderCostAmount: [{ required: true, message: "订单成本金额不能为空", trigger: "blur" }],
        orderPlanProfitAmount: [{ required: true, message: "订单计划利润金额不能为空", trigger: "blur" }],
        upstreamName: [{ required: true, message: "上游名称不能为空", trigger: "blur" }],
        downstreamName: [{ required: true, message: "下游名称不能为空", trigger: "blur" }],
        orderStatus: [{ required: true, message: "订单状态不能为空", trigger: "blur" }],
        settlementFlag: [{ required: true, message: "结算标志不能为空", trigger: "blur" }],
        size: [{ required: true, message: "尺码不能为空", trigger: "blur" }],
      },
      // 是否显示cropper
      editOrderPictruetitle: "编辑订单图片",
      openPictrue: false,
      visible: false,
      options: {
        img: store.getters.avatar, //裁剪图片的地址
        autoCrop: true, // 是否默认生成截图框
        autoCropWidth: 250, // 默认生成截图框宽度
        autoCropHeight: 180, // 默认生成截图框高度
        fixedBox: false // 固定截图框大小 不允许改变
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
      getOrderDetailInfoPage(this.queryParams).then(response => {
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
        orderCode: undefined,
        comprehensiveOrderCode: undefined,
        orderGoodsPictureUrl: undefined,
        orderOnedimensionalCodePictureUrl: undefined,
        orderSalesAmount: undefined,
        orderCostAmount: undefined,
        orderPlanProfitAmount: undefined,
        upstreamName: undefined,
        downstreamName: undefined,
        orderStatus: undefined,
        orderActualProfitAmount: undefined,
        settlementFlag: undefined,
        settlementTime: undefined,
        deletedTime: undefined,
        note: undefined,
        size: undefined,
        createSort: undefined,
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
      this.title = "添加配货订单明细";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getOrderDetailInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改配货订单明细";
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
          updateOrderDetailInfo(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        createOrderDetailInfo(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除配货订单明细编号为"' + id + '"的数据项?').then(function() {
          return deleteOrderDetailInfo(id);
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
      this.$modal.confirm('是否确认导出所有配货订单明细数据项?').then(() => {
          this.exportLoading = true;
          return exportOrderDetailInfoExcel(params);
        }).then(response => {
          this.$download.excel(response, '配货订单明细.xls');
          this.exportLoading = false;
        }).catch(() => {});
    },
    /** 导出标签按钮操作 */
    handleLableExport() {
      // 处理查询参数
      let params = {...this.queryParams};
      params.pageNo = undefined;
      params.pageSize = undefined;
      this.$modal.confirm('是否确认导出所有配货订单标签Excel?').then(() => {
          this.exportLoading = true;
          return exportOrderDetailInfoLableExcel(params);
        }).then(response => {
          this.$download.excel(response, '配货订单明细标签.xls');
          this.exportLoading = false;
        }).catch(() => {});
    },

    /** 面向上下游导出订单按钮操作 */
    handleFacingObjectExport(flag) {
      // 处理查询参数
      let params = {...this.queryParams};
      params.pageNo = undefined;
      params.pageSize = undefined;
      params.isFacingDownstream = flag;
      this.$modal.confirm('是否确认面向' + (flag?'下游':'上游') + '导出所有配货订单标签Excel?').then(() => {
          this.exportLoading = true;
          return exportOrderDetailInfoFacingObjectExcel(params);
        }).then(response => {
          this.$download.excel(response, '面向' + (flag?'下游':'上游') + '配货订单明细标签.xls');
          this.exportLoading = false;
        }).catch(() => {});
    },

    // 编辑订单图片
    editCropper(url) {
      this.options.img = url;
      this.openPictrue = true;
    },
    // 打开弹出层结束时的回调
    modalOpened() {
      this.visible = true;
    },
    // 覆盖默认的上传行为
    requestUpload() {
    },
    // 向左旋转
    rotateLeft() {
      this.$refs.cropper.rotateLeft();
    },
    // 向右旋转
    rotateRight() {
      this.$refs.cropper.rotateRight();
    },
    // 图片缩放
    changeScale(num) {
      num = num || 1;
      this.$refs.cropper.changeScale(num);
    },
    // 上传预处理
    beforeUpload(file) {
      if (file.type.indexOf("image/") === -1) {
        this.$modal.msgError("文件格式错误，请上传图片类型,如：JPG，PNG后缀的文件。");
      } else {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          this.options.img = reader.result;
        };
      }
    },
    // 上传图片
    uploadImg() {
      this.$refs.cropper.getCropBlob(data => {
        let formData = new FormData();
        formData.append("file", data);
        updateOrderPictrue(formData).then(resp => {
          this.openPictrue = false;
          this.options.img = process.env.VUE_APP_BASE_API + resp.data;
          this.$modal.msgSuccess("修改成功");
          this.visible = false;
          console.log(resp.data);
          this.form.orderGoodsPictureUrl = resp.data;
        });
      });
    },
    // 取消截图，关闭对话框
    closeDialog() {
      this.options.img = store.getters.avatar
    }
  }
};
</script>

<style scoped lang="scss">
.order-info-head {
  position: relative;
  display: inline-block;
  height: 180px;
}

.order-info-head:hover:after {
  content: '点击图片进行图片更换';
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  color: #eee;
  background: rgba(0, 0, 0, 0.5);
  font-size: 25px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  cursor: pointer;
  line-height: 180px;
  // border-radius: 50%;
}
</style>

