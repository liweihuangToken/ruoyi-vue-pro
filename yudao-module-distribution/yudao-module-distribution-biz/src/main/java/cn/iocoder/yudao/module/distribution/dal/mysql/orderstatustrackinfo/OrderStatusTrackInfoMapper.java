package cn.iocoder.yudao.module.distribution.dal.mysql.orderstatustrackinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.distribution.dal.dataobject.orderstatustrackinfo.OrderStatusTrackInfoDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.distribution.controller.admin.orderstatustrackinfo.vo.*;

/**
 * 配货订单状态跟踪 Mapper
 *
 * @author 李伟煌
 */
@Mapper
public interface OrderStatusTrackInfoMapper extends BaseMapperX<OrderStatusTrackInfoDO> {

    default PageResult<OrderStatusTrackInfoDO> selectPage(OrderStatusTrackInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OrderStatusTrackInfoDO>()
                .eqIfPresent(OrderStatusTrackInfoDO::getOrderCode, reqVO.getOrderCode())
                .eqIfPresent(OrderStatusTrackInfoDO::getOrderAfterChangeStatus, reqVO.getOrderAfterChangeStatus())
                .betweenIfPresent(OrderStatusTrackInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OrderStatusTrackInfoDO::getId));
    }

    default List<OrderStatusTrackInfoDO> selectList(OrderStatusTrackInfoExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<OrderStatusTrackInfoDO>()
                .eqIfPresent(OrderStatusTrackInfoDO::getOrderCode, reqVO.getOrderCode())
                .eqIfPresent(OrderStatusTrackInfoDO::getOrderAfterChangeStatus, reqVO.getOrderAfterChangeStatus())
                .betweenIfPresent(OrderStatusTrackInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OrderStatusTrackInfoDO::getId));
    }

}
