package cn.iocoder.yudao.module.distribution.convert.orderstatustrackinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import cn.iocoder.yudao.module.distribution.controller.admin.orderstatustrackinfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.orderstatustrackinfo.OrderStatusTrackInfoDO;

/**
 * 配货订单状态跟踪 Convert
 *
 * @author 李伟煌
 */
@Mapper
public interface OrderStatusTrackInfoConvert {

    OrderStatusTrackInfoConvert INSTANCE = Mappers.getMapper(OrderStatusTrackInfoConvert.class);

    OrderStatusTrackInfoDO convert(OrderStatusTrackInfoCreateReqVO bean);

    OrderStatusTrackInfoDO convert(OrderStatusTrackInfoUpdateReqVO bean);

    OrderStatusTrackInfoRespVO convert(OrderStatusTrackInfoDO bean);

    List<OrderStatusTrackInfoRespVO> convertList(List<OrderStatusTrackInfoDO> list);

    PageResult<OrderStatusTrackInfoRespVO> convertPage(PageResult<OrderStatusTrackInfoDO> page);

    List<OrderStatusTrackInfoExcelVO> convertList02(List<OrderStatusTrackInfoDO> list);

}
