package cn.iocoder.yudao.module.distribution.dal.dataobject.comprehensiveorderinfo;

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

/**
 * 配货综合订单信息 DO
 *
 * @author 李伟煌
 */
@TableName("distribution_comprehensive_order_info")
@KeySequence("distribution_comprehensive_order_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComprehensiveOrderInfoDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 订单所属日期
     */
    private LocalDate orderDate;
    /**
     * 综合订单编号
     */
    private String comprehensiveOrderCode;
    /**
     * 综合订单文件名称
     */
    private String comprehensiveOrderFileName;
    /**
     * 综合订单文件URL
     */
    private String comprehensiveOrderFileUrl;
    /**
     * 订单总数量
     */
    private Integer totalOrderNumber;
    /**
     * 订单销售总金额
     */
    private BigDecimal totalOrderSalesAmount;
    /**
     * 订单成本总金额
     */
    private BigDecimal totalOrderCostAmount;
    /**
     * 订单计划利润总金额
     */
    private BigDecimal totalOrderPlanProfitAmount;
    /**
     * 结算标志
     */
    private Byte settlementFlag;
    /**
     * 结算时间
     */
    private LocalDateTime settlementTime;
    /**
     * 订单成交总数量
     */
    private Integer totalOrderTransactionsNumber;
    /**
     * 订单退货总数量
     */
    private Integer totalOrderReturnNumber;
    /**
     * 订单实际利润总金额
     */
    private BigDecimal totalOrderActualProfitAmount;
    /**
     * 删除时间
     */
    private LocalDateTime deletedTime;
    /**
     * 备注
     */
    private String note;

}
