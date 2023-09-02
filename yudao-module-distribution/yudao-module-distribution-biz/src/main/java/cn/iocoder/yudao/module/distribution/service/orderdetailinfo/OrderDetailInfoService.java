package cn.iocoder.yudao.module.distribution.service.orderdetailinfo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.orderdetailinfo.OrderDetailInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 配货订单明细 Service 接口
 *
 * @author 李伟煌
 */
public interface OrderDetailInfoService {

    /**
     * 创建配货订单明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOrderDetailInfo(@Valid OrderDetailInfoCreateReqVO createReqVO);

    /**
     * 更新配货订单明细
     *
     * @param updateReqVO 更新信息
     */
    void updateOrderDetailInfo(@Valid OrderDetailInfoUpdateReqVO updateReqVO);

    /**
     * 删除配货订单明细
     *
     * @param id 编号
     */
    void deleteOrderDetailInfo(Long id);

    /**
     * 获得配货订单明细
     *
     * @param id 编号
     * @return 配货订单明细
     */
    OrderDetailInfoDO getOrderDetailInfo(Long id);

    /**
     * 获得配货订单明细列表
     *
     * @param ids 编号
     * @return 配货订单明细列表
     */
    List<OrderDetailInfoDO> getOrderDetailInfoList(Collection<Long> ids);

    /**
     * 获得配货订单明细分页
     *
     * @param pageReqVO 分页查询
     * @return 配货订单明细分页
     */
    PageResult<OrderDetailInfoDO> getOrderDetailInfoPage(OrderDetailInfoPageReqVO pageReqVO);

    /**
     * 获得配货订单明细列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 配货订单明细列表
     */
    List<OrderDetailInfoDO> getOrderDetailInfoList(OrderDetailInfoExportReqVO exportReqVO);

    /**
     * 生成订单标签图片
     * @param orderDetailInfoFacingObjectRespVO
     * @param orderCodeUrl
     * @param orderCode
     * @return
     */
    String assignOrderInfoLabel(OrderDetailInfoFacingObjectRespVO orderDetailInfoFacingObjectRespVO, String orderCodeUrl, String orderCode) throws IOException;

    /**
     * 面向上下游获取排序后订单标签信息
     * @param orderDetailInfoExportReqVO
     * @return
     */
    List<OrderDetailInfoLableExcelVO> exportOrderDetailInfoLableExcel(OrderDetailInfoExportReqVO orderDetailInfoExportReqVO) throws MalformedURLException;

    /**
     * 面向上下游获取排序后订单导出信息
     * @param orderDetailInfoFacingObjectExportReqVO
     * @return
     * @throws MalformedURLException
     */
    List<OrderDetailInfoFacingObjectRespVO> exportOrderDetailInfoFacingObjectExcel(OrderDetailInfoFacingObjectExportReqVO orderDetailInfoFacingObjectExportReqVO) throws MalformedURLException;

    List<OrderDetailInfoFacingObjectExcelVO> orderDetailInfoFacingObjectSort(
            List<OrderDetailInfoFacingObjectRespVO> orderDetailInfoFacingObjectRespVOList, boolean isFacingDownstream) throws MalformedURLException;
}
