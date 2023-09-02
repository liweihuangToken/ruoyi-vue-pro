package cn.iocoder.yudao.module.distribution.dal.dataobject.orderdetailinfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;

/**
 * 配货订单明细 DO
 *
 * @author 李伟煌
 */
@TableName("distribution_order_detail_info")
@KeySequence("distribution_order_detail_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailInfoDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 订单所属日期
     */
    @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY, timezone = TIME_ZONE_DEFAULT)
    private LocalDate orderDate;
    /**
     * 订单编号
     */
    private String orderCode;
    /**
     * 综合订单编号
     */
    private String comprehensiveOrderCode;
    /**
     * 订单图片URL
     */
    private String orderGoodsPictureUrl;
    /**
     * 订单一维码图片URL
     */
    private String orderOnedimensionalCodePictureUrl;
    /**
     * 订单销售金额
     */
    private BigDecimal orderSalesAmount;
    /**
     * 订单成本金额
     */
    private BigDecimal orderCostAmount;
    /**
     * 订单计划利润金额
     */
    private BigDecimal orderPlanProfitAmount;
    /**
     * 上游名称
     */
    private String upstreamName;
    /**
     * 下游名称
     */
    private String downstreamName;
    /**
     * 订单状态
     */
    private Byte orderStatus;
    /**
     * 订单实际利润金额
     */
    private BigDecimal orderActualProfitAmount;
    /**
     * 结算标志
     */
    private Byte settlementFlag;
    /**
     * 结算时间
     */
    private LocalDateTime settlementTime;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;
    /**
     * 备注
     */
    private String note;
    /**
     * 尺码
     */
    private String size;
    /**
     * 创建顺序
     */
    private Integer createSort;

}
