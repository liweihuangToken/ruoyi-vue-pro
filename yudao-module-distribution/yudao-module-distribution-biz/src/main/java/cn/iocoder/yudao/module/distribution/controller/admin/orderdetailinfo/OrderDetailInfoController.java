package cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.handle.CustomCellWriteHandler;
import cn.iocoder.yudao.framework.excel.core.strategy.CustomMergeStrategy;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo.*;
import cn.iocoder.yudao.module.distribution.convert.orderdetailinfo.OrderDetailInfoConvert;
import cn.iocoder.yudao.module.distribution.dal.dataobject.orderdetailinfo.OrderDetailInfoDO;
import cn.iocoder.yudao.module.distribution.service.orderdetailinfo.OrderDetailInfoService;
import com.alibaba.excel.EasyExcel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - 配货订单明细")
@RestController
@RequestMapping("/distribution/order-detail-info")
@Validated
public class OrderDetailInfoController {

    @Resource
    private OrderDetailInfoService orderDetailInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建配货订单明细")
    @PreAuthorize("@ss.hasPermission('distribution:order-detail-info:create')")
    public CommonResult<Long> createOrderDetailInfo(@Valid @RequestBody OrderDetailInfoCreateReqVO createReqVO) {
        return success(orderDetailInfoService.createOrderDetailInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新配货订单明细")
    @PreAuthorize("@ss.hasPermission('distribution:order-detail-info:update')")
    public CommonResult<Boolean> updateOrderDetailInfo(@Valid @RequestBody OrderDetailInfoUpdateReqVO updateReqVO) {
        orderDetailInfoService.updateOrderDetailInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除配货订单明细")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('distribution:order-detail-info:delete')")
    public CommonResult<Boolean> deleteOrderDetailInfo(@RequestParam("id") Long id) {
        orderDetailInfoService.deleteOrderDetailInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得配货订单明细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('distribution:order-detail-info:query')")
    public CommonResult<OrderDetailInfoRespVO> getOrderDetailInfo(@RequestParam("id") Long id) {
        OrderDetailInfoDO orderDetailInfo = orderDetailInfoService.getOrderDetailInfo(id);
        return success(OrderDetailInfoConvert.INSTANCE.convert(orderDetailInfo));
    }

    @GetMapping("/list")
    @Operation(summary = "获得配货订单明细列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('distribution:order-detail-info:query')")
    public CommonResult<List<OrderDetailInfoRespVO>> getOrderDetailInfoList(@RequestParam("ids") Collection<Long> ids) {
        List<OrderDetailInfoDO> list = orderDetailInfoService.getOrderDetailInfoList(ids);
        return success(OrderDetailInfoConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得配货订单明细分页")
    @PreAuthorize("@ss.hasPermission('distribution:order-detail-info:query')")
    public CommonResult<PageResult<OrderDetailInfoRespVO>> getOrderDetailInfoPage(@Valid OrderDetailInfoPageReqVO pageVO) {
        PageResult<OrderDetailInfoDO> pageResult = orderDetailInfoService.getOrderDetailInfoPage(pageVO);
        return success(OrderDetailInfoConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出配货订单明细 Excel")
    @PreAuthorize("@ss.hasPermission('distribution:order-detail-info:export')")
    @OperateLog(type = EXPORT)
    public void exportOrderDetailInfoExcel(@Valid OrderDetailInfoExportReqVO exportReqVO,
                                           HttpServletResponse response) throws IOException {
        List<OrderDetailInfoDO> list = orderDetailInfoService.getOrderDetailInfoList(exportReqVO);
        // 导出 Excel
        List<OrderDetailInfoExcelVO> datas = OrderDetailInfoConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "配货订单明细.xls", "数据", OrderDetailInfoExcelVO.class, datas);
    }


    @GetMapping("/export-lable-excel")
    @Operation(summary = "导出配货订单明细标签 Excel")
    @PreAuthorize("@ss.hasPermission('distribution:order-detail-info:export-lable-excel')")
    @OperateLog(type = EXPORT)
    public void exportOrderDetailInfoLableExcel(@Valid OrderDetailInfoExportReqVO exportReqVO,
                                                HttpServletResponse response) throws IOException {
        List<OrderDetailInfoLableExcelVO> list = orderDetailInfoService.exportOrderDetailInfoLableExcel(exportReqVO);
        ExcelUtils.write(response, "配货订单明细.xls", "数据", OrderDetailInfoLableExcelVO.class, list, false);
    }

    @GetMapping("/export-facing-excel")
    @Operation(summary = "导出配货订单明细面向上下游排序 Excel")
    @PreAuthorize("@ss.hasPermission('distribution:order-detail-info:export-facing-excel')")
    @OperateLog(type = EXPORT)
    public void exportOrderDetailInfoFacingObjectExcel(@Valid OrderDetailInfoFacingObjectExportReqVO exportReqVO,
                                                       HttpServletResponse response) throws IOException {
        List<OrderDetailInfoFacingObjectRespVO> orderDetailInfoFacingObjectRespVOList =
                orderDetailInfoService.exportOrderDetailInfoFacingObjectExcel(exportReqVO);

        List<OrderDetailInfoFacingObjectExcelVO> list = orderDetailInfoService.orderDetailInfoFacingObjectSort(
                orderDetailInfoFacingObjectRespVOList, exportReqVO.getIsFacingDownstream());

        // ExcelUtils.write(response, "配货订单明细.xls", "数据", OrderDetailInfoFacingObjectExcelVO.class, list, false);
        // 输出 Excel
        ServletOutputStream outputStream = response.getOutputStream();

        List<String> stringList = exportReqVO.getIsFacingDownstream() ?
                orderDetailInfoFacingObjectRespVOList.stream().map(OrderDetailInfoFacingObjectRespVO::getDownstreamName).collect(Collectors.toList()) :
                orderDetailInfoFacingObjectRespVOList.stream().map(OrderDetailInfoFacingObjectRespVO::getUpstreamName).collect(Collectors.toList());
        HashMap<String, String> totalInfoMap = new HashMap<>();
        for (OrderDetailInfoFacingObjectRespVO orderDetailInfoFacingObjectRespVO : orderDetailInfoFacingObjectRespVOList) {
            String name = exportReqVO.getIsFacingDownstream() ?
                    orderDetailInfoFacingObjectRespVO.getDownstreamName() :
                    orderDetailInfoFacingObjectRespVO.getUpstreamName();
            totalInfoMap.put(name, orderDetailInfoFacingObjectRespVO.getTotalInfo());
        }

        EasyExcel.write(outputStream, OrderDetailInfoFacingObjectExcelVO.class)
                .autoCloseStream(false) // 不要自动关闭，交给 Servlet 自己处理
                .needHead(false)
                .sheet("数据")
                .registerWriteHandler(new CustomCellWriteHandler(stringList, totalInfoMap))
                .registerWriteHandler(new CustomMergeStrategy(
                        list.stream().map(OrderDetailInfoFacingObjectExcelVO::getObjectName1).collect(Collectors.toList()), 0
                ))
                .registerWriteHandler(new CustomMergeStrategy(
                        list.stream().map(OrderDetailInfoFacingObjectExcelVO::getObjectName2).collect(Collectors.toList()), 2
                ))
                .registerWriteHandler(new CustomMergeStrategy(
                        list.stream().map(OrderDetailInfoFacingObjectExcelVO::getObjectName3).collect(Collectors.toList()), 4
                ))
                .doWrite(list);
        // 设置 header 和 contentType。写在最后的原因是，避免报错时，响应 contentType 已经被修改了
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("配货订单明细.xls", "UTF-8"));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        outputStream.close();
    }

}
