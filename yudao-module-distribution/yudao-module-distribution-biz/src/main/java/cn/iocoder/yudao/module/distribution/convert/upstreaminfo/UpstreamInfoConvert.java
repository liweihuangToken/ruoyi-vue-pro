package cn.iocoder.yudao.module.distribution.convert.upstreaminfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.distribution.controller.admin.upstreaminfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.upstreaminfo.UpstreamInfoDO;

/**
 * 配货上游信息 Convert
 *
 * @author 李伟煌
 */
@Mapper
public interface UpstreamInfoConvert {

    UpstreamInfoConvert INSTANCE = Mappers.getMapper(UpstreamInfoConvert.class);

    UpstreamInfoDO convert(UpstreamInfoCreateReqVO bean);

    UpstreamInfoDO convert(UpstreamInfoUpdateReqVO bean);

    UpstreamInfoRespVO convert(UpstreamInfoDO bean);

    List<UpstreamInfoRespVO> convertList(List<UpstreamInfoDO> list);

    PageResult<UpstreamInfoRespVO> convertPage(PageResult<UpstreamInfoDO> page);

    List<UpstreamInfoExcelVO> convertList02(List<UpstreamInfoDO> list);

}
