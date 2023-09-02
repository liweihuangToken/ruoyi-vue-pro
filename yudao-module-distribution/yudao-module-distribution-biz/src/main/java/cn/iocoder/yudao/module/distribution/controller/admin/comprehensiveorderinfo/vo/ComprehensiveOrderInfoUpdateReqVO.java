package cn.iocoder.yudao.module.distribution.controller.admin.comprehensiveorderinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 配货综合订单信息更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ComprehensiveOrderInfoUpdateReqVO extends ComprehensiveOrderInfoBaseVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "1231")
    @NotNull(message = "主键不能为空")
    private Long id;

}
