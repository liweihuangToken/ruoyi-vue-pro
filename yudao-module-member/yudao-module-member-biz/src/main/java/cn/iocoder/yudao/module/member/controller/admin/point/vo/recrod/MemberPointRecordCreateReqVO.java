package cn.iocoder.yudao.module.member.controller.admin.point.vo.recrod;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 用户积分记录创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberPointRecordCreateReqVO extends MemberPointRecordBaseVO {

}
