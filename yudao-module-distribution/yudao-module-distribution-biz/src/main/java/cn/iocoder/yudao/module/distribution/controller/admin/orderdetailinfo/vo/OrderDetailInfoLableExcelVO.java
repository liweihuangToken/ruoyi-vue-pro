package cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo;

/**
 * 标签导出对象 值适用于A4纸 4 * 9 = 36格
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
@ContentRowHeight(92)
public class OrderDetailInfoLableExcelVO {
    /**
     * 根据url导出
     *
     * @since 2.1.1
     */
    @ColumnWidth(26)
    @ExcelProperty("订单信息1")
    private URL url1;

    @ColumnWidth(26)
    @ExcelProperty("订单信息2")
    private URL url2;

    @ColumnWidth(27)
    @ExcelProperty("订单信息3")
    private URL url3;

    @ColumnWidth(26)
    @ExcelProperty("订单信息4")
    private URL url4;
}
