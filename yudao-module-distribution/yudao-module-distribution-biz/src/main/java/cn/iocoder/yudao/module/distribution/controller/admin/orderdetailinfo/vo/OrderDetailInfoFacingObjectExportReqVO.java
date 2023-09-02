package cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 配货订单明细面向上下有 Excel 导出 Request VO，参数和 OrderDetailInfoPageReqVO 是一致的")
@Data
public class OrderDetailInfoFacingObjectExportReqVO extends  OrderDetailInfoExportReqVO{

    @Schema(description = "是否面向下游")
    private Boolean isFacingDownstream;

}