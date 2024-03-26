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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.module.infra.enums.ErrorCodeConstants.FILE_IS_EMPTY;

@Tag(name = "管理后台 - 下游配货订单明细")
@RestController
@RequestMapping("/distribution/downstream-order-detail-info")
@Validated
public class DownstreamOrderController {

    @Resource
    private OrderDetailInfoService orderDetailInfoService;

    @GetMapping("/page")
    @Operation(summary = "获得下游订单综合信息分页")
    @PreAuthorize("@ss.hasPermission('distribution:downstream-order-detail-info:query')")
    public CommonResult<PageResult<OrderDetailInfoDownstreamRespVO>> getDownstreamOrderPage(@Valid OrderDetailInfoDownstreamReqVO pageVO) {
        PageResult<OrderDetailInfoDownstreamRespVO> pageResult = orderDetailInfoService.selectDownstreamOrderPage(pageVO);
        return success(pageResult);
    }

    ///////// 移动端 //////////
    @GetMapping("/selectTodayCustomerList")
    @Operation(summary = "获取最近一个交易日客户交易综合信息")
    public CommonResult<List<OrderDetailInfoDownstreamRespVO>> selectTodayCustomerList(@Valid OrderDetailInfoDownstreamReqVO pageVO) {
        List<OrderDetailInfoDownstreamRespVO> todayCustomerList = orderDetailInfoService.selectTodayCustomerList(pageVO);
        return success(todayCustomerList);
    }

    @GetMapping("/selectTodayCustomerOrderList")
    @Operation(summary = "获取最近一个交易日客户交易明细信息")
    public CommonResult<List<OrderDetailInfoDownstreamRespVO>> selectTodayCustomerOrderList(@Valid OrderDetailInfoDownstreamReqVO pageVO) {
        List<OrderDetailInfoDownstreamRespVO> todayCustomerOrderList = orderDetailInfoService.selectTodayCustomerOrderList(pageVO);
        return success(todayCustomerOrderList);
    }

}
