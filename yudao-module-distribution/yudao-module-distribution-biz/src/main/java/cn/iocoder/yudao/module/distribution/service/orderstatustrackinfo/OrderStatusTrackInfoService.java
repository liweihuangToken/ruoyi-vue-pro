package cn.iocoder.yudao.module.distribution.service.orderstatustrackinfo;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.distribution.controller.admin.orderstatustrackinfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.orderstatustrackinfo.OrderStatusTrackInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 配货订单状态跟踪 Service 接口
 *
 * @author 李伟煌
 */
public interface OrderStatusTrackInfoService {

    /**
     * 创建配货订单状态跟踪
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOrderStatusTrackInfo(@Valid OrderStatusTrackInfoCreateReqVO createReqVO);

    /**
     * 更新配货订单状态跟踪
     *
     * @param updateReqVO 更新信息
     */
    void updateOrderStatusTrackInfo(@Valid OrderStatusTrackInfoUpdateReqVO updateReqVO);

    /**
     * 删除配货订单状态跟踪
     *
     * @param id 编号
     */
    void deleteOrderStatusTrackInfo(Long id);

    /**
     * 获得配货订单状态跟踪
     *
     * @param id 编号
     * @return 配货订单状态跟踪
     */
    OrderStatusTrackInfoDO getOrderStatusTrackInfo(Long id);

    /**
     * 获得配货订单状态跟踪列表
     *
     * @param ids 编号
     * @return 配货订单状态跟踪列表
     */
    List<OrderStatusTrackInfoDO> getOrderStatusTrackInfoList(Collection<Long> ids);

    /**
     * 获得配货订单状态跟踪分页
     *
     * @param pageReqVO 分页查询
     * @return 配货订单状态跟踪分页
     */
    PageResult<OrderStatusTrackInfoDO> getOrderStatusTrackInfoPage(OrderStatusTrackInfoPageReqVO pageReqVO);

    /**
     * 获得配货订单状态跟踪列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 配货订单状态跟踪列表
     */
    List<OrderStatusTrackInfoDO> getOrderStatusTrackInfoList(OrderStatusTrackInfoExportReqVO exportReqVO);

}
