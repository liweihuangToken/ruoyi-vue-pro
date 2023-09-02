package cn.iocoder.yudao.module.distribution.convert.comprehensiveorderinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.distribution.controller.admin.comprehensiveorderinfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.comprehensiveorderinfo.ComprehensiveOrderInfoDO;

/**
 * 配货综合订单信息 Convert
 *
 * @author 李伟煌
 */
@Mapper
public interface ComprehensiveOrderInfoConvert {

    ComprehensiveOrderInfoConvert INSTANCE = Mappers.getMapper(ComprehensiveOrderInfoConvert.class);

    ComprehensiveOrderInfoDO convert(ComprehensiveOrderInfoCreateReqVO bean);

    ComprehensiveOrderInfoDO convert(ComprehensiveOrderInfoUpdateReqVO bean);

    ComprehensiveOrderInfoRespVO convert(ComprehensiveOrderInfoDO bean);

    List<ComprehensiveOrderInfoRespVO> convertList(List<ComprehensiveOrderInfoDO> list);

    PageResult<ComprehensiveOrderInfoRespVO> convertPage(PageResult<ComprehensiveOrderInfoDO> page);

    List<ComprehensiveOrderInfoExcelVO> convertList02(List<ComprehensiveOrderInfoDO> list);

}
