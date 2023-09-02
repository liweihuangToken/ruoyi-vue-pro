package cn.iocoder.yudao.module.distribution.controller.admin.downstreaminfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 配货下游信息创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DownstreamInfoCreateReqVO extends DownstreamInfoBaseVO {

}
