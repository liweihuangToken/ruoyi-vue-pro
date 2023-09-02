package cn.iocoder.yudao.module.distribution.convert.orderdetailinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.orderdetailinfo.OrderDetailInfoDO;

/**
 * 配货订单明细 Convert
 *
 * @author 李伟煌
 */
@Mapper
public interface OrderDetailInfoConvert {

    OrderDetailInfoConvert INSTANCE = Mappers.getMapper(OrderDetailInfoConvert.class);

    OrderDetailInfoDO convert(OrderDetailInfoCreateReqVO bean);

    OrderDetailInfoDO convert(OrderDetailInfoUpdateReqVO bean);

    OrderDetailInfoRespVO convert(OrderDetailInfoDO bean);

    List<OrderDetailInfoRespVO> convertList(List<OrderDetailInfoDO> list);

    PageResult<OrderDetailInfoRespVO> convertPage(PageResult<OrderDetailInfoDO> page);

    List<OrderDetailInfoExcelVO> convertList02(List<OrderDetailInfoDO> list);

}
