package cn.iocoder.yudao.module.distribution.controller.admin.comprehensiveorderinfo;

import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.module.system.enums.common.SexEnum;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import javax.servlet.http.*;
import java.math.BigDecimal;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.distribution.controller.admin.comprehensiveorderinfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.comprehensiveorderinfo.ComprehensiveOrderInfoDO;
import cn.iocoder.yudao.module.distribution.convert.comprehensiveorderinfo.ComprehensiveOrderInfoConvert;
import cn.iocoder.yudao.module.distribution.service.comprehensiveorderinfo.ComprehensiveOrderInfoService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 配货综合订单信息")
@RestController
@RequestMapping("/distribution/comprehensive-order-info")
@Validated
public class ComprehensiveOrderInfoController {

    @Resource
    private ComprehensiveOrderInfoService comprehensiveOrderInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建配货综合订单信息")
    @PreAuthorize("@ss.hasPermission('distribution:comprehensive-order-info:create')")
    public CommonResult<Long> createComprehensiveOrderInfo(@Valid @RequestBody ComprehensiveOrderInfoCreateReqVO createReqVO) {
        return success(comprehensiveOrderInfoService.createComprehensiveOrderInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新配货综合订单信息")
    @PreAuthorize("@ss.hasPermission('distribution:comprehensive-order-info:update')")
    public CommonResult<Boolean> updateComprehensiveOrderInfo(@Valid @RequestBody ComprehensiveOrderInfoUpdateReqVO updateReqVO) {
        comprehensiveOrderInfoService.updateComprehensiveOrderInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除配货综合订单信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('distribution:comprehensive-order-info:delete')")
    public CommonResult<Boolean> deleteComprehensiveOrderInfo(@RequestParam("id") Long id) {
        comprehensiveOrderInfoService.deleteComprehensiveOrderInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得配货综合订单信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('distribution:comprehensive-order-info:query')")
    public CommonResult<ComprehensiveOrderInfoRespVO> getComprehensiveOrderInfo(@RequestParam("id") Long id) {
        ComprehensiveOrderInfoDO comprehensiveOrderInfo = comprehensiveOrderInfoService.getComprehensiveOrderInfo(id);
        return success(ComprehensiveOrderInfoConvert.INSTANCE.convert(comprehensiveOrderInfo));
    }

    @GetMapping("/list")
    @Operation(summary = "获得配货综合订单信息列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('distribution:comprehensive-order-info:query')")
    public CommonResult<List<ComprehensiveOrderInfoRespVO>> getComprehensiveOrderInfoList(@RequestParam("ids") Collection<Long> ids) {
        List<ComprehensiveOrderInfoDO> list = comprehensiveOrderInfoService.getComprehensiveOrderInfoList(ids);
        return success(ComprehensiveOrderInfoConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得配货综合订单信息分页")
    @PreAuthorize("@ss.hasPermission('distribution:comprehensive-order-info:query')")
    public CommonResult<PageResult<ComprehensiveOrderInfoRespVO>> getComprehensiveOrderInfoPage(@Valid ComprehensiveOrderInfoPageReqVO pageVO) {
        PageResult<ComprehensiveOrderInfoDO> pageResult = comprehensiveOrderInfoService.getComprehensiveOrderInfoPage(pageVO);
        return success(ComprehensiveOrderInfoConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出配货综合订单信息 Excel")
    @PreAuthorize("@ss.hasPermission('distribution:comprehensive-order-info:export')")
    @OperateLog(type = EXPORT)
    public void exportComprehensiveOrderInfoExcel(@Valid ComprehensiveOrderInfoExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<ComprehensiveOrderInfoDO> list = comprehensiveOrderInfoService.getComprehensiveOrderInfoList(exportReqVO);
        // 导出 Excel
        List<ComprehensiveOrderInfoExcelVO> datas = ComprehensiveOrderInfoConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "配货综合订单信息.xls", "数据", ComprehensiveOrderInfoExcelVO.class, datas);
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入综合订单模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<ComprehensiveOrderImportExcelVO> importExcelVOList = new ArrayList<>(10000);
        importExcelVOList.add(ComprehensiveOrderImportExcelVO.builder()
                .downstreamName("示例：昵x")
                .upstreamName("疯x")
                .size("32 35*2 36或37")
                .deliveryMethod(1)
                .sellingPrice(new BigDecimal(265))
                .note("有印、xxx、xxx等等等")
                .downstreamAddress("xxx小区xxx室")
                .build());
        for (int i = 1; i < 10000; i++) {
            ComprehensiveOrderImportExcelVO comprehensiveOrderImportExcelVO = new ComprehensiveOrderImportExcelVO();
            importExcelVOList.add(comprehensiveOrderImportExcelVO);
        }
        // 输出
        ExcelUtils.write(response, "综合订单导入模板.xlsx", "订单列表", ComprehensiveOrderImportExcelVO.class, importExcelVOList);
    }

    @PostMapping("/import")
    @Operation(summary = "导入综合订单")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('distribution:comprehensive-order-info:import')")
    public CommonResult<ComprehensiveOrderInfoImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                      @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        return success(comprehensiveOrderInfoService.importExcel(file, updateSupport));
    }

    @PutMapping("/assign-order-code")
    @Operation(summary = "生成订单明细编码")
    @PreAuthorize("@ss.hasPermission('distribution:comprehensive-order-info:assigncode')")
    public CommonResult<Boolean> assignOrderInfoCode(@RequestParam("ids") Collection<Long> ids) throws IOException {
        comprehensiveOrderInfoService.assignOrderInfoCode(ids);
        return success(true);
    }


}
