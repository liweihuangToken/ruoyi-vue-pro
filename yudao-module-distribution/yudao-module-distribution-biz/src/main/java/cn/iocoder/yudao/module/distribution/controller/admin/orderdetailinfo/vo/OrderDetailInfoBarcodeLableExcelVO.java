package cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo;

/**
 * 标签导出条形码对象 值适用于A4纸 6 * 24 = 144格
 */

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;

@Getter
@Setter
@EqualsAndHashCode
@ContentRowHeight(35)
public class OrderDetailInfoBarcodeLableExcelVO {
    /**
     * 根据url导出
     *
     * @since 2.1.1
     */
    @ColumnWidth(18)
    @ExcelProperty("条码1")
    private URL url1;

    @ColumnWidth(17)
    @ExcelProperty("条码2")
    private URL url2;

    @ColumnWidth(18)
    @ExcelProperty("条码3")
    private URL url3;

    @ColumnWidth(18)
    @ExcelProperty("条码4")
    private URL url4;

    @ColumnWidth(17)
    @ExcelProperty("条码5")
    private URL url5;

    @ColumnWidth(18)
    @ExcelProperty("条码6")
    private URL url6;
}
