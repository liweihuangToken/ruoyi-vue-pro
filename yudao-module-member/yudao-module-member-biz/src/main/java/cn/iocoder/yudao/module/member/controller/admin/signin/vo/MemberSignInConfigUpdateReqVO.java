package cn.iocoder.yudao.module.member.controller.admin.signin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 积分签到规则更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberSignInConfigUpdateReqVO extends MemberSignInConfigBaseVO {

    @Schema(description = "规则自增主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "13653")
    @NotNull(message = "规则自增主键不能为空")
    private Integer id;

}
