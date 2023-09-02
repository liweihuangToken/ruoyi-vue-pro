package cn.iocoder.yudao.module.distribution.controller.admin.upstreaminfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 配货上游信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class UpstreamInfoBaseVO {

    @Schema(description = "上游编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "上游编号不能为空")
    private String upstreamCode;

    @Schema(description = "上游名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "上游名称不能为空")
    private String upstreamName;

    @Schema(description = "上游别名")
    private String upstreamAlias;

    @Schema(description = "上游地址", requiredMode = Schema.RequiredMode.REQUIRED)
    private String upstreamAddress;

    @Schema(description = "取货方式")
    private Byte pickupMethod;

    @Schema(description = "备注")
    private String note;

    @Schema(description = "取货顺序")
    private Integer pickupSort;

}
