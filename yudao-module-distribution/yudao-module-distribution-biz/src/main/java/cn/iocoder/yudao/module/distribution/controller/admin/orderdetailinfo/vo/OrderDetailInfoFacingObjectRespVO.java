package cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo;

import cn.iocoder.yudao.module.distribution.dal.dataobject.orderstatustrackinfo.OrderStatusTrackInfoDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 配货订单明细 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OrderDetailInfoFacingObjectRespVO extends OrderDetailInfoBaseVO {

    @Schema(description = "主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "9989")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "取货方式")
    private Byte pickupMethod;

    @Schema(description = "送货方式")
    private Byte deliveryMethod;

    @Schema(description = "上游别名")
    private String upstreamAlias;

    @Schema(description = "下游别名")
    private String downstreamAlias;

    @Schema(description = "综合信息")
    private String totalInfo;

    @Schema(description = "英文价格")
    private String engPrice;

    @Schema(description = "状态跟踪信息")
    private List<OrderStatusTrackInfoDO> orderStatusTrackInfoDOList;

    @Schema(description = "订单明细统计信息")
    private OrderDetailStatisticsInfoVO orderDetailStatisticsInfoVO;

}
