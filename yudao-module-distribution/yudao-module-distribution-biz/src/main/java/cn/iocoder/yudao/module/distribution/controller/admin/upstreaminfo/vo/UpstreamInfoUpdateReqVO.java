package cn.iocoder.yudao.module.distribution.controller.admin.upstreaminfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 配货上游信息更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UpstreamInfoUpdateReqVO extends UpstreamInfoBaseVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "6077")
    @NotNull(message = "主键不能为空")
    private Long id;

}
