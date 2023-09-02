package cn.iocoder.yudao.module.distribution.controller.admin.orderstatustrackinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 配货订单状态跟踪创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderStatusTrackInfoCreateReqVO extends OrderStatusTrackInfoBaseVO {

}
