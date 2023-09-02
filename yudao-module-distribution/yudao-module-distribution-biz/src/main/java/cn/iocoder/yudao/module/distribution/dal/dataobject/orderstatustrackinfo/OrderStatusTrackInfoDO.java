package cn.iocoder.yudao.module.distribution.dal.dataobject.orderstatustrackinfo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 配货订单状态跟踪 DO
 *
 * @author 李伟煌
 */
@TableName("distribution_order_status_track_info")
@KeySequence("distribution_order_status_track_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusTrackInfoDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 订单编号
     */
    private String orderCode;
    /**
     * 订单变更后状态
     */
    private Byte orderAfterChangeStatus;

}
