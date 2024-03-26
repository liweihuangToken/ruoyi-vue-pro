package cn.iocoder.yudao.module.distribution.service.orderdetailinfo;

import java.io.IOException;
import java.io.InputStream;
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
    void updateOrderDetailInfo(@Valid OrderDetailInfoUpdateReqVO updateReqVO) throws IOException;

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

    String assignOrderInfoBarcodeLabel(OrderDetailInfoFacingObjectRespVO orderDetailInfoFacingObjectRespVO,
                                       String orderCodeUrl, String orderCode) throws IOException;

    /**
     * 面向上下游获取排序后订单标签信息
     * @param orderDetailInfoExportReqVO
     * @return
     */
    List<OrderDetailInfoLableExcelVO> exportOrderDetailInfoLableExcel(OrderDetailInfoExportReqVO orderDetailInfoExportReqVO) throws MalformedURLException;


    /**
     * 获取最近一个交易日供货商交易综合信息
     * @param reqVO
     * @return
     */
    List<OrderDetailInfoUpstreamRespVO> selectTodaySupplierList(OrderDetailInfoUpstreamReqVO reqVO);

    /**
     * 获取最近一个交易日供货商交易明细信息
     * @param reqVO
     * @return
     */
    List<OrderDetailInfoUpstreamRespVO> selectTodaySupplierOrderList(OrderDetailInfoUpstreamReqVO reqVO);

    /**
     * 面向上下游获取排序后订单导出信息
     * @param orderDetailInfoFacingObjectExportReqVO
     * @return
     * @throws MalformedURLException
     */
    List<OrderDetailInfoFacingObjectRespVO> exportOrderDetailInfoFacingObjectExcel(OrderDetailInfoFacingObjectExportReqVO orderDetailInfoFacingObjectExportReqVO) throws MalformedURLException;

    /**
     * 订单明细详情面向上下游排序
     * @param orderDetailInfoFacingObjectRespVOList
     * @param isFacingDownstream
     * @return
     * @throws MalformedURLException
     */
    List<OrderDetailInfoFacingObjectExcelVO> orderDetailInfoFacingObjectSort(
            List<OrderDetailInfoFacingObjectRespVO> orderDetailInfoFacingObjectRespVOList, boolean isFacingDownstream) throws MalformedURLException;

    /**
     * 上传更新订单图片
     * @param inputStream
     * @return
     */
    String updateOrderPictrue(InputStream inputStream);

    /**
     * 获取每日下游综合信息
     * @param reqVO
     * @return
     */
    PageResult<OrderDetailInfoDownstreamRespVO> selectDownstreamOrderPage(OrderDetailInfoDownstreamReqVO reqVO);

    /**
     * 通过订单编码或货品编码获得配货订单明细
     * @param code
     * @return
     */
    OrderDetailInfoFacingObjectRespVO getOrderDetailInfoByCode(String code);

    /**
     * 更新订单操作
     * @param updateReqVO
     */
    void updateOrderStatus(OrderDetailInfoUpdateReqVO updateReqVO);


    /**
     * 获得配货订单明细列表, 用于 条形码 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 配货订单条形码列表
     */
    List<OrderDetailInfoBarcodeLableExcelVO> exportOrderDetailInfoBarCodeLableExcel(OrderDetailInfoExportReqVO exportReqVO) throws MalformedURLException;

    /**
     * 获取每日上游综合信息
     * @param reqVO
     * @return
     */
    PageResult<OrderDetailInfoUpstreamRespVO> selectUpstreamOrderPage(OrderDetailInfoUpstreamReqVO reqVO);

    List<OrderDetailInfoDownstreamRespVO> selectTodayCustomerList(OrderDetailInfoDownstreamReqVO pageVO);

    List<OrderDetailInfoDownstreamRespVO> selectTodayCustomerOrderList(OrderDetailInfoDownstreamReqVO pageVO);

    int updateOrdersStatusToNotReceived(OrderDetailInfoUpstreamReqVO reqVO);
}
