package cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 *  * 配货订单明细统计对象，提供给移动端入库退货展示 使用
 */
@Data
public class OrderDetailStatisticsInfoVO {

    @Schema(description = "订单总数量")
    private Integer orderTotalCount;

    @Schema(description = "入库订单总数")
    private Integer putStorageCount;

    @Schema(description = "无货订单总数")
    private Integer noPutStorageCount;

    @Schema(description = "待处理订单总数饿")
    private Integer untreatedCount;

    @Schema(description = "入库订单总金额")
    private BigDecimal putStorageOrderTotalAmont;
}
