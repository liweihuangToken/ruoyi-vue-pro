package cn.iocoder.yudao.module.distribution.service.downstreaminfo;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import cn.iocoder.yudao.module.distribution.controller.admin.downstreaminfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.downstreaminfo.DownstreamInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.distribution.convert.downstreaminfo.DownstreamInfoConvert;
import cn.iocoder.yudao.module.distribution.dal.mysql.downstreaminfo.DownstreamInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.distribution.enums.ErrorCodeConstants.*;

/**
 * 配货下游信息 Service 实现类
 *
 * @author 李伟煌
 */
@Service
@Validated
public class DownstreamInfoServiceImpl implements DownstreamInfoService {

    @Resource
    private DownstreamInfoMapper downstreamInfoMapper;

    @Override
    public Long createDownstreamInfo(DownstreamInfoCreateReqVO createReqVO) {
        // 插入
        DownstreamInfoDO downstreamInfo = DownstreamInfoConvert.INSTANCE.convert(createReqVO);
        downstreamInfoMapper.insert(downstreamInfo);
        // 返回
        return downstreamInfo.getId();
    }

    @Override
    public void updateDownstreamInfo(DownstreamInfoUpdateReqVO updateReqVO) {
        // 校验存在
        validateDownstreamInfoExists(updateReqVO.getId());
        // 更新
        DownstreamInfoDO updateObj = DownstreamInfoConvert.INSTANCE.convert(updateReqVO);
        downstreamInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteDownstreamInfo(Long id) {
        // 校验存在
        validateDownstreamInfoExists(id);
        // 删除
        downstreamInfoMapper.deleteById(id);
    }

    private void validateDownstreamInfoExists(Long id) {
        if (downstreamInfoMapper.selectById(id) == null) {
            throw exception(DOWNSTREAM_INFO_NOT_EXISTS);
        }
    }

    @Override
    public DownstreamInfoDO getDownstreamInfo(Long id) {
        return downstreamInfoMapper.selectById(id);
    }

    @Override
    public List<DownstreamInfoDO> getDownstreamInfoList(Collection<Long> ids) {
        return downstreamInfoMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DownstreamInfoDO> getDownstreamInfoPage(DownstreamInfoPageReqVO pageReqVO) {
        return downstreamInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DownstreamInfoDO> getDownstreamInfoList(DownstreamInfoExportReqVO exportReqVO) {
        return downstreamInfoMapper.selectList(exportReqVO);
    }

}
