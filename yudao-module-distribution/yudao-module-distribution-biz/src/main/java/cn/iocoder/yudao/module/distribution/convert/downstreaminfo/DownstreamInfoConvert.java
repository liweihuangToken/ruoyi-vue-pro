package cn.iocoder.yudao.module.distribution.convert.downstreaminfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.distribution.controller.admin.downstreaminfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.downstreaminfo.DownstreamInfoDO;

/**
 * 配货下游信息 Convert
 *
 * @author 李伟煌
 */
@Mapper
public interface DownstreamInfoConvert {

    DownstreamInfoConvert INSTANCE = Mappers.getMapper(DownstreamInfoConvert.class);

    DownstreamInfoDO convert(DownstreamInfoCreateReqVO bean);

    DownstreamInfoDO convert(DownstreamInfoUpdateReqVO bean);

    DownstreamInfoRespVO convert(DownstreamInfoDO bean);

    List<DownstreamInfoRespVO> convertList(List<DownstreamInfoDO> list);

    PageResult<DownstreamInfoRespVO> convertPage(PageResult<DownstreamInfoDO> page);

    List<DownstreamInfoExcelVO> convertList02(List<DownstreamInfoDO> list);

}
