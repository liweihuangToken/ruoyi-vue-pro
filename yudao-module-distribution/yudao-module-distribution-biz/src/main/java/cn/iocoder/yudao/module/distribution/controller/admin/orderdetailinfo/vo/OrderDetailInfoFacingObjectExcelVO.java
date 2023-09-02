package cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentFontStyle;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.BorderStyleEnum;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.net.URL;
import java.util.HashMap;

@Getter
@Setter
@EqualsAndHashCode
@ContentRowHeight(92)
@ContentFontStyle(fontName= "华文楷体", fontHeightInPoints = 10)
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER,
        verticalAlignment = VerticalAlignmentEnum.CENTER,
        wrapped = BooleanEnum.TRUE,
        borderLeft = BorderStyleEnum.THIN,
        borderRight = BorderStyleEnum.THIN,
        borderTop = BorderStyleEnum.THIN,
        borderBottom = BorderStyleEnum.THIN
)
@Accessors(chain = false)
public class OrderDetailInfoFacingObjectExcelVO {
    /**
     * 根据url导出
     *
     * @since 2.1.1
     */
    @ColumnWidth(8)
    @ExcelProperty("对象名称1")
    private String objectName1;

    @ColumnWidth(27)
    @ExcelProperty("订单信息1")
    private URL url1;

    @ColumnWidth(9)
    @ExcelProperty("对象名称2")
    private String objectName2;

    @ColumnWidth(27)
    @ExcelProperty("订单信息2")
    private URL url2;

    @ColumnWidth(9)
    @ExcelProperty("对象名称3")
    private String objectName3;

    @ColumnWidth(27)
    @ExcelProperty("订单信息3")
    private URL url3;
}
