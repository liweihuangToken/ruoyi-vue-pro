package cn.iocoder.yudao.module.distribution.controller.admin.comprehensiveorderinfo.vo;

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
 * 配货综合订单信息 Excel VO
 *
 * @author 李伟煌
 */
@Data
public class ComprehensiveOrderInfoExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("订单所属日期")
    private LocalDate orderDate;

    @ExcelProperty("综合订单编号")
    private String comprehensiveOrderCode;

    @ExcelProperty("综合订单文件名称")
    private String comprehensiveOrderFileName;

    @ExcelProperty("综合订单文件URL")
    private String comprehensiveOrderFileUrl;

    @ExcelProperty("订单总数量")
    private Integer totalOrderNumber;

    @ExcelProperty("订单销售总金额")
    private BigDecimal totalOrderSalesAmount;

    @ExcelProperty("订单成本总金额")
    private BigDecimal totalOrderCostAmount;

    @ExcelProperty("订单计划利润总金额")
    private BigDecimal totalOrderPlanProfitAmount;

    @ExcelProperty("结算标志")
    private Byte settlementFlag;

    @ExcelProperty("结算时间")
    private LocalDateTime settlementTime;

    @ExcelProperty("订单成交总数量")
    private Integer totalOrderTransactionsNumber;

    @ExcelProperty("订单退货总数量")
    private Integer totalOrderReturnNumber;

    @ExcelProperty("订单实际利润总金额")
    private BigDecimal totalOrderActualProfitAmount;

    @ExcelProperty("删除时间")
    private LocalDateTime deletedTime;

    @ExcelProperty("备注")
    private String note;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
