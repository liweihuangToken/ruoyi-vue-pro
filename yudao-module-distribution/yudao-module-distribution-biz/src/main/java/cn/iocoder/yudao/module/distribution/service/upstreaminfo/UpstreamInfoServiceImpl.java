package cn.iocoder.yudao.module.distribution.service.upstreaminfo;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.distribution.controller.admin.upstreaminfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.upstreaminfo.UpstreamInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.distribution.convert.upstreaminfo.UpstreamInfoConvert;
import cn.iocoder.yudao.module.distribution.dal.mysql.upstreaminfo.UpstreamInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.distribution.enums.ErrorCodeConstants.*;

/**
 * 配货上游信息 Service 实现类
 *
 * @author 李伟煌
 */
@Service
@Validated
public class UpstreamInfoServiceImpl implements UpstreamInfoService {

    @Resource
    private UpstreamInfoMapper upstreamInfoMapper;

    @Override
    public Long createUpstreamInfo(UpstreamInfoCreateReqVO createReqVO) {
        // 插入
        UpstreamInfoDO upstreamInfo = UpstreamInfoConvert.INSTANCE.convert(createReqVO);
        upstreamInfoMapper.insert(upstreamInfo);
        // 返回
        return upstreamInfo.getId();
    }

    @Override
    public void updateUpstreamInfo(UpstreamInfoUpdateReqVO updateReqVO) {
        // 校验存在
        validateUpstreamInfoExists(updateReqVO.getId());
        // 更新
        UpstreamInfoDO updateObj = UpstreamInfoConvert.INSTANCE.convert(updateReqVO);
        upstreamInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteUpstreamInfo(Long id) {
        // 校验存在
        validateUpstreamInfoExists(id);
        // 删除
        upstreamInfoMapper.deleteById(id);
    }

    private void validateUpstreamInfoExists(Long id) {
        if (upstreamInfoMapper.selectById(id) == null) {
            throw exception(UPSTREAM_INFO_NOT_EXISTS);
        }
    }

    @Override
    public UpstreamInfoDO getUpstreamInfo(Long id) {
        return upstreamInfoMapper.selectById(id);
    }

    @Override
    public List<UpstreamInfoDO> getUpstreamInfoList(Collection<Long> ids) {
        return upstreamInfoMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<UpstreamInfoDO> getUpstreamInfoPage(UpstreamInfoPageReqVO pageReqVO) {
        return upstreamInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<UpstreamInfoDO> getUpstreamInfoList(UpstreamInfoExportReqVO exportReqVO) {
        return upstreamInfoMapper.selectList(exportReqVO);
    }

}
