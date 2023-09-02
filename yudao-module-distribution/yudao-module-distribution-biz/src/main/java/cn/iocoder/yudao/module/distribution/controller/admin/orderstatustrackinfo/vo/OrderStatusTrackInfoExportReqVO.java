package cn.iocoder.yudao.module.distribution.controller.admin.orderstatustrackinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 配货订单状态跟踪 Excel 导出 Request VO，参数和 OrderStatusTrackInfoPageReqVO 是一致的")
@Data
public class OrderStatusTrackInfoExportReqVO {

    @Schema(description = "订单编号")
    private String orderCode;

    @Schema(description = "订单变更后状态", example = "1")
    private Byte orderAfterChangeStatus;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
