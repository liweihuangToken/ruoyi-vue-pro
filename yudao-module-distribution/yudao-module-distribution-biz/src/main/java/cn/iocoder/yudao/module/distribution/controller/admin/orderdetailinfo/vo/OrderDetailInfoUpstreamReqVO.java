package cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 供应商配货订单明细 Response VO")
@Data
@ToString(callSuper = true)
public class OrderDetailInfoUpstreamReqVO extends PageParam {

    @Schema(description = "供应商名称")
    private String upstreamName;

    @Schema(description = "供应商别名")
    private String upstreamAlias;

    @Schema(description = "供应商地址")
    private String upstreamAddress;

    @Schema(description = "取货方式")
    private Byte pickupMethod;

    @Schema(description = "订单所属日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDate[] orderDate;

    @Schema(description = "订单总数量")
    private Integer totalOrderNumber;

    @Schema(description = "订单销售总金额")
    private BigDecimal totalOrderSalesAmount;

    @Schema(description = "订单成本总金额")
    private BigDecimal totalOrderCostAmount;

    @Schema(description = "订单计划利润总金额")
    private BigDecimal totalOrderPlanProfitAmount;

    @Schema(description = "尺码")
    private String size;

    @Schema(description = "订单状态")
    private Byte orderStatus;

}
