package cn.iocoder.yudao.module.distribution.controller.admin.comprehensiveorderinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 配货综合订单信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ComprehensiveOrderInfoBaseVO {

    @Schema(description = "订单所属日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单所属日期不能为空")
    private LocalDate orderDate;

    @Schema(description = "综合订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "综合订单编号不能为空")
    private String comprehensiveOrderCode;

    @Schema(description = "综合订单文件名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotNull(message = "综合订单文件名称不能为空")
    private String comprehensiveOrderFileName;

    @Schema(description = "综合订单文件URL", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotNull(message = "综合订单文件URL不能为空")
    private String comprehensiveOrderFileUrl;

    @Schema(description = "订单总数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单总数量不能为空")
    private Integer totalOrderNumber;

    @Schema(description = "订单销售总金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单销售总金额不能为空")
    private BigDecimal totalOrderSalesAmount;

    @Schema(description = "订单成本总金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单成本总金额不能为空")
    private BigDecimal totalOrderCostAmount;

    @Schema(description = "订单计划利润总金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单计划利润总金额不能为空")
    private BigDecimal totalOrderPlanProfitAmount;

    @Schema(description = "结算标志", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "结算标志不能为空")
    private Byte settlementFlag;

    @Schema(description = "结算时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime settlementTime;

    @Schema(description = "订单成交总数量")
    private Integer totalOrderTransactionsNumber;

    @Schema(description = "订单退货总数量")
    private Integer totalOrderReturnNumber;

    @Schema(description = "订单实际利润总金额")
    private BigDecimal totalOrderActualProfitAmount;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime deletedTime;

    @Schema(description = "备注")
    private String note;

}
