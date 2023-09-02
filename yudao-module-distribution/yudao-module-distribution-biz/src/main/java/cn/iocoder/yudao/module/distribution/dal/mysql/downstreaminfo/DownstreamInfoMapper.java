package cn.iocoder.yudao.module.distribution.dal.mysql.downstreaminfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.distribution.dal.dataobject.downstreaminfo.DownstreamInfoDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.distribution.controller.admin.downstreaminfo.vo.*;

/**
 * 配货下游信息 Mapper
 *
 * @author 李伟煌
 */
@Mapper
public interface DownstreamInfoMapper extends BaseMapperX<DownstreamInfoDO> {

    default PageResult<DownstreamInfoDO> selectPage(DownstreamInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DownstreamInfoDO>()
                .eqIfPresent(DownstreamInfoDO::getDownstreamCode, reqVO.getDownstreamCode())
                .likeIfPresent(DownstreamInfoDO::getDownstreamName, reqVO.getDownstreamName())
                .eqIfPresent(DownstreamInfoDO::getDownstreamAlias, reqVO.getDownstreamAlias())
                .eqIfPresent(DownstreamInfoDO::getDownstreamAddress, reqVO.getDownstreamAddress())
                .eqIfPresent(DownstreamInfoDO::getDeliveryMethod, reqVO.getDeliveryMethod())
                .eqIfPresent(DownstreamInfoDO::getNote, reqVO.getNote())
                .betweenIfPresent(DownstreamInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DownstreamInfoDO::getDeliverySort, reqVO.getDeliverySort())
                .orderByDesc(DownstreamInfoDO::getId));
    }

    default List<DownstreamInfoDO> selectList(DownstreamInfoExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DownstreamInfoDO>()
                .eqIfPresent(DownstreamInfoDO::getDownstreamCode, reqVO.getDownstreamCode())
                .likeIfPresent(DownstreamInfoDO::getDownstreamName, reqVO.getDownstreamName())
                .eqIfPresent(DownstreamInfoDO::getDownstreamAlias, reqVO.getDownstreamAlias())
                .eqIfPresent(DownstreamInfoDO::getDownstreamAddress, reqVO.getDownstreamAddress())
                .eqIfPresent(DownstreamInfoDO::getDeliveryMethod, reqVO.getDeliveryMethod())
                .eqIfPresent(DownstreamInfoDO::getNote, reqVO.getNote())
                .betweenIfPresent(DownstreamInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DownstreamInfoDO::getDeliverySort, reqVO.getDeliverySort())
                .orderByDesc(DownstreamInfoDO::getId));
    }

}
