package cn.iocoder.yudao.module.distribution.controller.admin.orderstatustrackinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 配货订单状态跟踪 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class OrderStatusTrackInfoBaseVO {

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "订单编号不能为空")
    private String orderCode;

    @Schema(description = "订单变更后状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "订单变更后状态不能为空")
    private Byte orderAfterChangeStatus;

}
