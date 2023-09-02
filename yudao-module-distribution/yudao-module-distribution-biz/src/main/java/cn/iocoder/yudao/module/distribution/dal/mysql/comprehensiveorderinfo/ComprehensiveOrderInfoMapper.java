package cn.iocoder.yudao.module.distribution.dal.mysql.comprehensiveorderinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.distribution.dal.dataobject.comprehensiveorderinfo.ComprehensiveOrderInfoDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.distribution.controller.admin.comprehensiveorderinfo.vo.*;

/**
 * 配货综合订单信息 Mapper
 *
 * @author 李伟煌
 */
@Mapper
public interface ComprehensiveOrderInfoMapper extends BaseMapperX<ComprehensiveOrderInfoDO> {

    default PageResult<ComprehensiveOrderInfoDO> selectPage(ComprehensiveOrderInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ComprehensiveOrderInfoDO>()
                .betweenIfPresent(ComprehensiveOrderInfoDO::getOrderDate, reqVO.getOrderDate())
                .eqIfPresent(ComprehensiveOrderInfoDO::getComprehensiveOrderCode, reqVO.getComprehensiveOrderCode())
                .likeIfPresent(ComprehensiveOrderInfoDO::getComprehensiveOrderFileName, reqVO.getComprehensiveOrderFileName())
                .eqIfPresent(ComprehensiveOrderInfoDO::getComprehensiveOrderFileUrl, reqVO.getComprehensiveOrderFileUrl())
                .eqIfPresent(ComprehensiveOrderInfoDO::getTotalOrderNumber, reqVO.getTotalOrderNumber())
                .eqIfPresent(ComprehensiveOrderInfoDO::getTotalOrderSalesAmount, reqVO.getTotalOrderSalesAmount())
                .eqIfPresent(ComprehensiveOrderInfoDO::getTotalOrderCostAmount, reqVO.getTotalOrderCostAmount())
                .eqIfPresent(ComprehensiveOrderInfoDO::getTotalOrderPlanProfitAmount, reqVO.getTotalOrderPlanProfitAmount())
                .eqIfPresent(ComprehensiveOrderInfoDO::getSettlementFlag, reqVO.getSettlementFlag())
                .betweenIfPresent(ComprehensiveOrderInfoDO::getSettlementTime, reqVO.getSettlementTime())
                .eqIfPresent(ComprehensiveOrderInfoDO::getTotalOrderTransactionsNumber, reqVO.getTotalOrderTransactionsNumber())
                .eqIfPresent(ComprehensiveOrderInfoDO::getTotalOrderReturnNumber, reqVO.getTotalOrderReturnNumber())
                .eqIfPresent(ComprehensiveOrderInfoDO::getTotalOrderActualProfitAmount, reqVO.getTotalOrderActualProfitAmount())
                .betweenIfPresent(ComprehensiveOrderInfoDO::getDeletedTime, reqVO.getDeletedTime())
                .eqIfPresent(ComprehensiveOrderInfoDO::getNote, reqVO.getNote())
                .betweenIfPresent(ComprehensiveOrderInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ComprehensiveOrderInfoDO::getId));
    }

    default List<ComprehensiveOrderInfoDO> selectList(ComprehensiveOrderInfoExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ComprehensiveOrderInfoDO>()
                .betweenIfPresent(ComprehensiveOrderInfoDO::getOrderDate, reqVO.getOrderDate())
                .eqIfPresent(ComprehensiveOrderInfoDO::getComprehensiveOrderCode, reqVO.getComprehensiveOrderCode())
                .likeIfPresent(ComprehensiveOrderInfoDO::getComprehensiveOrderFileName, reqVO.getComprehensiveOrderFileName())
                .eqIfPresent(ComprehensiveOrderInfoDO::getComprehensiveOrderFileUrl, reqVO.getComprehensiveOrderFileUrl())
                .eqIfPresent(ComprehensiveOrderInfoDO::getTotalOrderNumber, reqVO.getTotalOrderNumber())
                .eqIfPresent(ComprehensiveOrderInfoDO::getTotalOrderSalesAmount, reqVO.getTotalOrderSalesAmount())
                .eqIfPresent(ComprehensiveOrderInfoDO::getTotalOrderCostAmount, reqVO.getTotalOrderCostAmount())
                .eqIfPresent(ComprehensiveOrderInfoDO::getTotalOrderPlanProfitAmount, reqVO.getTotalOrderPlanProfitAmount())
                .eqIfPresent(ComprehensiveOrderInfoDO::getSettlementFlag, reqVO.getSettlementFlag())
                .betweenIfPresent(ComprehensiveOrderInfoDO::getSettlementTime, reqVO.getSettlementTime())
                .eqIfPresent(ComprehensiveOrderInfoDO::getTotalOrderTransactionsNumber, reqVO.getTotalOrderTransactionsNumber())
                .eqIfPresent(ComprehensiveOrderInfoDO::getTotalOrderReturnNumber, reqVO.getTotalOrderReturnNumber())
                .eqIfPresent(ComprehensiveOrderInfoDO::getTotalOrderActualProfitAmount, reqVO.getTotalOrderActualProfitAmount())
                .betweenIfPresent(ComprehensiveOrderInfoDO::getDeletedTime, reqVO.getDeletedTime())
                .eqIfPresent(ComprehensiveOrderInfoDO::getNote, reqVO.getNote())
                .betweenIfPresent(ComprehensiveOrderInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ComprehensiveOrderInfoDO::getId));
    }

}
