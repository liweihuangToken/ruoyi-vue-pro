package cn.iocoder.yudao.module.distribution.dal.dataobject.upstreaminfo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 配货上游信息 DO
 *
 * @author 李伟煌
 */
@TableName("distribution_upstream_info")
@KeySequence("distribution_upstream_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpstreamInfoDO extends BaseDO {

    /**
     * 主键
     */
    @TableId
    private Long id;
    /**
     * 上游编号
     */
    private String upstreamCode;
    /**
     * 上游名称
     */
    private String upstreamName;
    /**
     * 上游别名
     */
    private String upstreamAlias;
    /**
     * 上游地址
     */
    private String upstreamAddress;
    /**
     * 取货方式
     */
    private Byte pickupMethod;
    /**
     * 备注
     */
    private String note;
    /**
     * 取货顺序
     */
    private Integer pickupSort;

}
