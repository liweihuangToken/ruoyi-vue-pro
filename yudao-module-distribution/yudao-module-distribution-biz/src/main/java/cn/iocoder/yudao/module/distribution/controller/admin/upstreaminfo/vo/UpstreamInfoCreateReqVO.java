package cn.iocoder.yudao.module.distribution.controller.admin.upstreaminfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 配货上游信息创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UpstreamInfoCreateReqVO extends UpstreamInfoBaseVO {

}
