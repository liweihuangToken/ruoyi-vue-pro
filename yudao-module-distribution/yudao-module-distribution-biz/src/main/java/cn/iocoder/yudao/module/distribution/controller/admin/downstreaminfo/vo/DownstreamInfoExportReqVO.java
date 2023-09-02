package cn.iocoder.yudao.module.distribution.controller.admin.downstreaminfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 配货下游信息 Excel 导出 Request VO，参数和 DownstreamInfoPageReqVO 是一致的")
@Data
public class DownstreamInfoExportReqVO {

    @Schema(description = "下游编号")
    private String downstreamCode;

    @Schema(description = "下游名称", example = "王五")
    private String downstreamName;

    @Schema(description = "下游别名")
    private String downstreamAlias;

    @Schema(description = "下游地址")
    private String downstreamAddress;

    @Schema(description = "送货方式")
    private Byte deliveryMethod;

    @Schema(description = "备注")
    private String note;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "送货顺序")
    private Integer deliverySort;

}
