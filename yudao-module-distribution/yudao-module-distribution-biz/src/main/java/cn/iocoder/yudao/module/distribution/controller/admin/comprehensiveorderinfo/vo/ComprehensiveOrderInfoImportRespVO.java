package cn.iocoder.yudao.module.distribution.controller.admin.comprehensiveorderinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - 综合订单信息导入 Response VO")
@Data
@Builder
public class ComprehensiveOrderInfoImportRespVO {

    @Schema(description = "创建成功的订单数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> createOrders;

    @Schema(description = "导入失败的订单集合，key 为订单序号，value 为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureOrders;

}
