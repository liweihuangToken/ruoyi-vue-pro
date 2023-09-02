package cn.iocoder.yudao.module.distribution.service.comprehensiveorderinfo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;

import cn.iocoder.yudao.module.distribution.controller.admin.comprehensiveorderinfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.comprehensiveorderinfo.ComprehensiveOrderInfoDO;
import cn.iocoder.yudao.module.distribution.dal.mysql.comprehensiveorderinfo.ComprehensiveOrderInfoMapper;
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
 * {@link ComprehensiveOrderInfoServiceImpl} 的单元测试类
 *
 * @author 李伟煌
 */
@Import(ComprehensiveOrderInfoServiceImpl.class)
public class ComprehensiveOrderInfoServiceImplTest extends BaseDbUnitTest {

    @Resource
    private ComprehensiveOrderInfoServiceImpl comprehensiveOrderInfoService;

    @Resource
    private ComprehensiveOrderInfoMapper comprehensiveOrderInfoMapper;

    @Test
    public void testCreateComprehensiveOrderInfo_success() {
        // 准备参数
        ComprehensiveOrderInfoCreateReqVO reqVO = randomPojo(ComprehensiveOrderInfoCreateReqVO.class);

        // 调用
        Long comprehensiveOrderInfoId = comprehensiveOrderInfoService.createComprehensiveOrderInfo(reqVO);
        // 断言
        assertNotNull(comprehensiveOrderInfoId);
        // 校验记录的属性是否正确
        ComprehensiveOrderInfoDO comprehensiveOrderInfo = comprehensiveOrderInfoMapper.selectById(comprehensiveOrderInfoId);
        assertPojoEquals(reqVO, comprehensiveOrderInfo);
    }

    @Test
    public void testUpdateComprehensiveOrderInfo_success() {
        // mock 数据
        ComprehensiveOrderInfoDO dbComprehensiveOrderInfo = randomPojo(ComprehensiveOrderInfoDO.class);
        comprehensiveOrderInfoMapper.insert(dbComprehensiveOrderInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        ComprehensiveOrderInfoUpdateReqVO reqVO = randomPojo(ComprehensiveOrderInfoUpdateReqVO.class, o -> {
            o.setId(dbComprehensiveOrderInfo.getId()); // 设置更新的 ID
        });

        // 调用
        comprehensiveOrderInfoService.updateComprehensiveOrderInfo(reqVO);
        // 校验是否更新正确
        ComprehensiveOrderInfoDO comprehensiveOrderInfo = comprehensiveOrderInfoMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, comprehensiveOrderInfo);
    }

    @Test
    public void testUpdateComprehensiveOrderInfo_notExists() {
        // 准备参数
        ComprehensiveOrderInfoUpdateReqVO reqVO = randomPojo(ComprehensiveOrderInfoUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> comprehensiveOrderInfoService.updateComprehensiveOrderInfo(reqVO), COMPREHENSIVE_ORDER_INFO_NOT_EXISTS);
    }

    @Test
    public void testDeleteComprehensiveOrderInfo_success() {
        // mock 数据
        ComprehensiveOrderInfoDO dbComprehensiveOrderInfo = randomPojo(ComprehensiveOrderInfoDO.class);
        comprehensiveOrderInfoMapper.insert(dbComprehensiveOrderInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbComprehensiveOrderInfo.getId();

        // 调用
        comprehensiveOrderInfoService.deleteComprehensiveOrderInfo(id);
       // 校验数据不存在了
       assertNull(comprehensiveOrderInfoMapper.selectById(id));
    }

    @Test
    public void testDeleteComprehensiveOrderInfo_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> comprehensiveOrderInfoService.deleteComprehensiveOrderInfo(id), COMPREHENSIVE_ORDER_INFO_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetComprehensiveOrderInfoPage() {
       // mock 数据
       ComprehensiveOrderInfoDO dbComprehensiveOrderInfo = randomPojo(ComprehensiveOrderInfoDO.class, o -> { // 等会查询到
           o.setOrderDate(null);
           o.setComprehensiveOrderCode(null);
           o.setComprehensiveOrderFileName(null);
           o.setComprehensiveOrderFileUrl(null);
           o.setTotalOrderNumber(null);
           o.setTotalOrderSalesAmount(null);
           o.setTotalOrderCostAmount(null);
           o.setTotalOrderPlanProfitAmount(null);
           o.setSettlementFlag(null);
           o.setSettlementTime(null);
           o.setTotalOrderTransactionsNumber(null);
           o.setTotalOrderReturnNumber(null);
           o.setTotalOrderActualProfitAmount(null);
           o.setDeletedTime(null);
           o.setNote(null);
           o.setCreateTime(null);
       });
       comprehensiveOrderInfoMapper.insert(dbComprehensiveOrderInfo);
       // 测试 orderDate 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setOrderDate(null)));
       // 测试 comprehensiveOrderCode 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setComprehensiveOrderCode(null)));
       // 测试 comprehensiveOrderFileName 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setComprehensiveOrderFileName(null)));
       // 测试 comprehensiveOrderFileUrl 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setComprehensiveOrderFileUrl(null)));
       // 测试 totalOrderNumber 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setTotalOrderNumber(null)));
       // 测试 totalOrderSalesAmount 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setTotalOrderSalesAmount(null)));
       // 测试 totalOrderCostAmount 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setTotalOrderCostAmount(null)));
       // 测试 totalOrderPlanProfitAmount 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setTotalOrderPlanProfitAmount(null)));
       // 测试 settlementFlag 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setSettlementFlag(null)));
       // 测试 settlementTime 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setSettlementTime(null)));
       // 测试 totalOrderTransactionsNumber 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setTotalOrderTransactionsNumber(null)));
       // 测试 totalOrderReturnNumber 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setTotalOrderReturnNumber(null)));
       // 测试 totalOrderActualProfitAmount 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setTotalOrderActualProfitAmount(null)));
       // 测试 deletedTime 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setDeletedTime(null)));
       // 测试 note 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setNote(null)));
       // 测试 createTime 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setCreateTime(null)));
       // 准备参数
       ComprehensiveOrderInfoPageReqVO reqVO = new ComprehensiveOrderInfoPageReqVO();
       //reqVO.setOrderDate(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setComprehensiveOrderCode(null);
       reqVO.setComprehensiveOrderFileName(null);
       reqVO.setComprehensiveOrderFileUrl(null);
       reqVO.setTotalOrderNumber(null);
       reqVO.setTotalOrderSalesAmount(null);
       reqVO.setTotalOrderCostAmount(null);
       reqVO.setTotalOrderPlanProfitAmount(null);
       reqVO.setSettlementFlag(null);
       reqVO.setSettlementTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setTotalOrderTransactionsNumber(null);
       reqVO.setTotalOrderReturnNumber(null);
       reqVO.setTotalOrderActualProfitAmount(null);
       reqVO.setDeletedTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setNote(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       PageResult<ComprehensiveOrderInfoDO> pageResult = comprehensiveOrderInfoService.getComprehensiveOrderInfoPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbComprehensiveOrderInfo, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetComprehensiveOrderInfoList() {
       // mock 数据
       ComprehensiveOrderInfoDO dbComprehensiveOrderInfo = randomPojo(ComprehensiveOrderInfoDO.class, o -> { // 等会查询到
           o.setOrderDate(null);
           o.setComprehensiveOrderCode(null);
           o.setComprehensiveOrderFileName(null);
           o.setComprehensiveOrderFileUrl(null);
           o.setTotalOrderNumber(null);
           o.setTotalOrderSalesAmount(null);
           o.setTotalOrderCostAmount(null);
           o.setTotalOrderPlanProfitAmount(null);
           o.setSettlementFlag(null);
           o.setSettlementTime(null);
           o.setTotalOrderTransactionsNumber(null);
           o.setTotalOrderReturnNumber(null);
           o.setTotalOrderActualProfitAmount(null);
           o.setDeletedTime(null);
           o.setNote(null);
           o.setCreateTime(null);
       });
       comprehensiveOrderInfoMapper.insert(dbComprehensiveOrderInfo);
       // 测试 orderDate 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setOrderDate(null)));
       // 测试 comprehensiveOrderCode 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setComprehensiveOrderCode(null)));
       // 测试 comprehensiveOrderFileName 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setComprehensiveOrderFileName(null)));
       // 测试 comprehensiveOrderFileUrl 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setComprehensiveOrderFileUrl(null)));
       // 测试 totalOrderNumber 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setTotalOrderNumber(null)));
       // 测试 totalOrderSalesAmount 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setTotalOrderSalesAmount(null)));
       // 测试 totalOrderCostAmount 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setTotalOrderCostAmount(null)));
       // 测试 totalOrderPlanProfitAmount 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setTotalOrderPlanProfitAmount(null)));
       // 测试 settlementFlag 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setSettlementFlag(null)));
       // 测试 settlementTime 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setSettlementTime(null)));
       // 测试 totalOrderTransactionsNumber 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setTotalOrderTransactionsNumber(null)));
       // 测试 totalOrderReturnNumber 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setTotalOrderReturnNumber(null)));
       // 测试 totalOrderActualProfitAmount 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setTotalOrderActualProfitAmount(null)));
       // 测试 deletedTime 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setDeletedTime(null)));
       // 测试 note 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setNote(null)));
       // 测试 createTime 不匹配
       comprehensiveOrderInfoMapper.insert(cloneIgnoreId(dbComprehensiveOrderInfo, o -> o.setCreateTime(null)));
       // 准备参数
       ComprehensiveOrderInfoExportReqVO reqVO = new ComprehensiveOrderInfoExportReqVO();
       // reqVO.setOrderDate(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setComprehensiveOrderCode(null);
       reqVO.setComprehensiveOrderFileName(null);
       reqVO.setComprehensiveOrderFileUrl(null);
       reqVO.setTotalOrderNumber(null);
       reqVO.setTotalOrderSalesAmount(null);
       reqVO.setTotalOrderCostAmount(null);
       reqVO.setTotalOrderPlanProfitAmount(null);
       reqVO.setSettlementFlag(null);
       reqVO.setSettlementTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setTotalOrderTransactionsNumber(null);
       reqVO.setTotalOrderReturnNumber(null);
       reqVO.setTotalOrderActualProfitAmount(null);
       reqVO.setDeletedTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setNote(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));

       // 调用
       List<ComprehensiveOrderInfoDO> list = comprehensiveOrderInfoService.getComprehensiveOrderInfoList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbComprehensiveOrderInfo, list.get(0));
    }

}
