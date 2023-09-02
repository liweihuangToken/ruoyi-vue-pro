package cn.iocoder.yudao.module.distribution.controller.admin.downstreaminfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 配货下游信息 Excel VO
 *
 * @author 李伟煌
 */
@Data
public class DownstreamInfoExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("下游编号")
    private String downstreamCode;

    @ExcelProperty("下游名称")
    private String downstreamName;

    @ExcelProperty("下游别名")
    private String downstreamAlias;

    @ExcelProperty("下游地址")
    private String downstreamAddress;

    @ExcelProperty("送货方式")
    private Byte deliveryMethod;

    @ExcelProperty("备注")
    private String note;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("送货顺序")
    private Integer deliverySort;

}
