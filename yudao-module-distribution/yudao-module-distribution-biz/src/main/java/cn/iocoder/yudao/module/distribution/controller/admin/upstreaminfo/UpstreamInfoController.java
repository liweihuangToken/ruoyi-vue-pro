package cn.iocoder.yudao.module.distribution.controller.admin.upstreaminfo;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.distribution.controller.admin.upstreaminfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.upstreaminfo.UpstreamInfoDO;
import cn.iocoder.yudao.module.distribution.convert.upstreaminfo.UpstreamInfoConvert;
import cn.iocoder.yudao.module.distribution.service.upstreaminfo.UpstreamInfoService;

@Tag(name = "管理后台 - 配货上游信息")
@RestController
@RequestMapping("/distribution/upstream-info")
@Validated
public class UpstreamInfoController {

    @Resource
    private UpstreamInfoService upstreamInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建配货上游信息")
    @PreAuthorize("@ss.hasPermission('distribution:upstream-info:create')")
    public CommonResult<Long> createUpstreamInfo(@Valid @RequestBody UpstreamInfoCreateReqVO createReqVO) {
        return success(upstreamInfoService.createUpstreamInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新配货上游信息")
    @PreAuthorize("@ss.hasPermission('distribution:upstream-info:update')")
    public CommonResult<Boolean> updateUpstreamInfo(@Valid @RequestBody UpstreamInfoUpdateReqVO updateReqVO) {
        upstreamInfoService.updateUpstreamInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除配货上游信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('distribution:upstream-info:delete')")
    public CommonResult<Boolean> deleteUpstreamInfo(@RequestParam("id") Long id) {
        upstreamInfoService.deleteUpstreamInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得配货上游信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('distribution:upstream-info:query')")
    public CommonResult<UpstreamInfoRespVO> getUpstreamInfo(@RequestParam("id") Long id) {
        UpstreamInfoDO upstreamInfo = upstreamInfoService.getUpstreamInfo(id);
        return success(UpstreamInfoConvert.INSTANCE.convert(upstreamInfo));
    }

    @GetMapping("/list")
    @Operation(summary = "获得配货上游信息列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('distribution:upstream-info:query')")
    public CommonResult<List<UpstreamInfoRespVO>> getUpstreamInfoList(@RequestParam("ids") Collection<Long> ids) {
        List<UpstreamInfoDO> list = upstreamInfoService.getUpstreamInfoList(ids);
        return success(UpstreamInfoConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得配货上游信息分页")
    @PreAuthorize("@ss.hasPermission('distribution:upstream-info:query')")
    public CommonResult<PageResult<UpstreamInfoRespVO>> getUpstreamInfoPage(@Valid UpstreamInfoPageReqVO pageVO) {
        PageResult<UpstreamInfoDO> pageResult = upstreamInfoService.getUpstreamInfoPage(pageVO);
        return success(UpstreamInfoConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出配货上游信息 Excel")
    @PreAuthorize("@ss.hasPermission('distribution:upstream-info:export')")
    @OperateLog(type = EXPORT)
    public void exportUpstreamInfoExcel(@Valid UpstreamInfoExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<UpstreamInfoDO> list = upstreamInfoService.getUpstreamInfoList(exportReqVO);
        // 导出 Excel
        List<UpstreamInfoExcelVO> datas = UpstreamInfoConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "配货上游信息.xls", "数据", UpstreamInfoExcelVO.class, datas);
    }

}
