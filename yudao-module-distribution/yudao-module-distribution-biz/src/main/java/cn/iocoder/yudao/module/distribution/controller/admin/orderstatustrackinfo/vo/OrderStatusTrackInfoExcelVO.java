package cn.iocoder.yudao.module.distribution.controller.admin.orderstatustrackinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 配货订单状态跟踪 Excel VO
 *
 * @author 李伟煌
 */
@Data
public class OrderStatusTrackInfoExcelVO {

    @ExcelProperty("主键")
    private Long id;

    @ExcelProperty("订单编号")
    private String orderCode;

    @ExcelProperty("订单变更后状态")
    private Byte orderAfterChangeStatus;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
