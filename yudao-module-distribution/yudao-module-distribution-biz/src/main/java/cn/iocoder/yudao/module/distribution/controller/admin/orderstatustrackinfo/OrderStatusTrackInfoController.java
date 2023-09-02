package cn.iocoder.yudao.module.distribution.controller.admin.orderstatustrackinfo;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.distribution.controller.admin.orderstatustrackinfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.orderstatustrackinfo.OrderStatusTrackInfoDO;
import cn.iocoder.yudao.module.distribution.convert.orderstatustrackinfo.OrderStatusTrackInfoConvert;
import cn.iocoder.yudao.module.distribution.service.orderstatustrackinfo.OrderStatusTrackInfoService;

@Tag(name = "管理后台 - 配货订单状态跟踪")
@RestController
@RequestMapping("/distribution/order-status-track-info")
@Validated
public class OrderStatusTrackInfoController {

    @Resource
    private OrderStatusTrackInfoService orderStatusTrackInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建配货订单状态跟踪")
    @PreAuthorize("@ss.hasPermission('distribution:order-status-track-info:create')")
    public CommonResult<Long> createOrderStatusTrackInfo(@Valid @RequestBody OrderStatusTrackInfoCreateReqVO createReqVO) {
        return success(orderStatusTrackInfoService.createOrderStatusTrackInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新配货订单状态跟踪")
    @PreAuthorize("@ss.hasPermission('distribution:order-status-track-info:update')")
    public CommonResult<Boolean> updateOrderStatusTrackInfo(@Valid @RequestBody OrderStatusTrackInfoUpdateReqVO updateReqVO) {
        orderStatusTrackInfoService.updateOrderStatusTrackInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除配货订单状态跟踪")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('distribution:order-status-track-info:delete')")
    public CommonResult<Boolean> deleteOrderStatusTrackInfo(@RequestParam("id") Long id) {
        orderStatusTrackInfoService.deleteOrderStatusTrackInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得配货订单状态跟踪")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('distribution:order-status-track-info:query')")
    public CommonResult<OrderStatusTrackInfoRespVO> getOrderStatusTrackInfo(@RequestParam("id") Long id) {
        OrderStatusTrackInfoDO orderStatusTrackInfo = orderStatusTrackInfoService.getOrderStatusTrackInfo(id);
        return success(OrderStatusTrackInfoConvert.INSTANCE.convert(orderStatusTrackInfo));
    }

    @GetMapping("/list")
    @Operation(summary = "获得配货订单状态跟踪列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('distribution:order-status-track-info:query')")
    public CommonResult<List<OrderStatusTrackInfoRespVO>> getOrderStatusTrackInfoList(@RequestParam("ids") Collection<Long> ids) {
        List<OrderStatusTrackInfoDO> list = orderStatusTrackInfoService.getOrderStatusTrackInfoList(ids);
        return success(OrderStatusTrackInfoConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得配货订单状态跟踪分页")
    @PreAuthorize("@ss.hasPermission('distribution:order-status-track-info:query')")
    public CommonResult<PageResult<OrderStatusTrackInfoRespVO>> getOrderStatusTrackInfoPage(@Valid OrderStatusTrackInfoPageReqVO pageVO) {
        PageResult<OrderStatusTrackInfoDO> pageResult = orderStatusTrackInfoService.getOrderStatusTrackInfoPage(pageVO);
        return success(OrderStatusTrackInfoConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出配货订单状态跟踪 Excel")
    @PreAuthorize("@ss.hasPermission('distribution:order-status-track-info:export')")
    @OperateLog(type = EXPORT)
    public void exportOrderStatusTrackInfoExcel(@Valid OrderStatusTrackInfoExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<OrderStatusTrackInfoDO> list = orderStatusTrackInfoService.getOrderStatusTrackInfoList(exportReqVO);
        // 导出 Excel
        List<OrderStatusTrackInfoExcelVO> datas = OrderStatusTrackInfoConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "配货订单状态跟踪.xls", "数据", OrderStatusTrackInfoExcelVO.class, datas);
    }

}
