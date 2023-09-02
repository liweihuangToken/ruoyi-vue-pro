package cn.iocoder.yudao.module.distribution.dal.mysql.upstreaminfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.distribution.dal.dataobject.upstreaminfo.UpstreamInfoDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.distribution.controller.admin.upstreaminfo.vo.*;

/**
 * 配货上游信息 Mapper
 *
 * @author 李伟煌
 */
@Mapper
public interface UpstreamInfoMapper extends BaseMapperX<UpstreamInfoDO> {

    default PageResult<UpstreamInfoDO> selectPage(UpstreamInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UpstreamInfoDO>()
                .eqIfPresent(UpstreamInfoDO::getUpstreamCode, reqVO.getUpstreamCode())
                .likeIfPresent(UpstreamInfoDO::getUpstreamName, reqVO.getUpstreamName())
                .eqIfPresent(UpstreamInfoDO::getUpstreamAlias, reqVO.getUpstreamAlias())
                .eqIfPresent(UpstreamInfoDO::getUpstreamAddress, reqVO.getUpstreamAddress())
                .eqIfPresent(UpstreamInfoDO::getPickupMethod, reqVO.getPickupMethod())
                .eqIfPresent(UpstreamInfoDO::getNote, reqVO.getNote())
                .betweenIfPresent(UpstreamInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(UpstreamInfoDO::getPickupSort, reqVO.getPickupSort())
                .orderByDesc(UpstreamInfoDO::getId));
    }

    default List<UpstreamInfoDO> selectList(UpstreamInfoExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<UpstreamInfoDO>()
                .eqIfPresent(UpstreamInfoDO::getUpstreamCode, reqVO.getUpstreamCode())
                .likeIfPresent(UpstreamInfoDO::getUpstreamName, reqVO.getUpstreamName())
                .eqIfPresent(UpstreamInfoDO::getUpstreamAlias, reqVO.getUpstreamAlias())
                .eqIfPresent(UpstreamInfoDO::getUpstreamAddress, reqVO.getUpstreamAddress())
                .eqIfPresent(UpstreamInfoDO::getPickupMethod, reqVO.getPickupMethod())
                .eqIfPresent(UpstreamInfoDO::getNote, reqVO.getNote())
                .betweenIfPresent(UpstreamInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(UpstreamInfoDO::getPickupSort, reqVO.getPickupSort())
                .orderByDesc(UpstreamInfoDO::getId));
    }

}
