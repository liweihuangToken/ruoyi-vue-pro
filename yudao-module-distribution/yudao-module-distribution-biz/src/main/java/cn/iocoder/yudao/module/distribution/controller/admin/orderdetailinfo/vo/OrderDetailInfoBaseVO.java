package cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo;

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
 * 配货订单明细 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class OrderDetailInfoBaseVO {

    @Schema(description = "订单所属日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @NotNull(message = "订单所属日期不能为空")
    private LocalDate orderDate;

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单编号不能为空")
    private String orderCode;

    @Schema(description = "综合订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "综合订单编号不能为空")
    private String comprehensiveOrderCode;

    @Schema(description = "订单图片URL", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotNull(message = "订单图片URL不能为空")
    private String orderGoodsPictureUrl;

    @Schema(description = "订单一维码图片URL", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotNull(message = "订单一维码图片URL不能为空")
    private String orderOnedimensionalCodePictureUrl;

    @Schema(description = "订单销售金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单销售金额不能为空")
    private BigDecimal orderSalesAmount;

    @Schema(description = "订单成本金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单成本金额不能为空")
    private BigDecimal orderCostAmount;

    @Schema(description = "订单计划利润金额", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单计划利润金额不能为空")
    private BigDecimal orderPlanProfitAmount;

    @Schema(description = "上游名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotNull(message = "上游名称不能为空")
    private String upstreamName;

    @Schema(description = "下游名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotNull(message = "下游名称不能为空")
    private String downstreamName;

    @Schema(description = "订单状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "订单状态不能为空")
    private Byte orderStatus;

    @Schema(description = "订单实际利润金额")
    private BigDecimal orderActualProfitAmount;

    @Schema(description = "结算标志", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "结算标志不能为空")
    private Byte settlementFlag;

    @Schema(description = "结算时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime settlementTime;

    @Schema(description = "删除时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime deletedTime;

    @Schema(description = "备注")
    private String note;

    @Schema(description = "尺码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "尺码不能为空")
    private String size;

    @Schema(description = "创建顺序")
    private Integer createSort;

    @Schema(description = "获取编码")
    private String goodsCode;

    @Schema(description = "条形码图片URL", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotNull(message = "条形码图片URL")
    private String orderBarcodePictureUrl;

}
