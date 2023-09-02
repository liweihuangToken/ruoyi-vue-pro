package cn.iocoder.yudao.module.distribution.service.downstreaminfo;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.distribution.controller.admin.downstreaminfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.downstreaminfo.DownstreamInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 配货下游信息 Service 接口
 *
 * @author 李伟煌
 */
public interface DownstreamInfoService {

    /**
     * 创建配货下游信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDownstreamInfo(@Valid DownstreamInfoCreateReqVO createReqVO);

    /**
     * 更新配货下游信息
     *
     * @param updateReqVO 更新信息
     */
    void updateDownstreamInfo(@Valid DownstreamInfoUpdateReqVO updateReqVO);

    /**
     * 删除配货下游信息
     *
     * @param id 编号
     */
    void deleteDownstreamInfo(Long id);

    /**
     * 获得配货下游信息
     *
     * @param id 编号
     * @return 配货下游信息
     */
    DownstreamInfoDO getDownstreamInfo(Long id);

    /**
     * 获得配货下游信息列表
     *
     * @param ids 编号
     * @return 配货下游信息列表
     */
    List<DownstreamInfoDO> getDownstreamInfoList(Collection<Long> ids);

    /**
     * 获得配货下游信息分页
     *
     * @param pageReqVO 分页查询
     * @return 配货下游信息分页
     */
    PageResult<DownstreamInfoDO> getDownstreamInfoPage(DownstreamInfoPageReqVO pageReqVO);

    /**
     * 获得配货下游信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 配货下游信息列表
     */
    List<DownstreamInfoDO> getDownstreamInfoList(DownstreamInfoExportReqVO exportReqVO);

}
