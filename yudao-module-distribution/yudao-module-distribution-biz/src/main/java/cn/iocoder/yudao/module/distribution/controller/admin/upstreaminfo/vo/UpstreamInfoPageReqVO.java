package cn.iocoder.yudao.module.distribution.controller.admin.upstreaminfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 配货上游信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UpstreamInfoPageReqVO extends PageParam {

    @Schema(description = "上游编号")
    private String upstreamCode;

    @Schema(description = "上游名称", example = "王五")
    private String upstreamName;

    @Schema(description = "上游别名")
    private String upstreamAlias;

    @Schema(description = "上游地址")
    private String upstreamAddress;

    @Schema(description = "取货方式")
    private Byte pickupMethod;

    @Schema(description = "备注")
    private String note;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "取货顺序")
    private Integer pickupSort;

}
