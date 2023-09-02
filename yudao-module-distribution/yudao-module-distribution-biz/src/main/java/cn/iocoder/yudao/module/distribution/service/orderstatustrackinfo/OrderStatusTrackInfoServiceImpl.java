package cn.iocoder.yudao.module.distribution.service.orderstatustrackinfo;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.distribution.controller.admin.orderstatustrackinfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.orderstatustrackinfo.OrderStatusTrackInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.distribution.convert.orderstatustrackinfo.OrderStatusTrackInfoConvert;
import cn.iocoder.yudao.module.distribution.dal.mysql.orderstatustrackinfo.OrderStatusTrackInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.distribution.enums.ErrorCodeConstants.*;

/**
 * 配货订单状态跟踪 Service 实现类
 *
 * @author 李伟煌
 */
@Service
@Validated
public class OrderStatusTrackInfoServiceImpl implements OrderStatusTrackInfoService {

    @Resource
    private OrderStatusTrackInfoMapper orderStatusTrackInfoMapper;

    @Override
    public Long createOrderStatusTrackInfo(OrderStatusTrackInfoCreateReqVO createReqVO) {
        // 插入
        OrderStatusTrackInfoDO orderStatusTrackInfo = OrderStatusTrackInfoConvert.INSTANCE.convert(createReqVO);
        orderStatusTrackInfoMapper.insert(orderStatusTrackInfo);
        // 返回
        return orderStatusTrackInfo.getId();
    }

    @Override
    public void updateOrderStatusTrackInfo(OrderStatusTrackInfoUpdateReqVO updateReqVO) {
        // 校验存在
        validateOrderStatusTrackInfoExists(updateReqVO.getId());
        // 更新
        OrderStatusTrackInfoDO updateObj = OrderStatusTrackInfoConvert.INSTANCE.convert(updateReqVO);
        orderStatusTrackInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteOrderStatusTrackInfo(Long id) {
        // 校验存在
        validateOrderStatusTrackInfoExists(id);
        // 删除
        orderStatusTrackInfoMapper.deleteById(id);
    }

    private void validateOrderStatusTrackInfoExists(Long id) {
        if (orderStatusTrackInfoMapper.selectById(id) == null) {
            throw exception(ORDER_STATUS_TRACK_INFO_NOT_EXISTS);
        }
    }

    @Override
    public OrderStatusTrackInfoDO getOrderStatusTrackInfo(Long id) {
        return orderStatusTrackInfoMapper.selectById(id);
    }

    @Override
    public List<OrderStatusTrackInfoDO> getOrderStatusTrackInfoList(Collection<Long> ids) {
        return orderStatusTrackInfoMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<OrderStatusTrackInfoDO> getOrderStatusTrackInfoPage(OrderStatusTrackInfoPageReqVO pageReqVO) {
        return orderStatusTrackInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<OrderStatusTrackInfoDO> getOrderStatusTrackInfoList(OrderStatusTrackInfoExportReqVO exportReqVO) {
        return orderStatusTrackInfoMapper.selectList(exportReqVO);
    }

}
