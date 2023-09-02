package cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 配货订单明细 Excel 导出 Request VO，参数和 OrderDetailInfoPageReqVO 是一致的")
@Data
public class OrderDetailInfoExportReqVO {

    @Schema(description = "订单所属日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDate[] orderDate;

    @Schema(description = "订单编号")
    private String orderCode;

    @Schema(description = "综合订单编号")
    private String comprehensiveOrderCode;

    @Schema(description = "订单图片URL", example = "https://www.iocoder.cn")
    private String orderGoodsPictureUrl;

    @Schema(description = "订单一维码图片URL", example = "https://www.iocoder.cn")
    private String orderOnedimensionalCodePictureUrl;

    @Schema(description = "订单销售金额")
    private BigDecimal orderSalesAmount;

    @Schema(description = "订单成本金额")
    private BigDecimal orderCostAmount;

    @Schema(description = "订单计划利润金额")
    private BigDecimal orderPlanProfitAmount;

    @Schema(description = "上游名称", example = "芋艿")
    private String upstreamName;

    @Schema(description = "下游名称", example = "赵六")
    private String downstreamName;

    @Schema(description = "订单状态", example = "1")
    private Byte orderStatus;

    @Schema(description = "订单实际利润金额")
    private BigDecimal orderActualProfitAmount;

    @Schema(description = "结算标志")
    private Byte settlementFlag;

    @Schema(description = "结算时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] settlementTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

    @Schema(description = "备注")
    private String note;

    @Schema(description = "尺码")
    private String size;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "创建顺序")
    private Integer createSort;

}
