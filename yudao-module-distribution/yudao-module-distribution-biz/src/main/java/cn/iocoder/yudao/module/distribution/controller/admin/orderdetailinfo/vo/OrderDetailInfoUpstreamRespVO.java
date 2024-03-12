package cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "管理后台 - 供应商配货订单明细 Response VO")
@Data
@ToString(callSuper = true)
public class OrderDetailInfoUpstreamRespVO {

    @Schema(description = "供应商名称")
    private String upstreamName;

    @Schema(description = "供应商别名")
    private String upstreamAlias;

    @Schema(description = "供应商地址")
    private String upstreamAddress;

    @Schema(description = "取货方式")
    private Byte pickupMethod;

    @Schema(description = "订单所属日期")
    private LocalDate orderDate;

    @Schema(description = "订单总数量")
    private Integer totalOrderNumber;

    @Schema(description = "订单销售总金额")
    private BigDecimal totalOrderSalesAmount;

    @Schema(description = "订单成本总金额")
    private BigDecimal totalOrderCostAmount;

    @Schema(description = "订单计划利润总金额")
    private BigDecimal totalOrderPlanProfitAmount;

    @Schema(description = "订单详细信息")
    private List<OrderDetailInfoFacingObjectRespVO> orderDetailInfoFacingObjectRespVOList;

}
