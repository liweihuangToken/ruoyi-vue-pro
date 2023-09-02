package cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 配货订单明细更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderDetailInfoUpdateReqVO extends OrderDetailInfoBaseVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "9989")
    @NotNull(message = "主键不能为空")
    private Long id;

}
