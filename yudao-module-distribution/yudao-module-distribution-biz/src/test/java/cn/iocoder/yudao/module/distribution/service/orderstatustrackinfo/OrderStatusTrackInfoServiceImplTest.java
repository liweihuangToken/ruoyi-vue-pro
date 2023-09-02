package cn.iocoder.yudao.module.distribution.service.orderstatustrackinfo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.distribution.controller.admin.orderstatustrackinfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.orderstatustrackinfo.OrderStatusTrackInfoDO;
import cn.iocoder.yudao.module.distribution.dal.mysql.orderstatustrackinfo.OrderStatusTrackInfoMapper;
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
 * {@link OrderStatusTrackInfoServiceImpl} 的单元测试类
 *
 * @author 李伟煌
 */
@Import(OrderStatusTrackInfoServiceImpl.class)
public class OrderStatusTrackInfoServiceImplTest extends BaseDbUnitTest {

    @Resource
    private OrderStatusTrackInfoServiceImpl orderStatusTrackInfoService;

    @Resource
    private OrderStatusTrackInfoMapper orderStatusTrackInfoMapper;

    @Test
    public void testCreateOrderStatusTrackInfo_success() {
        // 准备参数
        OrderStatusTrackInfoCreateReqVO reqVO = randomPojo(OrderStatusTrackInfoCreateReqVO.class);

        // 调用
        Long orderStatusTrackInfoId = orderStatusTrackInfoService.createOrderStatusTrackInfo(reqVO);
        // 断言
        assertNotNull(orderStatusTrackInfoId);
        // 校验记录的属性是否正确
        OrderStatusTrackInfoDO orderStatusTrackInfo = orderStatusTrackInfoMapper.selectById(orderStatusTrackInfoId);
        assertPojoEquals(reqVO, orderStatusTrackInfo);
    }

    @Test
    public void testUpdateOrderStatusTrackInfo_success() {
        // mock 数据
        OrderStatusTrackInfoDO dbOrderStatusTrackInfo = randomPojo(OrderStatusTrackInfoDO.class);
        orderStatusTrackInfoMapper.insert(dbOrderStatusTrackInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        OrderStatusTrackInfoUpdateReqVO reqVO = randomPojo(OrderStatusTrackInfoUpdateReqVO.class, o -> {
            o.setId(dbOrderStatusTrackInfo.getId()); // 设置更新的 ID
        });

        // 调用
        orderStatusTrackInfoService.updateOrderStatusTrackInfo(reqVO);
        // 校验是否更新正确
        OrderStatusTrackInfoDO orderStatusTrackInfo = orderStatusTrackInfoMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, orderStatusTrackInfo);
    }

    @Test
    public void testUpdateOrderStatusTrackInfo_notExists() {
        // 准备参数
        OrderStatusTrackInfoUpdateReqVO reqVO = randomPojo(OrderStatusTrackInfoUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> orderStatusTrackInfoService.updateOrderStatusTrackInfo(reqVO), ORDER_STATUS_TRACK_INFO_NOT_EXISTS);
    }

    @Test
    public void testDeleteOrderStatusTrackInfo_success() {
        // mock 数据
        OrderStatusTrackInfoDO dbOrderStatusTrackInfo = randomPojo(OrderStatusTrackInfoDO.class);
        orderStatusTrackInfoMapper.insert(dbOrderStatusTrackInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbOrderStatusTrackInfo.getId();

        // 调用
        orderStatusTrackInfoService.deleteOrderStatusTrackInfo(id);
       // 校验数据不存在了
       assertNull(orderStatusTrackInfoMapper.selectById(id));
    }

    @Test
    public void testDeleteOrderStatusTrackInfo_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> orderStatusTrackInfoService.deleteOrderStatusTrackInfo(id), ORDER_STATUS_TRACK_INFO_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetOrderStatusTrackInfoPage() {
       // mock 数据
       OrderStatusTrackInfoDO dbOrderStatusTrackInfo = randomPojo(OrderStatusTrackInfoDO.class, o -> { // 等会查询到
           o.setOrderCode(null);
           o.setOrderAfterChangeStatus(null);
           o.setCreateTime(null);
       });
       orderStatusTrackInfoMapper.insert(dbOrderStatusTrackInfo);
       // 测试 orderCode 不匹配
       orderStatusTrackInfoMapper.insert(cloneIgnoreId(dbOrderStatusTrackInfo, o -> o.setOrderCode(null)));
       // 测试 orderAfterChangeStatus 不匹配
       orderStatusTrackInfoMapper.insert(cloneIgnoreId(dbOrderStatusTrackInfo, o -> o.setOrderAfterChangeStatus(null)));
       // 测试 createTime 不匹配
       orderStatusTrackInfoMapper.insert(cloneIgnoreId(dbOrderStatusTrackInfo, o -> o.setCreateTime(null)));
       // 准备参数
       OrderStatusTrackInfoPageReqVO reqVO = new OrderStatusTrackInfoPageReqVO();
       reqVO.setOrderCode(null);
       reqVO.setOrderAfterChangeStatus(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<OrderStatusTrackInfoDO> pageResult = orderStatusTrackInfoService.getOrderStatusTrackInfoPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbOrderStatusTrackInfo, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetOrderStatusTrackInfoList() {
       // mock 数据
       OrderStatusTrackInfoDO dbOrderStatusTrackInfo = randomPojo(OrderStatusTrackInfoDO.class, o -> { // 等会查询到
           o.setOrderCode(null);
           o.setOrderAfterChangeStatus(null);
           o.setCreateTime(null);
       });
       orderStatusTrackInfoMapper.insert(dbOrderStatusTrackInfo);
       // 测试 orderCode 不匹配
       orderStatusTrackInfoMapper.insert(cloneIgnoreId(dbOrderStatusTrackInfo, o -> o.setOrderCode(null)));
       // 测试 orderAfterChangeStatus 不匹配
       orderStatusTrackInfoMapper.insert(cloneIgnoreId(dbOrderStatusTrackInfo, o -> o.setOrderAfterChangeStatus(null)));
       // 测试 createTime 不匹配
       orderStatusTrackInfoMapper.insert(cloneIgnoreId(dbOrderStatusTrackInfo, o -> o.setCreateTime(null)));
       // 准备参数
       OrderStatusTrackInfoExportReqVO reqVO = new OrderStatusTrackInfoExportReqVO();
       reqVO.setOrderCode(null);
       reqVO.setOrderAfterChangeStatus(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<OrderStatusTrackInfoDO> list = orderStatusTrackInfoService.getOrderStatusTrackInfoList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbOrderStatusTrackInfo, list.get(0));
    }

}
