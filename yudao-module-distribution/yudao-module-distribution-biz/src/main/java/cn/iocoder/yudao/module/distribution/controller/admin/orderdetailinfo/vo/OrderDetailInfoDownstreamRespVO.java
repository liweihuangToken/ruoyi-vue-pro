package cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "管理后台 - 配货订单明细 Response VO")
@Data
@ToString(callSuper = true)
public class OrderDetailInfoDownstreamRespVO {

    @Schema(description = "下游名称")
    private String downstreamName;

    @Schema(description = "下游别名")
    private String downstreamAlias;

    @Schema(description = "下游地址")
    private String downstreamAddress;

    @Schema(description = "送货方式")
    private Byte deliveryMethod;

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

    @Schema(description = "尺码组")
    private List<String> sizeList;

    @Schema(description = "登记数量")
    private Integer registrationCount;

    @Schema(description = "入库数量")
    private Integer warehousingCount;

    @Schema(description = "未到货数量")
    private Integer notWarehousingCount;

}
