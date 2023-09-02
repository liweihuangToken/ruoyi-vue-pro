package cn.iocoder.yudao.module.distribution.controller.admin.upstreaminfo.vo;

import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 配货上游信息 Excel VO
 *
 * @author 李伟煌
 */
@Data
public class UpstreamInfoExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("上游编号")
    private String upstreamCode;

    @ExcelProperty("上游名称")
    private String upstreamName;

    @ExcelProperty("上游别名")
    private String upstreamAlias;

    @ExcelProperty("上游地址")
    private String upstreamAddress;

    @ExcelProperty("取货方式")
    private Byte pickupMethod;

    @ExcelProperty("备注")
    private String note;

    @ExcelProperty("创建时间")
    @ExcelIgnore
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime createTime;

    @ExcelProperty("取货顺序")
    private Integer pickupSort;

}
