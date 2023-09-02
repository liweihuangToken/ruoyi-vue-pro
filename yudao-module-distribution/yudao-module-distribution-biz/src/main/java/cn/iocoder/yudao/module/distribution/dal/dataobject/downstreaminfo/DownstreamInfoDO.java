package cn.iocoder.yudao.module.distribution.dal.dataobject.downstreaminfo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 配货下游信息 DO
 *
 * @author 李伟煌
 */
@TableName("distribution_downstream_info")
@KeySequence("distribution_downstream_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DownstreamInfoDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 下游编号
     */
    private String downstreamCode;
    /**
     * 下游名称
     */
    private String downstreamName;
    /**
     * 下游别名
     */
    private String downstreamAlias;
    /**
     * 下游地址
     */
    private String downstreamAddress;
    /**
     * 送货方式
     */
    private Byte deliveryMethod;
    /**
     * 备注
     */
    private String note;
    /**
     * 送货顺序
     */
    private Integer deliverySort;

}
