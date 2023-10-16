package cn.iocoder.yudao.module.distribution.controller.admin.comprehensiveorderinfo.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import cn.iocoder.yudao.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.BorderStyleEnum;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 用户 Excel 导入 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@HeadRowHeight(35)
@HeadFontStyle(fontHeightInPoints = 12, bold = BooleanEnum.TRUE, fontName = "华文楷体")
@ContentRowHeight(50)
@ContentFontStyle(fontHeightInPoints = 12, fontName = "华文楷体")
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER,
        verticalAlignment = VerticalAlignmentEnum.CENTER,
        wrapped = BooleanEnum.TRUE,
        borderLeft = BorderStyleEnum.THIN,
        borderRight = BorderStyleEnum.THIN,
        borderTop = BorderStyleEnum.THIN,
        borderBottom = BorderStyleEnum.THIN
)
@Accessors(chain = false) // 设置 chain = false，避免综合订单导入有问题
public class ComprehensiveOrderImportExcelVO {

    @ExcelProperty("下游")
    @Excel(name = "下游") // 由于EasyExcel导入图片文档无具体描述，所以使用EeayPoi注解
    @ColumnWidth(8)
    private String downstreamName;

    @ExcelProperty("上游")
    @Excel(name = "上游")
    @ColumnWidth(8)
    private String upstreamName;

    @ExcelProperty("尺码")
    @Excel(name = "尺码")
    @ColumnWidth(17)
    private String size;

    @ExcelProperty(value = "送货方式", converter = DictConvert.class)
    @Excel(name = "送货方式", replace = {"自取_1","配送_2","代发_3"})
    @DictFormat(DictTypeConstants.DISTRIBUTION_DELIVERY_METHOD)
    @ColumnWidth(10)
    private Integer deliveryMethod;

    @ExcelProperty("图片")
    @Excel(name = "图片", type = 2, savePath = "www\\www\\picture")
    @ColumnWidth(12)
    private String picture;

    @ExcelProperty("售价")
    @Excel(name = "售价")
    @ColumnWidth(8)
    private BigDecimal sellingPrice;

    @ExcelProperty("备注")
    @Excel(name = "备注")
    @ColumnWidth(12)
    private String note;

    @ExcelProperty("下游地址")
    @Excel(name = "下游地址")
    @ColumnWidth(14)
    private String downstreamAddress;

}
