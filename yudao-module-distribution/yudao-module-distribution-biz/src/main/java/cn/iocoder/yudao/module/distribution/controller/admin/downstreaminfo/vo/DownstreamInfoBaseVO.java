package cn.iocoder.yudao.module.distribution.controller.admin.downstreaminfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 配货下游信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DownstreamInfoBaseVO {

    @Schema(description = "下游编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "下游编号不能为空")
    private String downstreamCode;

    @Schema(description = "下游名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "下游名称不能为空")
    private String downstreamName;

    @Schema(description = "下游别名")
    private String downstreamAlias;

    @Schema(description = "下游地址", requiredMode = Schema.RequiredMode.REQUIRED)
    private String downstreamAddress;

    @Schema(description = "送货方式")
    private Byte deliveryMethod;

    @Schema(description = "备注")
    private String note;

    @Schema(description = "送货顺序")
    private Integer deliverySort;

}
