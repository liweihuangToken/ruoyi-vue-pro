package cn.iocoder.yudao.module.distribution.service.upstreaminfo;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.distribution.controller.admin.upstreaminfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.upstreaminfo.UpstreamInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 配货上游信息 Service 接口
 *
 * @author 李伟煌
 */
public interface UpstreamInfoService {

    /**
     * 创建配货上游信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createUpstreamInfo(@Valid UpstreamInfoCreateReqVO createReqVO);

    /**
     * 更新配货上游信息
     *
     * @param updateReqVO 更新信息
     */
    void updateUpstreamInfo(@Valid UpstreamInfoUpdateReqVO updateReqVO);

    /**
     * 删除配货上游信息
     *
     * @param id 编号
     */
    void deleteUpstreamInfo(Long id);

    /**
     * 获得配货上游信息
     *
     * @param id 编号
     * @return 配货上游信息
     */
    UpstreamInfoDO getUpstreamInfo(Long id);

    /**
     * 获得配货上游信息列表
     *
     * @param ids 编号
     * @return 配货上游信息列表
     */
    List<UpstreamInfoDO> getUpstreamInfoList(Collection<Long> ids);

    /**
     * 获得配货上游信息分页
     *
     * @param pageReqVO 分页查询
     * @return 配货上游信息分页
     */
    PageResult<UpstreamInfoDO> getUpstreamInfoPage(UpstreamInfoPageReqVO pageReqVO);

    /**
     * 获得配货上游信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 配货上游信息列表
     */
    List<UpstreamInfoDO> getUpstreamInfoList(UpstreamInfoExportReqVO exportReqVO);

}
