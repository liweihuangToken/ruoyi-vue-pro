package cn.iocoder.yudao.module.distribution.controller.admin.comprehensiveorderinfo.vo;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 配货综合订单信息 Excel 导出 Request VO，参数和 ComprehensiveOrderInfoPageReqVO 是一致的")
@Data
public class ComprehensiveOrderInfoExportReqVO {

    @Schema(description = "订单所属日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDate[] orderDate;

    @Schema(description = "综合订单编号")
    private String comprehensiveOrderCode;

    @Schema(description = "综合订单文件名称", example = "张三")
    private String comprehensiveOrderFileName;

    @Schema(description = "综合订单文件URL", example = "https://www.iocoder.cn")
    private String comprehensiveOrderFileUrl;

    @Schema(description = "订单总数量")
    private Integer totalOrderNumber;

    @Schema(description = "订单销售总金额")
    private BigDecimal totalOrderSalesAmount;

    @Schema(description = "订单成本总金额")
    private BigDecimal totalOrderCostAmount;

    @Schema(description = "订单计划利润总金额")
    private BigDecimal totalOrderPlanProfitAmount;

    @Schema(description = "结算标志")
    private Byte settlementFlag;

    @Schema(description = "结算时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] settlementTime;

    @Schema(description = "订单成交总数量")
    private Integer totalOrderTransactionsNumber;

    @Schema(description = "订单退货总数量")
    private Integer totalOrderReturnNumber;

    @Schema(description = "订单实际利润总金额")
    private BigDecimal totalOrderActualProfitAmount;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] deletedTime;

    @Schema(description = "备注")
    private String note;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
