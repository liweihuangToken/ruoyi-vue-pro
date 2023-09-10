package cn.iocoder.yudao.module.distribution.service.orderdetailinfo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.orderdetailinfo.OrderDetailInfoDO;
import cn.iocoder.yudao.module.distribution.dal.mysql.orderdetailinfo.OrderDetailInfoMapper;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;

import java.io.IOException;
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
 * {@link OrderDetailInfoServiceImpl} 的单元测试类
 *
 * @author 李伟煌
 */
@Import(OrderDetailInfoServiceImpl.class)
public class OrderDetailInfoServiceImplTest extends BaseDbUnitTest {

    @Resource
    private OrderDetailInfoServiceImpl orderDetailInfoService;

    @Resource
    private OrderDetailInfoMapper orderDetailInfoMapper;

    @Test
    public void testCreateOrderDetailInfo_success() {
        // 准备参数
        OrderDetailInfoCreateReqVO reqVO = randomPojo(OrderDetailInfoCreateReqVO.class);

        // 调用
        Long orderDetailInfoId = orderDetailInfoService.createOrderDetailInfo(reqVO);
        // 断言
        assertNotNull(orderDetailInfoId);
        // 校验记录的属性是否正确
        OrderDetailInfoDO orderDetailInfo = orderDetailInfoMapper.selectById(orderDetailInfoId);
        assertPojoEquals(reqVO, orderDetailInfo);
    }

    @Test
    public void testUpdateOrderDetailInfo_success() throws IOException {
        // mock 数据
        OrderDetailInfoDO dbOrderDetailInfo = randomPojo(OrderDetailInfoDO.class);
        orderDetailInfoMapper.insert(dbOrderDetailInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        OrderDetailInfoUpdateReqVO reqVO = randomPojo(OrderDetailInfoUpdateReqVO.class, o -> {
            o.setId(dbOrderDetailInfo.getId()); // 设置更新的 ID
        });

        // 调用
        orderDetailInfoService.updateOrderDetailInfo(reqVO);
        // 校验是否更新正确
        OrderDetailInfoDO orderDetailInfo = orderDetailInfoMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, orderDetailInfo);
    }

    @Test
    public void testUpdateOrderDetailInfo_notExists() {
        // 准备参数
        OrderDetailInfoUpdateReqVO reqVO = randomPojo(OrderDetailInfoUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> orderDetailInfoService.updateOrderDetailInfo(reqVO), ORDER_DETAIL_INFO_NOT_EXISTS);
    }

    @Test
    public void testDeleteOrderDetailInfo_success() {
        // mock 数据
        OrderDetailInfoDO dbOrderDetailInfo = randomPojo(OrderDetailInfoDO.class);
        orderDetailInfoMapper.insert(dbOrderDetailInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbOrderDetailInfo.getId();

        // 调用
        orderDetailInfoService.deleteOrderDetailInfo(id);
       // 校验数据不存在了
       assertNull(orderDetailInfoMapper.selectById(id));
    }

    @Test
    public void testDeleteOrderDetailInfo_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> orderDetailInfoService.deleteOrderDetailInfo(id), ORDER_DETAIL_INFO_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetOrderDetailInfoPage() {
       // mock 数据
       OrderDetailInfoDO dbOrderDetailInfo = randomPojo(OrderDetailInfoDO.class, o -> { // 等会查询到
           o.setOrderDate(null);
           o.setOrderCode(null);
           o.setComprehensiveOrderCode(null);
           o.setOrderGoodsPictureUrl(null);
           o.setOrderOnedimensionalCodePictureUrl(null);
           o.setOrderSalesAmount(null);
           o.setOrderCostAmount(null);
           o.setOrderPlanProfitAmount(null);
           o.setUpstreamName(null);
           o.setDownstreamName(null);
           o.setOrderStatus(null);
           o.setOrderActualProfitAmount(null);
           o.setSettlementFlag(null);
           o.setSettlementTime(null);
           o.setDeletedTime(null);
           o.setNote(null);
           o.setSize(null);
           o.setCreateTime(null);
           o.setCreateSort(null);
       });
       orderDetailInfoMapper.insert(dbOrderDetailInfo);
       // 测试 orderDate 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setOrderDate(null)));
       // 测试 orderCode 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setOrderCode(null)));
       // 测试 comprehensiveOrderCode 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setComprehensiveOrderCode(null)));
       // 测试 orderGoodsPictureUrl 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setOrderGoodsPictureUrl(null)));
       // 测试 orderOnedimensionalCodePictureUrl 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setOrderOnedimensionalCodePictureUrl(null)));
       // 测试 orderSalesAmount 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setOrderSalesAmount(null)));
       // 测试 orderCostAmount 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setOrderCostAmount(null)));
       // 测试 orderPlanProfitAmount 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setOrderPlanProfitAmount(null)));
       // 测试 upstreamName 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setUpstreamName(null)));
       // 测试 downstreamName 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setDownstreamName(null)));
       // 测试 orderStatus 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setOrderStatus(null)));
       // 测试 orderActualProfitAmount 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setOrderActualProfitAmount(null)));
       // 测试 settlementFlag 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setSettlementFlag(null)));
       // 测试 settlementTime 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setSettlementTime(null)));
       // 测试 deletedTime 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setDeletedTime(null)));
       // 测试 note 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setNote(null)));
       // 测试 size 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setSize(null)));
       // 测试 createTime 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setCreateTime(null)));
       // 测试 createSort 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setCreateSort(null)));
       // 准备参数
       OrderDetailInfoPageReqVO reqVO = new OrderDetailInfoPageReqVO();
       // reqVO.setOrderDate(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setOrderCode(null);
       reqVO.setComprehensiveOrderCode(null);
       reqVO.setOrderGoodsPictureUrl(null);
       reqVO.setOrderOnedimensionalCodePictureUrl(null);
       reqVO.setOrderSalesAmount(null);
       reqVO.setOrderCostAmount(null);
       reqVO.setOrderPlanProfitAmount(null);
       reqVO.setUpstreamName(null);
       reqVO.setDownstreamName(null);
       reqVO.setOrderStatus(null);
       reqVO.setOrderActualProfitAmount(null);
       reqVO.setSettlementFlag(null);
       reqVO.setSettlementTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setDeletedTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setNote(null);
       reqVO.setSize(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setCreateSort(null);

       // 调用
       PageResult<OrderDetailInfoDO> pageResult = orderDetailInfoService.getOrderDetailInfoPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbOrderDetailInfo, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetOrderDetailInfoList() {
       // mock 数据
       OrderDetailInfoDO dbOrderDetailInfo = randomPojo(OrderDetailInfoDO.class, o -> { // 等会查询到
           o.setOrderDate(null);
           o.setOrderCode(null);
           o.setComprehensiveOrderCode(null);
           o.setOrderGoodsPictureUrl(null);
           o.setOrderOnedimensionalCodePictureUrl(null);
           o.setOrderSalesAmount(null);
           o.setOrderCostAmount(null);
           o.setOrderPlanProfitAmount(null);
           o.setUpstreamName(null);
           o.setDownstreamName(null);
           o.setOrderStatus(null);
           o.setOrderActualProfitAmount(null);
           o.setSettlementFlag(null);
           o.setSettlementTime(null);
           o.setDeletedTime(null);
           o.setNote(null);
           o.setSize(null);
           o.setCreateTime(null);
           o.setCreateSort(null);
       });
       orderDetailInfoMapper.insert(dbOrderDetailInfo);
       // 测试 orderDate 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setOrderDate(null)));
       // 测试 orderCode 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setOrderCode(null)));
       // 测试 comprehensiveOrderCode 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setComprehensiveOrderCode(null)));
       // 测试 orderGoodsPictureUrl 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setOrderGoodsPictureUrl(null)));
       // 测试 orderOnedimensionalCodePictureUrl 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setOrderOnedimensionalCodePictureUrl(null)));
       // 测试 orderSalesAmount 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setOrderSalesAmount(null)));
       // 测试 orderCostAmount 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setOrderCostAmount(null)));
       // 测试 orderPlanProfitAmount 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setOrderPlanProfitAmount(null)));
       // 测试 upstreamName 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setUpstreamName(null)));
       // 测试 downstreamName 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setDownstreamName(null)));
       // 测试 orderStatus 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setOrderStatus(null)));
       // 测试 orderActualProfitAmount 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setOrderActualProfitAmount(null)));
       // 测试 settlementFlag 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setSettlementFlag(null)));
       // 测试 settlementTime 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setSettlementTime(null)));
       // 测试 deletedTime 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setDeletedTime(null)));
       // 测试 note 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setNote(null)));
       // 测试 size 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setSize(null)));
       // 测试 createTime 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setCreateTime(null)));
       // 测试 createSort 不匹配
       orderDetailInfoMapper.insert(cloneIgnoreId(dbOrderDetailInfo, o -> o.setCreateSort(null)));
       // 准备参数
       OrderDetailInfoExportReqVO reqVO = new OrderDetailInfoExportReqVO();
       // reqVO.setOrderDate(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setOrderCode(null);
       reqVO.setComprehensiveOrderCode(null);
       reqVO.setOrderGoodsPictureUrl(null);
       reqVO.setOrderOnedimensionalCodePictureUrl(null);
       reqVO.setOrderSalesAmount(null);
       reqVO.setOrderCostAmount(null);
       reqVO.setOrderPlanProfitAmount(null);
       reqVO.setUpstreamName(null);
       reqVO.setDownstreamName(null);
       reqVO.setOrderStatus(null);
       reqVO.setOrderActualProfitAmount(null);
       reqVO.setSettlementFlag(null);
       reqVO.setSettlementTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setDeletedTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setNote(null);
       reqVO.setSize(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setCreateSort(null);

       // 调用
       List<OrderDetailInfoDO> list = orderDetailInfoService.getOrderDetailInfoList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbOrderDetailInfo, list.get(0));
    }

}
