package cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 配货订单明细 Excel VO
 *
 * @author 李伟煌
 */
@Data
public class OrderDetailInfoExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("订单所属日期")
    private LocalDate orderDate;

    @ExcelProperty("订单编号")
    private String orderCode;

    @ExcelProperty("综合订单编号")
    private String comprehensiveOrderCode;

    @ExcelProperty("订单图片URL")
    private String orderGoodsPictureUrl;

    @ExcelProperty("订单一维码图片URL")
    private String orderOnedimensionalCodePictureUrl;

    @ExcelProperty("订单销售金额")
    private BigDecimal orderSalesAmount;

    @ExcelProperty("订单成本金额")
    private BigDecimal orderCostAmount;

    @ExcelProperty("订单计划利润金额")
    private BigDecimal orderPlanProfitAmount;

    @ExcelProperty("上游名称")
    private String upstreamName;

    @ExcelProperty("下游名称")
    private String downstreamName;

    @ExcelProperty("订单状态")
    private Byte orderStatus;

    @ExcelProperty("订单实际利润金额")
    private BigDecimal orderActualProfitAmount;

    @ExcelProperty("结算标志")
    private Byte settlementFlag;

    @ExcelProperty("结算时间")
    private LocalDateTime settlementTime;

    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

    @ExcelProperty("备注")
    private String note;

    @ExcelProperty("尺码")
    private String size;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("创建顺序")
    private Integer createSort;

}
