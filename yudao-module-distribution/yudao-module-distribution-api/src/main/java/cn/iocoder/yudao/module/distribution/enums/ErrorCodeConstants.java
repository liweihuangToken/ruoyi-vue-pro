package cn.iocoder.yudao.module.distribution.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * Distribution 错误码枚举类
 *
 * Distribution 系统，使用 1-005-000-000 段
 */
public interface ErrorCodeConstants {

    //========== 用户积分 1004005000 ==========

    // TODO @xiaqing：错误码要分段；例如说这里，积分配置、积分记录、签到配置、签到记录；分成 4 段；

    ErrorCode UPSTREAM_INFO_NOT_EXISTS = new ErrorCode(1004001000, "上游信息不存在");

    ErrorCode ORDER_STATUS_TRACK_INFO_NOT_EXISTS = new ErrorCode(1004002000, "订单状态跟踪信息不存在");

    ErrorCode ORDER_DETAIL_INFO_NOT_EXISTS = new ErrorCode( 1004003000, "订单详细信息记录不存在");

    ErrorCode DOWNSTREAM_INFO_NOT_EXISTS = new ErrorCode(1004004000, "下游信息不存在");

    ErrorCode OBJECT_STREAM_METHON_NOT_EXISTS = new ErrorCode( 1004003001, "相关订单明细存在新的上下游客户，新上下游客户取货/提货方式不存在,请前往补全信息");

    ErrorCode COMPREHENSIVE_ORDER_INFO_NOT_EXISTS = new ErrorCode(1004005000, "综合订单信息不存在");

    ErrorCode ORDER_IMPORT_LIST_IS_EMPTY = new ErrorCode(1004005001, "导入订单数据不能为空！");
}
