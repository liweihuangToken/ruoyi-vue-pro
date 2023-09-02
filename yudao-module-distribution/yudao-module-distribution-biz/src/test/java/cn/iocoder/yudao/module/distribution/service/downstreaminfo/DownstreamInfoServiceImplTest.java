package cn.iocoder.yudao.module.distribution.service.downstreaminfo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.distribution.controller.admin.downstreaminfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.downstreaminfo.DownstreamInfoDO;
import cn.iocoder.yudao.module.distribution.dal.mysql.downstreaminfo.DownstreamInfoMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static cn.iocoder.yudao.module.distribution.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.test.core.util.AssertUtils.*;
import static cn.iocoder.yudao.framework.test.core.util.RandomUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.LocalDateTimeUtils.*;
import static cn.iocoder.yudao.framework.common.util.object.ObjectUtils.*;
import static cn.iocoder.yudao.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link DownstreamInfoServiceImpl} 的单元测试类
 *
 * @author 李伟煌
 */
@Import(DownstreamInfoServiceImpl.class)
public class DownstreamInfoServiceImplTest extends BaseDbUnitTest {

    @Resource
    private DownstreamInfoServiceImpl downstreamInfoService;

    @Resource
    private DownstreamInfoMapper downstreamInfoMapper;

    @Test
    public void testCreateDownstreamInfo_success() {
        // 准备参数
        DownstreamInfoCreateReqVO reqVO = randomPojo(DownstreamInfoCreateReqVO.class);

        // 调用
        Long downstreamInfoId = downstreamInfoService.createDownstreamInfo(reqVO);
        // 断言
        assertNotNull(downstreamInfoId);
        // 校验记录的属性是否正确
        DownstreamInfoDO downstreamInfo = downstreamInfoMapper.selectById(downstreamInfoId);
        assertPojoEquals(reqVO, downstreamInfo);
    }

    @Test
    public void testUpdateDownstreamInfo_success() {
        // mock 数据
        DownstreamInfoDO dbDownstreamInfo = randomPojo(DownstreamInfoDO.class);
        downstreamInfoMapper.insert(dbDownstreamInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        DownstreamInfoUpdateReqVO reqVO = randomPojo(DownstreamInfoUpdateReqVO.class, o -> {
            o.setId(dbDownstreamInfo.getId()); // 设置更新的 ID
        });

        // 调用
        downstreamInfoService.updateDownstreamInfo(reqVO);
        // 校验是否更新正确
        DownstreamInfoDO downstreamInfo = downstreamInfoMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, downstreamInfo);
    }

    @Test
    public void testUpdateDownstreamInfo_notExists() {
        // 准备参数
        DownstreamInfoUpdateReqVO reqVO = randomPojo(DownstreamInfoUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> downstreamInfoService.updateDownstreamInfo(reqVO), DOWNSTREAM_INFO_NOT_EXISTS);
    }

    @Test
    public void testDeleteDownstreamInfo_success() {
        // mock 数据
        DownstreamInfoDO dbDownstreamInfo = randomPojo(DownstreamInfoDO.class);
        downstreamInfoMapper.insert(dbDownstreamInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbDownstreamInfo.getId();

        // 调用
        downstreamInfoService.deleteDownstreamInfo(id);
       // 校验数据不存在了
       assertNull(downstreamInfoMapper.selectById(id));
    }

    @Test
    public void testDeleteDownstreamInfo_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> downstreamInfoService.deleteDownstreamInfo(id), DOWNSTREAM_INFO_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetDownstreamInfoPage() {
       // mock 数据
       DownstreamInfoDO dbDownstreamInfo = randomPojo(DownstreamInfoDO.class, o -> { // 等会查询到
           o.setDownstreamCode(null);
           o.setDownstreamName(null);
           o.setDownstreamAlias(null);
           o.setDownstreamAddress(null);
           o.setDeliveryMethod(null);
           o.setNote(null);
           o.setCreateTime(null);
           o.setDeliverySort(null);
       });
       downstreamInfoMapper.insert(dbDownstreamInfo);
       // 测试 downstreamCode 不匹配
       downstreamInfoMapper.insert(cloneIgnoreId(dbDownstreamInfo, o -> o.setDownstreamCode(null)));
       // 测试 downstreamName 不匹配
       downstreamInfoMapper.insert(cloneIgnoreId(dbDownstreamInfo, o -> o.setDownstreamName(null)));
       // 测试 downstreamAlias 不匹配
       downstreamInfoMapper.insert(cloneIgnoreId(dbDownstreamInfo, o -> o.setDownstreamAlias(null)));
       // 测试 downstreamAddress 不匹配
       downstreamInfoMapper.insert(cloneIgnoreId(dbDownstreamInfo, o -> o.setDownstreamAddress(null)));
       // 测试 deliveryMethod 不匹配
       downstreamInfoMapper.insert(cloneIgnoreId(dbDownstreamInfo, o -> o.setDeliveryMethod(null)));
       // 测试 note 不匹配
       downstreamInfoMapper.insert(cloneIgnoreId(dbDownstreamInfo, o -> o.setNote(null)));
       // 测试 createTime 不匹配
       downstreamInfoMapper.insert(cloneIgnoreId(dbDownstreamInfo, o -> o.setCreateTime(null)));
       // 测试 deliverySort 不匹配
       downstreamInfoMapper.insert(cloneIgnoreId(dbDownstreamInfo, o -> o.setDeliverySort(null)));
       // 准备参数
       DownstreamInfoPageReqVO reqVO = new DownstreamInfoPageReqVO();
       reqVO.setDownstreamCode(null);
       reqVO.setDownstreamName(null);
       reqVO.setDownstreamAlias(null);
       reqVO.setDownstreamAddress(null);
       reqVO.setDeliveryMethod(null);
       reqVO.setNote(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setDeliverySort(null);

       // 调用
       PageResult<DownstreamInfoDO> pageResult = downstreamInfoService.getDownstreamInfoPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbDownstreamInfo, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetDownstreamInfoList() {
       // mock 数据
       DownstreamInfoDO dbDownstreamInfo = randomPojo(DownstreamInfoDO.class, o -> { // 等会查询到
           o.setDownstreamCode(null);
           o.setDownstreamName(null);
           o.setDownstreamAlias(null);
           o.setDownstreamAddress(null);
           o.setDeliveryMethod(null);
           o.setNote(null);
           o.setCreateTime(null);
           o.setDeliverySort(null);
       });
       downstreamInfoMapper.insert(dbDownstreamInfo);
       // 测试 downstreamCode 不匹配
       downstreamInfoMapper.insert(cloneIgnoreId(dbDownstreamInfo, o -> o.setDownstreamCode(null)));
       // 测试 downstreamName 不匹配
       downstreamInfoMapper.insert(cloneIgnoreId(dbDownstreamInfo, o -> o.setDownstreamName(null)));
       // 测试 downstreamAlias 不匹配
       downstreamInfoMapper.insert(cloneIgnoreId(dbDownstreamInfo, o -> o.setDownstreamAlias(null)));
       // 测试 downstreamAddress 不匹配
       downstreamInfoMapper.insert(cloneIgnoreId(dbDownstreamInfo, o -> o.setDownstreamAddress(null)));
       // 测试 deliveryMethod 不匹配
       downstreamInfoMapper.insert(cloneIgnoreId(dbDownstreamInfo, o -> o.setDeliveryMethod(null)));
       // 测试 note 不匹配
       downstreamInfoMapper.insert(cloneIgnoreId(dbDownstreamInfo, o -> o.setNote(null)));
       // 测试 createTime 不匹配
       downstreamInfoMapper.insert(cloneIgnoreId(dbDownstreamInfo, o -> o.setCreateTime(null)));
       // 测试 deliverySort 不匹配
       downstreamInfoMapper.insert(cloneIgnoreId(dbDownstreamInfo, o -> o.setDeliverySort(null)));
       // 准备参数
       DownstreamInfoExportReqVO reqVO = new DownstreamInfoExportReqVO();
       reqVO.setDownstreamCode(null);
       reqVO.setDownstreamName(null);
       reqVO.setDownstreamAlias(null);
       reqVO.setDownstreamAddress(null);
       reqVO.setDeliveryMethod(null);
       reqVO.setNote(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setDeliverySort(null);

       // 调用
       List<DownstreamInfoDO> list = downstreamInfoService.getDownstreamInfoList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbDownstreamInfo, list.get(0));
    }

}
