package cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo.OrderDetailInfoDownstreamReqVO;
import cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo.OrderDetailInfoDownstreamRespVO;
import cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo.OrderDetailInfoUpstreamReqVO;
import cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo.OrderDetailInfoUpstreamRespVO;
import cn.iocoder.yudao.module.distribution.service.orderdetailinfo.OrderDetailInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 下游配货订单明细")
@RestController
@RequestMapping("/distribution/upstream-order-detail-info")
@Validated
public class UpstreamOrderController {

    @Resource
    private OrderDetailInfoService orderDetailInfoService;

    @GetMapping("/page")
    @Operation(summary = "获得下游订单综合信息分页")
    @PreAuthorize("@ss.hasPermission('distribution:upstream-order-detail-info:query')")
    public CommonResult<PageResult<OrderDetailInfoUpstreamRespVO>> getUptreamOrderPage(@Valid OrderDetailInfoUpstreamReqVO pageVO) {
        PageResult<OrderDetailInfoUpstreamRespVO> pageResult = orderDetailInfoService.selectUpstreamOrderPage(pageVO);
        return success(pageResult);
    }

}
