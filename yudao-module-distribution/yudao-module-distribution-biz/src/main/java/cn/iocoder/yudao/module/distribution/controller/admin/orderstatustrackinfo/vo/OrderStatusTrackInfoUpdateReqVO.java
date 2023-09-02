package cn.iocoder.yudao.module.distribution.controller.admin.orderstatustrackinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 配货订单状态跟踪更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderStatusTrackInfoUpdateReqVO extends OrderStatusTrackInfoBaseVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "27744")
    @NotNull(message = "主键不能为空")
    private Long id;

}
