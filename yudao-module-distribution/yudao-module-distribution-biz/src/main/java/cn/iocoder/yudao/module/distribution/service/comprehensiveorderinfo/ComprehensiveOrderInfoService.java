package cn.iocoder.yudao.module.distribution.service.comprehensiveorderinfo;

import java.io.IOException;
import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.distribution.controller.admin.comprehensiveorderinfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.comprehensiveorderinfo.ComprehensiveOrderInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 配货综合订单信息 Service 接口
 *
 * @author 李伟煌
 */
public interface ComprehensiveOrderInfoService {

    /**
     * 创建配货综合订单信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createComprehensiveOrderInfo(@Valid ComprehensiveOrderInfoCreateReqVO createReqVO);

    /**
     * 更新配货综合订单信息
     *
     * @param updateReqVO 更新信息
     */
    void updateComprehensiveOrderInfo(@Valid ComprehensiveOrderInfoUpdateReqVO updateReqVO);

    /**
     * 删除配货综合订单信息
     *
     * @param id 编号
     */
    void deleteComprehensiveOrderInfo(Long id);

    /**
     * 获得配货综合订单信息
     *
     * @param id 编号
     * @return 配货综合订单信息
     */
    ComprehensiveOrderInfoDO getComprehensiveOrderInfo(Long id);

    /**
     * 获得配货综合订单信息列表
     *
     * @param ids 编号
     * @return 配货综合订单信息列表
     */
    List<ComprehensiveOrderInfoDO> getComprehensiveOrderInfoList(Collection<Long> ids);

    /**
     * 获得配货综合订单信息分页
     *
     * @param pageReqVO 分页查询
     * @return 配货综合订单信息分页
     */
    PageResult<ComprehensiveOrderInfoDO> getComprehensiveOrderInfoPage(ComprehensiveOrderInfoPageReqVO pageReqVO);

    /**
     * 获得配货综合订单信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 配货综合订单信息列表
     */
    List<ComprehensiveOrderInfoDO> getComprehensiveOrderInfoList(ComprehensiveOrderInfoExportReqVO exportReqVO);

    /**
     * 导入综合订单信息
     *
     * @param file     导入综合订单Excel文件
     * @param isUpdateSupport 是否支持更新
     * @return 导入结果
     */
    ComprehensiveOrderInfoImportRespVO importExcel(MultipartFile file, Boolean isUpdateSupport) throws Exception;

    /**
     * 生成订单明明细编码
     * @param ids
     * @return
     */
    void assignOrderInfoCode(Collection<Long> ids) throws IOException;
}
