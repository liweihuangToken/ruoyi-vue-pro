package cn.iocoder.yudao.module.distribution.dal.mysql.orderdetailinfo;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.query.MPJLambdaWrapperX;
import cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.comprehensiveorderinfo.ComprehensiveOrderInfoDO;
import cn.iocoder.yudao.module.distribution.dal.dataobject.downstreaminfo.DownstreamInfoDO;
import cn.iocoder.yudao.module.distribution.dal.dataobject.orderdetailinfo.OrderDetailInfoDO;
import cn.iocoder.yudao.module.distribution.dal.dataobject.upstreaminfo.UpstreamInfoDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * 配货订单明细 Mapper
 *
 * @author 李伟煌
 */
@Mapper
public interface OrderDetailInfoMapper extends BaseMapperX<OrderDetailInfoDO> {

    default PageResult<OrderDetailInfoDO> selectPage(OrderDetailInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OrderDetailInfoDO>()
                .betweenIfPresent(OrderDetailInfoDO::getOrderDate, reqVO.getOrderDate())
                .eqIfPresent(OrderDetailInfoDO::getOrderCode, reqVO.getOrderCode())
                .eqIfPresent(OrderDetailInfoDO::getComprehensiveOrderCode, reqVO.getComprehensiveOrderCode())
                .eqIfPresent(OrderDetailInfoDO::getOrderGoodsPictureUrl, reqVO.getOrderGoodsPictureUrl())
                .eqIfPresent(OrderDetailInfoDO::getOrderOnedimensionalCodePictureUrl, reqVO.getOrderOnedimensionalCodePictureUrl())
                .eqIfPresent(OrderDetailInfoDO::getOrderSalesAmount, reqVO.getOrderSalesAmount())
                .eqIfPresent(OrderDetailInfoDO::getOrderCostAmount, reqVO.getOrderCostAmount())
                .eqIfPresent(OrderDetailInfoDO::getOrderPlanProfitAmount, reqVO.getOrderPlanProfitAmount())
                .eqIfPresent(OrderDetailInfoDO::getUpstreamName, reqVO.getUpstreamName())
                .eqIfPresent(OrderDetailInfoDO::getDownstreamName, reqVO.getDownstreamName())
                .eqIfPresent(OrderDetailInfoDO::getOrderStatus, reqVO.getOrderStatus())
                .eqIfPresent(OrderDetailInfoDO::getOrderActualProfitAmount, reqVO.getOrderActualProfitAmount())
                .eqIfPresent(OrderDetailInfoDO::getSettlementFlag, reqVO.getSettlementFlag())
                .betweenIfPresent(OrderDetailInfoDO::getSettlementTime, reqVO.getSettlementTime())
                .betweenIfPresent(OrderDetailInfoDO::getDeletedTime, reqVO.getDeletedTime())
                .eqIfPresent(OrderDetailInfoDO::getNote, reqVO.getNote())
                .eqIfPresent(OrderDetailInfoDO::getSize, reqVO.getSize())
                .betweenIfPresent(OrderDetailInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(OrderDetailInfoDO::getCreateSort, reqVO.getCreateSort())
                .orderByDesc(OrderDetailInfoDO::getComprehensiveOrderCode)
                .orderByAsc(OrderDetailInfoDO::getCreateSort)
                .orderByAsc(OrderDetailInfoDO::getSize)
                .orderByDesc(OrderDetailInfoDO::getId));
    }

    default List<OrderDetailInfoDO> selectList(OrderDetailInfoExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<OrderDetailInfoDO>()
                .betweenIfPresent(OrderDetailInfoDO::getOrderDate, reqVO.getOrderDate())
                .eqIfPresent(OrderDetailInfoDO::getOrderCode, reqVO.getOrderCode())
                .eqIfPresent(OrderDetailInfoDO::getComprehensiveOrderCode, reqVO.getComprehensiveOrderCode())
                .eqIfPresent(OrderDetailInfoDO::getOrderGoodsPictureUrl, reqVO.getOrderGoodsPictureUrl())
                .eqIfPresent(OrderDetailInfoDO::getOrderOnedimensionalCodePictureUrl, reqVO.getOrderOnedimensionalCodePictureUrl())
                .eqIfPresent(OrderDetailInfoDO::getOrderSalesAmount, reqVO.getOrderSalesAmount())
                .eqIfPresent(OrderDetailInfoDO::getOrderCostAmount, reqVO.getOrderCostAmount())
                .eqIfPresent(OrderDetailInfoDO::getOrderPlanProfitAmount, reqVO.getOrderPlanProfitAmount())
                .eqIfPresent(OrderDetailInfoDO::getUpstreamName, reqVO.getUpstreamName())
                .eqIfPresent(OrderDetailInfoDO::getDownstreamName, reqVO.getDownstreamName())
                .eqIfPresent(OrderDetailInfoDO::getOrderStatus, reqVO.getOrderStatus())
                .eqIfPresent(OrderDetailInfoDO::getOrderActualProfitAmount, reqVO.getOrderActualProfitAmount())
                .eqIfPresent(OrderDetailInfoDO::getSettlementFlag, reqVO.getSettlementFlag())
                .betweenIfPresent(OrderDetailInfoDO::getSettlementTime, reqVO.getSettlementTime())
                .betweenIfPresent(OrderDetailInfoDO::getDeletedTime, reqVO.getDeletedTime())
                .eqIfPresent(OrderDetailInfoDO::getNote, reqVO.getNote())
                .eqIfPresent(OrderDetailInfoDO::getSize, reqVO.getSize())
                .betweenIfPresent(OrderDetailInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(OrderDetailInfoDO::getCreateSort, reqVO.getCreateSort())
                .orderByDesc(OrderDetailInfoDO::getId));
    }

    default List<OrderDetailInfoFacingObjectRespVO> selectListFacingDownstream(Collection<Long> ids) {
        return selectJoinList(OrderDetailInfoFacingObjectRespVO.class, new MPJLambdaWrapper<OrderDetailInfoDO>()
                .selectAll(OrderDetailInfoDO.class)
                .select(UpstreamInfoDO::getUpstreamAlias)
                .select(UpstreamInfoDO::getPickupMethod)
                .select(DownstreamInfoDO::getDeliveryMethod)
                .leftJoin(ComprehensiveOrderInfoDO.class, ComprehensiveOrderInfoDO::getComprehensiveOrderCode, OrderDetailInfoDO::getComprehensiveOrderCode)
                .leftJoin(DownstreamInfoDO.class, DownstreamInfoDO::getDownstreamName, OrderDetailInfoDO::getDownstreamName)
                .leftJoin(UpstreamInfoDO.class, UpstreamInfoDO::getUpstreamName, OrderDetailInfoDO::getUpstreamName)
                .in(!ids.isEmpty(), ComprehensiveOrderInfoDO::getId, ids)
                .orderByAsc(DownstreamInfoDO::getDeliveryMethod)
                .orderByAsc(DownstreamInfoDO::getDownstreamAlias)
                // .orderByAsc(OrderDetailInfoDO::getOrderCode)
                .orderByAsc(DownstreamInfoDO::getDeliverySort)
                .orderByAsc(UpstreamInfoDO::getUpstreamAlias)
                .orderByAsc(UpstreamInfoDO::getPickupSort)
                .orderByAsc(OrderDetailInfoDO::getCreateSort)
                .orderByAsc(OrderDetailInfoDO::getSize));
    }

    default List<OrderDetailInfoFacingObjectRespVO> selectListFacingDownstream(OrderDetailInfoExportReqVO reqVO) {
        return selectJoinList(OrderDetailInfoFacingObjectRespVO.class, new MPJLambdaWrapperX<OrderDetailInfoDO>()
                .selectAll(OrderDetailInfoDO.class)
                .selectX(UpstreamInfoDO::getUpstreamAlias, UpstreamInfoDO::getPickupMethod)
                .selectX(DownstreamInfoDO::getDownstreamAlias, DownstreamInfoDO::getDeliveryMethod)
                .leftJoinX(ComprehensiveOrderInfoDO.class, ComprehensiveOrderInfoDO::getComprehensiveOrderCode, OrderDetailInfoDO::getComprehensiveOrderCode)
                .leftJoinX(DownstreamInfoDO.class, DownstreamInfoDO::getDownstreamName, OrderDetailInfoDO::getDownstreamName)
                .leftJoinX(UpstreamInfoDO.class, UpstreamInfoDO::getUpstreamName, OrderDetailInfoDO::getUpstreamName)
                .betweenIfPresent(OrderDetailInfoDO::getOrderDate, reqVO.getOrderDate())
                .eqIfPresent(OrderDetailInfoDO::getOrderCode, reqVO.getOrderCode())
                .eqIfPresent(OrderDetailInfoDO::getComprehensiveOrderCode, reqVO.getComprehensiveOrderCode())
                .eqIfPresent(OrderDetailInfoDO::getOrderGoodsPictureUrl, reqVO.getOrderGoodsPictureUrl())
                .eqIfPresent(OrderDetailInfoDO::getOrderOnedimensionalCodePictureUrl, reqVO.getOrderOnedimensionalCodePictureUrl())
                .eqIfPresent(OrderDetailInfoDO::getOrderSalesAmount, reqVO.getOrderSalesAmount())
                .eqIfPresent(OrderDetailInfoDO::getOrderCostAmount, reqVO.getOrderCostAmount())
                .eqIfPresent(OrderDetailInfoDO::getOrderPlanProfitAmount, reqVO.getOrderPlanProfitAmount())
                .eqIfPresent(OrderDetailInfoDO::getUpstreamName, reqVO.getUpstreamName())
                .eqIfPresent(OrderDetailInfoDO::getDownstreamName, reqVO.getDownstreamName())
                .eqIfPresent(OrderDetailInfoDO::getOrderStatus, reqVO.getOrderStatus())
                .eqIfPresent(OrderDetailInfoDO::getOrderActualProfitAmount, reqVO.getOrderActualProfitAmount())
                .eqIfPresent(OrderDetailInfoDO::getSettlementFlag, reqVO.getSettlementFlag())
                .betweenIfPresent(OrderDetailInfoDO::getSettlementTime, reqVO.getSettlementTime())
                .betweenIfPresent(OrderDetailInfoDO::getDeletedTime, reqVO.getDeletedTime())
                .eqIfPresent(OrderDetailInfoDO::getNote, reqVO.getNote())
                .eqIfPresent(OrderDetailInfoDO::getSize, reqVO.getSize())
                .betweenIfPresent(OrderDetailInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(OrderDetailInfoDO::getCreateSort, reqVO.getCreateSort())
                .orderByAsc(DownstreamInfoDO::getDeliveryMethod)
                .orderByAsc(DownstreamInfoDO::getDownstreamAlias)
                // .orderByAsc(OrderDetailInfoDO::getOrderCode)
                .orderByAsc(DownstreamInfoDO::getDeliverySort)
                .orderByAsc(UpstreamInfoDO::getUpstreamAlias)
                .orderByAsc(UpstreamInfoDO::getPickupSort)
                .orderByAsc(OrderDetailInfoDO::getCreateSort)
                .orderByAsc(OrderDetailInfoDO::getSize));
    }

    default List<OrderDetailInfoFacingObjectRespVO> selectListFacingUpstream(OrderDetailInfoExportReqVO reqVO) {
        return selectJoinList(OrderDetailInfoFacingObjectRespVO.class, new MPJLambdaWrapperX<OrderDetailInfoDO>()
                .selectAll(OrderDetailInfoDO.class)
                .selectX(UpstreamInfoDO::getUpstreamAlias, UpstreamInfoDO::getPickupMethod)
                .selectX(DownstreamInfoDO::getDownstreamAlias, DownstreamInfoDO::getDeliveryMethod)
                .leftJoinX(ComprehensiveOrderInfoDO.class, ComprehensiveOrderInfoDO::getComprehensiveOrderCode, OrderDetailInfoDO::getComprehensiveOrderCode)
                .leftJoinX(DownstreamInfoDO.class, DownstreamInfoDO::getDownstreamName, OrderDetailInfoDO::getDownstreamName)
                .leftJoinX(UpstreamInfoDO.class, UpstreamInfoDO::getUpstreamName, OrderDetailInfoDO::getUpstreamName)
                .betweenIfPresent(OrderDetailInfoDO::getOrderDate, reqVO.getOrderDate())
                .eqIfPresent(OrderDetailInfoDO::getOrderCode, reqVO.getOrderCode())
                .eqIfPresent(OrderDetailInfoDO::getComprehensiveOrderCode, reqVO.getComprehensiveOrderCode())
                .eqIfPresent(OrderDetailInfoDO::getOrderGoodsPictureUrl, reqVO.getOrderGoodsPictureUrl())
                .eqIfPresent(OrderDetailInfoDO::getOrderOnedimensionalCodePictureUrl, reqVO.getOrderOnedimensionalCodePictureUrl())
                .eqIfPresent(OrderDetailInfoDO::getOrderSalesAmount, reqVO.getOrderSalesAmount())
                .eqIfPresent(OrderDetailInfoDO::getOrderCostAmount, reqVO.getOrderCostAmount())
                .eqIfPresent(OrderDetailInfoDO::getOrderPlanProfitAmount, reqVO.getOrderPlanProfitAmount())
                .eqIfPresent(OrderDetailInfoDO::getUpstreamName, reqVO.getUpstreamName())
                .eqIfPresent(OrderDetailInfoDO::getDownstreamName, reqVO.getDownstreamName())
                .eqIfPresent(OrderDetailInfoDO::getOrderStatus, reqVO.getOrderStatus())
                .eqIfPresent(OrderDetailInfoDO::getOrderActualProfitAmount, reqVO.getOrderActualProfitAmount())
                .eqIfPresent(OrderDetailInfoDO::getSettlementFlag, reqVO.getSettlementFlag())
                .betweenIfPresent(OrderDetailInfoDO::getSettlementTime, reqVO.getSettlementTime())
                .betweenIfPresent(OrderDetailInfoDO::getDeletedTime, reqVO.getDeletedTime())
                .eqIfPresent(OrderDetailInfoDO::getNote, reqVO.getNote())
                .eqIfPresent(OrderDetailInfoDO::getSize, reqVO.getSize())
                .betweenIfPresent(OrderDetailInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(OrderDetailInfoDO::getCreateSort, reqVO.getCreateSort())
                .orderByAsc(UpstreamInfoDO::getPickupMethod)
                .orderByAsc(UpstreamInfoDO::getUpstreamAlias)
                .orderByAsc(OrderDetailInfoDO::getOrderCode)
                .orderByAsc(UpstreamInfoDO::getPickupSort)
                .orderByAsc(DownstreamInfoDO::getDownstreamAlias)
                .orderByAsc(DownstreamInfoDO::getDeliveryMethod)
                .orderByAsc(DownstreamInfoDO::getDeliverySort)
                .orderByAsc(OrderDetailInfoDO::getCreateSort)
                .orderByAsc(OrderDetailInfoDO::getSize));
    }

    default List<OrderDetailInfoFacingObjectRespVO> selectListFacingUpstream(Collection<Long> ids) {
        return selectJoinList(OrderDetailInfoFacingObjectRespVO.class, new MPJLambdaWrapper<OrderDetailInfoDO>()
                .selectAll(OrderDetailInfoDO.class)
                .select(UpstreamInfoDO::getUpstreamAlias)
                .select(UpstreamInfoDO::getPickupMethod)
                .select(DownstreamInfoDO::getDeliveryMethod)
                .leftJoin(ComprehensiveOrderInfoDO.class, ComprehensiveOrderInfoDO::getComprehensiveOrderCode, OrderDetailInfoDO::getComprehensiveOrderCode)
                .leftJoin(DownstreamInfoDO.class, DownstreamInfoDO::getDownstreamName, OrderDetailInfoDO::getDownstreamName)
                .leftJoin(UpstreamInfoDO.class, UpstreamInfoDO::getUpstreamName, OrderDetailInfoDO::getUpstreamName)
                .in(!ids.isEmpty(), ComprehensiveOrderInfoDO::getId, ids)
                .orderByAsc(UpstreamInfoDO::getPickupMethod)
                .orderByAsc(UpstreamInfoDO::getUpstreamAlias)
                .orderByAsc(OrderDetailInfoDO::getOrderCode)
                .orderByAsc(UpstreamInfoDO::getPickupSort)
                .orderByAsc(DownstreamInfoDO::getDownstreamAlias)
                .orderByAsc(DownstreamInfoDO::getDeliveryMethod)
                .orderByAsc(DownstreamInfoDO::getDeliverySort)
                .orderByAsc(OrderDetailInfoDO::getCreateSort)
                .orderByAsc(OrderDetailInfoDO::getSize));
    }

    default PageResult<OrderDetailInfoDownstreamRespVO> selectDownstreamOrderPage(OrderDetailInfoDownstreamReqVO reqVO) {
        return selectJoinPage(reqVO, OrderDetailInfoDownstreamRespVO.class, new MPJLambdaWrapperX<OrderDetailInfoDO>()
                .selectX(OrderDetailInfoDO::getOrderDate)
                .selectX(DownstreamInfoDO::getDownstreamAlias, DownstreamInfoDO::getDownstreamName)
                .selectMaxX(DownstreamInfoDO::getDeliveryMethod)
                .selectMaxX(DownstreamInfoDO::getDownstreamAddress)
                .selectCount(OrderDetailInfoDO::getId, OrderDetailInfoDownstreamRespVO::getTotalOrderNumber)
                .selectSumX(OrderDetailInfoDO::getOrderSalesAmount, OrderDetailInfoDownstreamRespVO::getTotalOrderSalesAmount)
                .selectSumX(OrderDetailInfoDO::getOrderCostAmount, OrderDetailInfoDownstreamRespVO::getTotalOrderCostAmount)
                .selectSumX(OrderDetailInfoDO::getOrderPlanProfitAmount, OrderDetailInfoDownstreamRespVO::getTotalOrderPlanProfitAmount)
                .leftJoinX(DownstreamInfoDO.class, DownstreamInfoDO::getDownstreamName, OrderDetailInfoDO::getDownstreamName)
                .betweenIfPresent(OrderDetailInfoDO::getOrderDate, reqVO.getOrderDate())
                .eqIfPresent(DownstreamInfoDO::getDownstreamName, reqVO.getDownstreamName())
                .eqIfPresent(DownstreamInfoDO::getDownstreamAlias, reqVO.getDownstreamAlias())
                .eqIfPresent(DownstreamInfoDO::getDeliveryMethod, reqVO.getDeliveryMethod())
                .groupBy(OrderDetailInfoDO::getDownstreamName)
                .groupBy(DownstreamInfoDO::getDownstreamAlias)
                .groupBy(OrderDetailInfoDO::getOrderDate)
                .orderByAsc(OrderDetailInfoDO::getOrderDate)
                .orderByAsc(DownstreamInfoDO::getDownstreamAlias)
        );
    }

    default PageResult<OrderDetailInfoUpstreamRespVO> selectUpstreamOrderPage(OrderDetailInfoUpstreamReqVO reqVO) {
        return selectJoinPage(reqVO, OrderDetailInfoUpstreamRespVO.class, new MPJLambdaWrapperX<OrderDetailInfoDO>()
                .selectX(OrderDetailInfoDO::getOrderDate)
                .selectX(UpstreamInfoDO::getUpstreamAlias, UpstreamInfoDO::getUpstreamName)
                .selectMaxX(UpstreamInfoDO::getPickupMethod)
                .selectMaxX(UpstreamInfoDO::getUpstreamAddress)
                .selectCount(OrderDetailInfoDO::getId, OrderDetailInfoUpstreamRespVO::getTotalOrderNumber)
                .selectSumX(OrderDetailInfoDO::getOrderSalesAmount, OrderDetailInfoUpstreamRespVO::getTotalOrderSalesAmount)
                .selectSumX(OrderDetailInfoDO::getOrderCostAmount, OrderDetailInfoUpstreamRespVO::getTotalOrderCostAmount)
                .selectSumX(OrderDetailInfoDO::getOrderPlanProfitAmount, OrderDetailInfoUpstreamRespVO::getTotalOrderPlanProfitAmount)
                .leftJoinX(UpstreamInfoDO.class, UpstreamInfoDO::getUpstreamName, OrderDetailInfoDO::getUpstreamName)
                .betweenIfPresent(OrderDetailInfoDO::getOrderDate, reqVO.getOrderDate())
                .eqIfPresent(UpstreamInfoDO::getUpstreamName, reqVO.getUpstreamName())
                .eqIfPresent(UpstreamInfoDO::getUpstreamAlias, reqVO.getUpstreamAlias())
                .eqIfPresent(UpstreamInfoDO::getPickupMethod, reqVO.getPickupMethod())
                .groupBy(OrderDetailInfoDO::getUpstreamName)
                .groupBy(UpstreamInfoDO::getUpstreamAlias)
                .groupBy(OrderDetailInfoDO::getOrderDate)
                .orderByAsc(OrderDetailInfoDO::getOrderDate)
                .orderByAsc(UpstreamInfoDO::getUpstreamAlias)
        );
    }

    default OrderDetailInfoFacingObjectRespVO getOrderDetailInfoByCode(String code) {
        List<OrderDetailInfoFacingObjectRespVO> orderDetailInfoFacingObjectRespVOList = selectJoinList(OrderDetailInfoFacingObjectRespVO.class, new MPJLambdaWrapperX<OrderDetailInfoDO>()
                .selectAll(OrderDetailInfoDO.class)
                .selectX(UpstreamInfoDO::getUpstreamAlias, UpstreamInfoDO::getPickupMethod)
                .selectX(DownstreamInfoDO::getDownstreamAlias, DownstreamInfoDO::getDeliveryMethod)
                .leftJoinX(ComprehensiveOrderInfoDO.class, ComprehensiveOrderInfoDO::getComprehensiveOrderCode, OrderDetailInfoDO::getComprehensiveOrderCode)
                .leftJoinX(DownstreamInfoDO.class, DownstreamInfoDO::getDownstreamName, OrderDetailInfoDO::getDownstreamName)
                .leftJoinX(UpstreamInfoDO.class, UpstreamInfoDO::getUpstreamName, OrderDetailInfoDO::getUpstreamName)
                .eqIfPresent(OrderDetailInfoDO::getOrderCode, code)
                .or()
                .eqIfPresent(OrderDetailInfoDO::getGoodsCode, code));
        if (null != orderDetailInfoFacingObjectRespVOList && !orderDetailInfoFacingObjectRespVOList.isEmpty()) {
            return orderDetailInfoFacingObjectRespVOList.get(0);
        } else {
            return null;
        }
    }
}
