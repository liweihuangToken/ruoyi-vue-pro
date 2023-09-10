<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="订单所属日期" prop="orderDate">
        <el-date-picker v-model="queryParams.orderDate" style="width: 260px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                        range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="下游名称" prop="downstreamName">
        <el-input v-model="queryParams.downstreamName" style="width: 260px" placeholder="请输入下游名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="下游简称" prop="downstreamAlias">
        <el-input v-model="queryParams.downstreamAlias" style="width: 260px" placeholder="请输入下游简称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="送货方式" prop="deliveryMethod">
        <el-select v-model="queryParams.deliveryMethod" style="width: 260px" placeholder="请输入送货方式" clearable size="small">
          <el-option v-for="dict in this.getDictDatas(DICT_TYPE.DISTRIBUTION_DELIVERY_METHOD)"
                     :key="dict.value" :label="dict.label" :value="dict.value"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-download" size="mini" @click="handleFacingObjectExport(true)" :loading="exportLoading"
                   v-hasPermi="['distribution:order-detail-info:export-facing-excel']">面向下游导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table border height="680" header-align="center" v-loading="loading" :data="list">
      <el-table-column label="序号" align="center" min-width="50px" type="index"/>
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-tabs v-model="activeName" type = "border-card" @tab-click="handleClick">
            <el-tab-pane label="今日订单" name="first">
              <el-table border height="480" header-align="center" v-loading="loading" :data="props.row.orderDetailInfoFacingObjectRespVOList">
                <el-table-column label="订单标签图" align="center" prop="content" min-width="100px">
                  <template v-slot="scope">
                    <image-preview v-if="scope.row.orderOnedimensionalCodePictureUrl" :src="scope.row.orderOnedimensionalCodePictureUrl" :width="'100px'"/>
                  </template>
                </el-table-column>
                <el-table-column label="上游名称" align="center" prop="upstreamName" min-width="50px"/>
                <el-table-column label="订单状态" align="center" prop="orderStatus" min-width="50px">
                  <template v-slot="scope">
                    <dict-tag :type="DICT_TYPE.DISTRIBUTION_ORDER_STATUS" :value="scope.row.orderStatus" />
                  </template>
                </el-table-column>
                <el-table-column label="订单销售金额(单位:元)" align="center" prop="orderSalesAmount" min-width="100px"/>
                <el-table-column label="订单成本金额(单位:元)" align="center" prop="orderCostAmount" min-width="100px"/>
                <el-table-column label="尺码" align="center" prop="size" />
                <el-table-column label="备注" align="center" prop="note" />
                <el-table-column label="订单计划利润金额(单位:元)" align="center" prop="orderPlanProfitAmount" min-width="130px"/>
              </el-table>
            </el-tab-pane>
            <el-tab-pane label="今日退单" name="second">
              <el-table border height="480" header-align="center" v-loading="loading" :data="props.row.orderDetailInfoFacingObjectRespVOList">
                <el-table-column label="订单标签图1" align="center" prop="content" min-width="100px">
                  <template v-slot="scope">
                    <image-preview v-if="scope.row.orderOnedimensionalCodePictureUrl" :src="scope.row.orderOnedimensionalCodePictureUrl" :width="'100px'"/>
                  </template>
                </el-table-column>
                <el-table-column label="上游名称1" align="center" prop="upstreamName" min-width="50px"/>
                <el-table-column label="订单状态1" align="center" prop="orderStatus" min-width="50px">
                  <template v-slot="scope">
                    <dict-tag :type="DICT_TYPE.DISTRIBUTION_ORDER_STATUS" :value="scope.row.orderStatus" />
                  </template>
                </el-table-column>
                <el-table-column label="订单销售金额1(单位:元)" align="center" prop="orderSalesAmount" min-width="100px"/>
                <el-table-column label="订单成本金额1(单位:元)" align="center" prop="orderCostAmount" min-width="100px"/>
                <el-table-column label="尺码1" align="center" prop="size" />
                <el-table-column label="备注1" align="center" prop="note" />
                <el-table-column label="订单计划利润金额1(单位:元)" align="center" prop="orderPlanProfitAmount" min-width="130px"/>
              </el-table>
            </el-tab-pane>
          </el-tabs>
        </template>
      </el-table-column>
      <el-table-column label="订单所属日期" align="center" prop="orderDate" min-width="100px"/>
      <el-table-column label="下游名称" align="center" prop="downstreamName" min-width="80px"/>
      <el-table-column label="下游简称" align="center" prop="downstreamAlias" min-width="80px"/>
      <el-table-column label="送货方式" align="center" prop="deliveryMethod" min-width="80px">
        <template v-slot="scope">
          <dict-tag :type="DICT_TYPE.DISTRIBUTION_DELIVERY_METHOD" :value="scope.row.deliveryMethod" />
        </template>
      </el-table-column>
      <el-table-column label="订单总数量" align="center" prop="totalOrderNumber" min-width="100px"/>
      <el-table-column label="订单销售总金额(单位:元)" align="center" prop="totalOrderSalesAmount" min-width="150px"/>
      <el-table-column label="订单成本总金额(单位:元)" align="center" prop="totalOrderCostAmount" min-width="150px"/>
      <el-table-column label="订单计划利润总金额(单位:元)" align="center" prop="totalOrderPlanProfitAmount" min-width="150px"/>
      <el-table-column label="下游地址" align="center" prop="downstreamAddress" min-width="250px"/>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

  </div>
</template>

<script>
import {
        getOrderDetailInfoPage,
        exportOrderDetailInfoFacingObjectExcel,
        getDownstreamOrderPage,
        } from "@/api/distribution/orderDetailInfo";
import ImagePreview from "@/components/ImagePreview";

export default {
  name: "DownstreamOrderDetailInfo",
  components: {
    ImagePreview
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
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        orderDate: [],
        downstreamName: null,
        downstreamAlias: null,
        deliveryMethod: null,
      },
      activeName: 'first'
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
      getDownstreamOrderPage(this.queryParams).then(response => {
        this.list = response.data.list;
        // 设置默认搜索栏所属日期
        if((this.list != undefined && this.list.length > 0)
        && this.queryParams.orderDate != undefined && this.queryParams.orderDate.length === 0){
        	var date = this.list[0].orderDate + " 00:00:00";
          this.queryParams.orderDate = [date, date];
        }
        this.total = response.data.total;
        this.loading = false;
      });
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
    }
  }
};
</script>
