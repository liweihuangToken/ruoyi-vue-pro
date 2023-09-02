package cn.iocoder.yudao.module.distribution.controller.admin.downstreaminfo;

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

import cn.iocoder.yudao.module.distribution.controller.admin.downstreaminfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.downstreaminfo.DownstreamInfoDO;
import cn.iocoder.yudao.module.distribution.convert.downstreaminfo.DownstreamInfoConvert;
import cn.iocoder.yudao.module.distribution.service.downstreaminfo.DownstreamInfoService;

@Tag(name = "管理后台 - 配货下游信息")
@RestController
@RequestMapping("/distribution/downstream-info")
@Validated
public class DownstreamInfoController {

    @Resource
    private DownstreamInfoService downstreamInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建配货下游信息")
    @PreAuthorize("@ss.hasPermission('distribution:downstream-info:create')")
    public CommonResult<Long> createDownstreamInfo(@Valid @RequestBody DownstreamInfoCreateReqVO createReqVO) {
        return success(downstreamInfoService.createDownstreamInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新配货下游信息")
    @PreAuthorize("@ss.hasPermission('distribution:downstream-info:update')")
    public CommonResult<Boolean> updateDownstreamInfo(@Valid @RequestBody DownstreamInfoUpdateReqVO updateReqVO) {
        downstreamInfoService.updateDownstreamInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除配货下游信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('distribution:downstream-info:delete')")
    public CommonResult<Boolean> deleteDownstreamInfo(@RequestParam("id") Long id) {
        downstreamInfoService.deleteDownstreamInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得配货下游信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('distribution:downstream-info:query')")
    public CommonResult<DownstreamInfoRespVO> getDownstreamInfo(@RequestParam("id") Long id) {
        DownstreamInfoDO downstreamInfo = downstreamInfoService.getDownstreamInfo(id);
        return success(DownstreamInfoConvert.INSTANCE.convert(downstreamInfo));
    }

    @GetMapping("/list")
    @Operation(summary = "获得配货下游信息列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('distribution:downstream-info:query')")
    public CommonResult<List<DownstreamInfoRespVO>> getDownstreamInfoList(@RequestParam("ids") Collection<Long> ids) {
        List<DownstreamInfoDO> list = downstreamInfoService.getDownstreamInfoList(ids);
        return success(DownstreamInfoConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得配货下游信息分页")
    @PreAuthorize("@ss.hasPermission('distribution:downstream-info:query')")
    public CommonResult<PageResult<DownstreamInfoRespVO>> getDownstreamInfoPage(@Valid DownstreamInfoPageReqVO pageVO) {
        PageResult<DownstreamInfoDO> pageResult = downstreamInfoService.getDownstreamInfoPage(pageVO);
        return success(DownstreamInfoConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出配货下游信息 Excel")
    @PreAuthorize("@ss.hasPermission('distribution:downstream-info:export')")
    @OperateLog(type = EXPORT)
    public void exportDownstreamInfoExcel(@Valid DownstreamInfoExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<DownstreamInfoDO> list = downstreamInfoService.getDownstreamInfoList(exportReqVO);
        // 导出 Excel
        List<DownstreamInfoExcelVO> datas = DownstreamInfoConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "配货下游信息.xls", "数据", DownstreamInfoExcelVO.class, datas);
    }

}
