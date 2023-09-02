package cn.iocoder.yudao.module.distribution.service.upstreaminfo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.distribution.controller.admin.upstreaminfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.upstreaminfo.UpstreamInfoDO;
import cn.iocoder.yudao.module.distribution.dal.mysql.upstreaminfo.UpstreamInfoMapper;
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
 * {@link UpstreamInfoServiceImpl} 的单元测试类
 *
 * @author 李伟煌
 */
@Import(UpstreamInfoServiceImpl.class)
public class UpstreamInfoServiceImplTest extends BaseDbUnitTest {

    @Resource
    private UpstreamInfoServiceImpl upstreamInfoService;

    @Resource
    private UpstreamInfoMapper upstreamInfoMapper;

    @Test
    public void testCreateUpstreamInfo_success() {
        // 准备参数
        UpstreamInfoCreateReqVO reqVO = randomPojo(UpstreamInfoCreateReqVO.class);

        // 调用
        Long upstreamInfoId = upstreamInfoService.createUpstreamInfo(reqVO);
        // 断言
        assertNotNull(upstreamInfoId);
        // 校验记录的属性是否正确
        UpstreamInfoDO upstreamInfo = upstreamInfoMapper.selectById(upstreamInfoId);
        assertPojoEquals(reqVO, upstreamInfo);
    }

    @Test
    public void testUpdateUpstreamInfo_success() {
        // mock 数据
        UpstreamInfoDO dbUpstreamInfo = randomPojo(UpstreamInfoDO.class);
        upstreamInfoMapper.insert(dbUpstreamInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        UpstreamInfoUpdateReqVO reqVO = randomPojo(UpstreamInfoUpdateReqVO.class, o -> {
            o.setId(dbUpstreamInfo.getId()); // 设置更新的 ID
        });

        // 调用
        upstreamInfoService.updateUpstreamInfo(reqVO);
        // 校验是否更新正确
        UpstreamInfoDO upstreamInfo = upstreamInfoMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, upstreamInfo);
    }

    @Test
    public void testUpdateUpstreamInfo_notExists() {
        // 准备参数
        UpstreamInfoUpdateReqVO reqVO = randomPojo(UpstreamInfoUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> upstreamInfoService.updateUpstreamInfo(reqVO), UPSTREAM_INFO_NOT_EXISTS);
    }

    @Test
    public void testDeleteUpstreamInfo_success() {
        // mock 数据
        UpstreamInfoDO dbUpstreamInfo = randomPojo(UpstreamInfoDO.class);
        upstreamInfoMapper.insert(dbUpstreamInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbUpstreamInfo.getId();

        // 调用
        upstreamInfoService.deleteUpstreamInfo(id);
       // 校验数据不存在了
       assertNull(upstreamInfoMapper.selectById(id));
    }

    @Test
    public void testDeleteUpstreamInfo_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> upstreamInfoService.deleteUpstreamInfo(id), UPSTREAM_INFO_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetUpstreamInfoPage() {
       // mock 数据
       UpstreamInfoDO dbUpstreamInfo = randomPojo(UpstreamInfoDO.class, o -> { // 等会查询到
           o.setUpstreamCode(null);
           o.setUpstreamName(null);
           o.setUpstreamAlias(null);
           o.setUpstreamAddress(null);
           o.setPickupMethod(null);
           o.setNote(null);
           o.setCreateTime(null);
           o.setPickupSort(null);
       });
       upstreamInfoMapper.insert(dbUpstreamInfo);
       // 测试 upstreamCode 不匹配
       upstreamInfoMapper.insert(cloneIgnoreId(dbUpstreamInfo, o -> o.setUpstreamCode(null)));
       // 测试 upstreamName 不匹配
       upstreamInfoMapper.insert(cloneIgnoreId(dbUpstreamInfo, o -> o.setUpstreamName(null)));
       // 测试 upstreamAlias 不匹配
       upstreamInfoMapper.insert(cloneIgnoreId(dbUpstreamInfo, o -> o.setUpstreamAlias(null)));
       // 测试 upstreamAddress 不匹配
       upstreamInfoMapper.insert(cloneIgnoreId(dbUpstreamInfo, o -> o.setUpstreamAddress(null)));
       // 测试 pickupMethod 不匹配
       upstreamInfoMapper.insert(cloneIgnoreId(dbUpstreamInfo, o -> o.setPickupMethod(null)));
       // 测试 note 不匹配
       upstreamInfoMapper.insert(cloneIgnoreId(dbUpstreamInfo, o -> o.setNote(null)));
       // 测试 createTime 不匹配
       upstreamInfoMapper.insert(cloneIgnoreId(dbUpstreamInfo, o -> o.setCreateTime(null)));
       // 测试 pickupSort 不匹配
       upstreamInfoMapper.insert(cloneIgnoreId(dbUpstreamInfo, o -> o.setPickupSort(null)));
       // 准备参数
       UpstreamInfoPageReqVO reqVO = new UpstreamInfoPageReqVO();
       reqVO.setUpstreamCode(null);
       reqVO.setUpstreamName(null);
       reqVO.setUpstreamAlias(null);
       reqVO.setUpstreamAddress(null);
       reqVO.setPickupMethod(null);
       reqVO.setNote(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setPickupSort(null);

       // 调用
       PageResult<UpstreamInfoDO> pageResult = upstreamInfoService.getUpstreamInfoPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbUpstreamInfo, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetUpstreamInfoList() {
       // mock 数据
       UpstreamInfoDO dbUpstreamInfo = randomPojo(UpstreamInfoDO.class, o -> { // 等会查询到
           o.setUpstreamCode(null);
           o.setUpstreamName(null);
           o.setUpstreamAlias(null);
           o.setUpstreamAddress(null);
           o.setPickupMethod(null);
           o.setNote(null);
           o.setCreateTime(null);
           o.setPickupSort(null);
       });
       upstreamInfoMapper.insert(dbUpstreamInfo);
       // 测试 upstreamCode 不匹配
       upstreamInfoMapper.insert(cloneIgnoreId(dbUpstreamInfo, o -> o.setUpstreamCode(null)));
       // 测试 upstreamName 不匹配
       upstreamInfoMapper.insert(cloneIgnoreId(dbUpstreamInfo, o -> o.setUpstreamName(null)));
       // 测试 upstreamAlias 不匹配
       upstreamInfoMapper.insert(cloneIgnoreId(dbUpstreamInfo, o -> o.setUpstreamAlias(null)));
       // 测试 upstreamAddress 不匹配
       upstreamInfoMapper.insert(cloneIgnoreId(dbUpstreamInfo, o -> o.setUpstreamAddress(null)));
       // 测试 pickupMethod 不匹配
       upstreamInfoMapper.insert(cloneIgnoreId(dbUpstreamInfo, o -> o.setPickupMethod(null)));
       // 测试 note 不匹配
       upstreamInfoMapper.insert(cloneIgnoreId(dbUpstreamInfo, o -> o.setNote(null)));
       // 测试 createTime 不匹配
       upstreamInfoMapper.insert(cloneIgnoreId(dbUpstreamInfo, o -> o.setCreateTime(null)));
       // 测试 pickupSort 不匹配
       upstreamInfoMapper.insert(cloneIgnoreId(dbUpstreamInfo, o -> o.setPickupSort(null)));
       // 准备参数
       UpstreamInfoExportReqVO reqVO = new UpstreamInfoExportReqVO();
       reqVO.setUpstreamCode(null);
       reqVO.setUpstreamName(null);
       reqVO.setUpstreamAlias(null);
       reqVO.setUpstreamAddress(null);
       reqVO.setPickupMethod(null);
       reqVO.setNote(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setPickupSort(null);

       // 调用
       List<UpstreamInfoDO> list = upstreamInfoService.getUpstreamInfoList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbUpstreamInfo, list.get(0));
    }

}
