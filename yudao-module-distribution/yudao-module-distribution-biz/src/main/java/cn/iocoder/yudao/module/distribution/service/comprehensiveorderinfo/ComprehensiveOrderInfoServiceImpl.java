package cn.iocoder.yudao.module.distribution.service.comprehensiveorderinfo;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.text.StrSplitter;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.pinyin.PinyinUtil;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.distribution.controller.admin.comprehensiveorderinfo.vo.*;
import cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo.OrderDetailInfoFacingObjectRespVO;
import cn.iocoder.yudao.module.distribution.convert.comprehensiveorderinfo.ComprehensiveOrderInfoConvert;
import cn.iocoder.yudao.module.distribution.dal.dataobject.comprehensiveorderinfo.ComprehensiveOrderInfoDO;
import cn.iocoder.yudao.module.distribution.dal.dataobject.downstreaminfo.DownstreamInfoDO;
import cn.iocoder.yudao.module.distribution.dal.dataobject.orderdetailinfo.OrderDetailInfoDO;
import cn.iocoder.yudao.module.distribution.dal.dataobject.orderstatustrackinfo.OrderStatusTrackInfoDO;
import cn.iocoder.yudao.module.distribution.dal.dataobject.upstreaminfo.UpstreamInfoDO;
import cn.iocoder.yudao.module.distribution.dal.mysql.comprehensiveorderinfo.ComprehensiveOrderInfoMapper;
import cn.iocoder.yudao.module.distribution.dal.mysql.downstreaminfo.DownstreamInfoMapper;
import cn.iocoder.yudao.module.distribution.dal.mysql.orderdetailinfo.OrderDetailInfoMapper;
import cn.iocoder.yudao.module.distribution.dal.mysql.orderstatustrackinfo.OrderStatusTrackInfoMapper;
import cn.iocoder.yudao.module.distribution.dal.mysql.upstreaminfo.UpstreamInfoMapper;
import cn.iocoder.yudao.module.distribution.enums.DeliveryMethodEnum;
import cn.iocoder.yudao.module.distribution.enums.OrderStatusEnum;
import cn.iocoder.yudao.module.distribution.service.orderdetailinfo.OrderDetailInfoService;
import cn.iocoder.yudao.module.distribution.utils.BarcodeUtil;
import cn.iocoder.yudao.module.infra.service.file.FileService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.distribution.enums.ErrorCodeConstants.*;

/**
 * 配货综合订单信息 Service 实现类
 *
 * @author 李伟煌
 */
@Service
@Validated
public class ComprehensiveOrderInfoServiceImpl implements ComprehensiveOrderInfoService {

    @Resource
    private ComprehensiveOrderInfoMapper comprehensiveOrderInfoMapper;

    @Resource
    private FileService fileService;

    @Resource
    private OrderDetailInfoService orderDetailInfoService;
    @Resource
    private OrderDetailInfoMapper orderDetailInfoMapper;

    @Resource
    private OrderStatusTrackInfoMapper orderStatusTrackInfoMapper;

    @Resource
    private DownstreamInfoMapper downstreamInfoMapper;

    @Resource
    private UpstreamInfoMapper upstreamInfoMapper;

    @Override
    public Long createComprehensiveOrderInfo(ComprehensiveOrderInfoCreateReqVO createReqVO) {
        // 插入
        ComprehensiveOrderInfoDO comprehensiveOrderInfo = ComprehensiveOrderInfoConvert.INSTANCE.convert(createReqVO);
        comprehensiveOrderInfoMapper.insert(comprehensiveOrderInfo);
        // 返回
        return comprehensiveOrderInfo.getId();
    }

    @Override
    public void updateComprehensiveOrderInfo(ComprehensiveOrderInfoUpdateReqVO updateReqVO) {
        // 校验存在
        validateComprehensiveOrderInfoExists(updateReqVO.getId());
        // 更新
        ComprehensiveOrderInfoDO updateObj = ComprehensiveOrderInfoConvert.INSTANCE.convert(updateReqVO);
        comprehensiveOrderInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteComprehensiveOrderInfo(Long id) {
        // 校验存在
        validateComprehensiveOrderInfoExists(id);
        ComprehensiveOrderInfoDO comprehensiveOrderInfoDO = comprehensiveOrderInfoMapper.selectById(id);
        List<OrderDetailInfoDO> orderDetailInfoDOList = orderDetailInfoMapper.selectList(
                OrderDetailInfoDO::getComprehensiveOrderCode, comprehensiveOrderInfoDO.getComprehensiveOrderCode());
        if (!orderDetailInfoDOList.isEmpty()) {
            List<Long> idList = new ArrayList<>();
            for (OrderDetailInfoDO orderDetailInfoDO : orderDetailInfoDOList) {
                idList.add(orderDetailInfoDO.getId());
            }
            orderDetailInfoMapper.deleteBatchIds(idList);
        }
        // 删除
        comprehensiveOrderInfoMapper.deleteById(id);
    }

    private void validateComprehensiveOrderInfoExists(Long id) {
        if (comprehensiveOrderInfoMapper.selectById(id) == null) {
            throw exception(COMPREHENSIVE_ORDER_INFO_NOT_EXISTS);
        }
    }

    @Override
    public ComprehensiveOrderInfoDO getComprehensiveOrderInfo(Long id) {
        return comprehensiveOrderInfoMapper.selectById(id);
    }

    @Override
    public List<ComprehensiveOrderInfoDO> getComprehensiveOrderInfoList(Collection<Long> ids) {
        return comprehensiveOrderInfoMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<ComprehensiveOrderInfoDO> getComprehensiveOrderInfoPage(ComprehensiveOrderInfoPageReqVO pageReqVO) {
        return comprehensiveOrderInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ComprehensiveOrderInfoDO> getComprehensiveOrderInfoList(ComprehensiveOrderInfoExportReqVO exportReqVO) {
        return comprehensiveOrderInfoMapper.selectList(exportReqVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // 添加事务，异常则回滚所有导入
    public ComprehensiveOrderInfoImportRespVO importExcel(MultipartFile file, Boolean isUpdateSupport) throws Exception {
        // 覆盖今日已生成的订单明细及综合订单
        if (isUpdateSupport) {
            ComprehensiveOrderInfoExportReqVO comprehensiveOrderInfoExportReqVO = new ComprehensiveOrderInfoExportReqVO();
            LocalDate[] orderDate = new LocalDate[2];
            orderDate[0] = orderDate[1] = LocalDate.now();
            comprehensiveOrderInfoExportReqVO.setOrderDate(orderDate);
            List<ComprehensiveOrderInfoDO> comprehensiveOrderInfoDOList = comprehensiveOrderInfoMapper.selectList(comprehensiveOrderInfoExportReqVO);
            for (ComprehensiveOrderInfoDO comprehensiveOrderInfoDO : comprehensiveOrderInfoDOList) {
                deleteComprehensiveOrderInfo(comprehensiveOrderInfoDO.getId());
            }
        }

        // 保留原始导入文件到文件管理
        InputStream fileInputStream = file.getInputStream();
        String fileName = file.getOriginalFilename();
        String fileUrl = fileService.createFileOtherTransaction(fileName, null, IoUtil.readBytes(fileInputStream));
        fileInputStream.close();

        // 通过url获取导入文件的文件流，并利用easyPoi解析成订单明细对象
        InputStream inputStream = new URL(fileUrl).openConnection().getInputStream();
        ImportParams params = new ImportParams();
        params.setNeedSave(true);
        // 设置读取Excel第二列不为空的所有行，如为空则不读取
        params.setKeyIndex(2);
        List<ComprehensiveOrderImportExcelVO> importOrderList = ExcelImportUtil.importExcel(inputStream, ComprehensiveOrderImportExcelVO.class, params);
        inputStream.close();
        // 判断订单是否为空，为空返回提示
        if (CollUtil.isEmpty(importOrderList)) {
            throw exception(ORDER_IMPORT_LIST_IS_EMPTY);
        }

        // 解析导入的订单明细，并对订单明细进行业务处理
        ComprehensiveOrderInfoImportRespVO comprehensiveOrderInfoImportRespVO = analysisOrders(importOrderList, fileName, fileUrl);
        return comprehensiveOrderInfoImportRespVO;
    }

    @Override
    public void assignOrderInfoCode(Collection<Long> ids) throws IOException {
        // 查询面向下游顺序查询所有ids相关的所有订单明细信息
        List<OrderDetailInfoFacingObjectRespVO> orderDetailInfoFacingObjectRespVOList = orderDetailInfoMapper.selectListFacingDownstream(ids);

        // 检查是否存在取货、提货方式未配置的相关上下游 并记录每个下游客户订单登记总量
        boolean flag = false;
        for (OrderDetailInfoFacingObjectRespVO orderDetailInfoFacingObjectRespVO : orderDetailInfoFacingObjectRespVOList) {
            if (null == orderDetailInfoFacingObjectRespVO.getDeliveryMethod()
                    || null == orderDetailInfoFacingObjectRespVO.getPickupMethod()) {
                flag = true;
                break;
            }
        }
        if(flag){
            throw exception(OBJECT_STREAM_METHON_NOT_EXISTS);
        }

        // 更新订单明细订单编号 + 订单条形码信息
        List<OrderDetailInfoDO> orderDetailInfoUpdateDOList = new ArrayList<>();
        // 添加订单状态跟踪信息
        List<OrderStatusTrackInfoDO> orderStatusTrackInfoDOList = new ArrayList<>();
        // 14位时间码
        String dateStr = LocalDateTimeUtil.format(LocalDateTime.now(), DatePattern.PURE_DATE_FORMATTER);
        int cnt = 1;
        // 页码排序统计
        for (OrderDetailInfoFacingObjectRespVO orderDetailInfoFacingObjectRespVO : orderDetailInfoFacingObjectRespVOList) {
            // 跳过已生成订单编码的订单明细
            if (null != orderDetailInfoFacingObjectRespVO.getOrderCode()) {
                continue;
            }
            StringBuffer sb = new StringBuffer();
            // 给下游送货方式简码
            String deliveryCode = DeliveryMethodEnum.valueOfType(Integer.valueOf(orderDetailInfoFacingObjectRespVO.getDeliveryMethod())).getCode();
            // 去上游提货方式简码
            String pickupCode = DeliveryMethodEnum.valueOfType(Integer.valueOf(orderDetailInfoFacingObjectRespVO.getPickupMethod())).getCode();
            // 4位顺序码
            String sortNumber = String.format("%04d", cnt);
            cnt++;
            // 合成订单编码
            String orderCode = sb.append(deliveryCode).append(pickupCode).append(dateStr).append(sortNumber).toString();

            // 生成订单编码一维码图片并上传文件管理
            byte[] bytes = BarcodeUtil.generateBarCode128(orderCode, null, 8.00d, 4.00d, true, null);
            String codePictrueName = orderCode + ".png";
            String url = fileService.createFile(codePictrueName, null, bytes);

            // 生成订单标签并上传到文件管理
            String labelUrl = orderDetailInfoService.assignOrderInfoLabel(orderDetailInfoFacingObjectRespVO, url, orderCode);
            // 组装更新条目
            OrderDetailInfoDO orderDetailInfoUpdateDO = new OrderDetailInfoDO();
            orderDetailInfoUpdateDO.setId(orderDetailInfoFacingObjectRespVO.getId());
            orderDetailInfoUpdateDO.setOrderCode(orderCode);
            orderDetailInfoUpdateDO.setOrderOnedimensionalCodePictureUrl(labelUrl);
            orderDetailInfoUpdateDOList.add(orderDetailInfoUpdateDO);

            // 组装订单状态跟踪条目
            OrderStatusTrackInfoDO orderStatusTrackInfoDO = new OrderStatusTrackInfoDO();
            orderStatusTrackInfoDO.setOrderCode(orderCode);
            orderStatusTrackInfoDO.setOrderAfterChangeStatus(Byte.valueOf(OrderStatusEnum.ORDER_STATUS_REGISTRATION.getCode().toString()));
            orderStatusTrackInfoDOList.add(orderStatusTrackInfoDO);
        }
        orderStatusTrackInfoMapper.insertBatch(orderStatusTrackInfoDOList);
        orderDetailInfoMapper.updateBatch(orderDetailInfoUpdateDOList);
    }

    /**
     * 分析Excel订单列表
     *
     * @param importOrderList
     * @return
     */
    private ComprehensiveOrderInfoImportRespVO analysisOrders(List<ComprehensiveOrderImportExcelVO> importOrderList, String fileName, String fileUrl) {
        // 定义导入任务方法的返回对象
        ComprehensiveOrderInfoImportRespVO respVO = ComprehensiveOrderInfoImportRespVO.builder().createOrders(new LinkedHashMap<>()).failureOrders(new LinkedHashMap<>()).build();

        // 综合订单参数组装
        BigDecimal totalSalesAmount = BigDecimal.ZERO;
        BigDecimal totalCostAmount = BigDecimal.ZERO;
        BigDecimal totalPlanProfitAmount = BigDecimal.ZERO;
        LocalDate orderDate = LocalDate.now();
        String comprehensiveOrderCode = "C" + LocalDateTimeUtil.format(LocalDateTime.now(), DatePattern.PURE_DATETIME_FORMATTER);
        ComprehensiveOrderInfoDO comprehensiveOrderInfoDO = new ComprehensiveOrderInfoDO();
        comprehensiveOrderInfoDO.setOrderDate(orderDate);
        comprehensiveOrderInfoDO.setComprehensiveOrderCode(comprehensiveOrderCode);
        comprehensiveOrderInfoDO.setComprehensiveOrderFileName(fileName);
        comprehensiveOrderInfoDO.setComprehensiveOrderFileUrl(fileUrl);
        comprehensiveOrderInfoDO.setTotalOrderSalesAmount(totalSalesAmount);
        comprehensiveOrderInfoDO.setTotalOrderCostAmount(totalCostAmount);
        comprehensiveOrderInfoDO.setTotalOrderPlanProfitAmount(totalPlanProfitAmount);
        comprehensiveOrderInfoDO.setSettlementFlag((byte) 0);

        // 分析Excel行信息属性
        String downstreamName = "";
        List<String> upstreamNameList = new ArrayList<>();
        List<String> downstreamNameList = new ArrayList<>();
        HashMap<String, String> downstreamAddressHashMap = new HashMap<>();
        HashMap<String, Integer> downstreamdeliveryMethodHashMap = new HashMap<>();
        List<OrderDetailInfoDO> actualOrderList = new ArrayList<>();

        for (int i = 1; i <= importOrderList.size(); i++) {
            ComprehensiveOrderImportExcelVO importOrder = importOrderList.get(i - 1);

            // 统计下游
            if (null != importOrder.getDownstreamName() && !downstreamNameList.contains(importOrder.getDownstreamName())) {
                downstreamNameList.add(importOrder.getDownstreamName());
            }
            if (null != importOrder.getDownstreamName() && null != importOrder.getDownstreamAddress()) {
                downstreamAddressHashMap.put(importOrder.getDownstreamName(), importOrder.getDownstreamAddress());
            }
            if (null != importOrder.getDownstreamName() && null != importOrder.getDeliveryMethod()) {
                downstreamdeliveryMethodHashMap.put(importOrder.getDownstreamName(), importOrder.getDeliveryMethod());
            }

            // 统计上游
            if (null != importOrder.getUpstreamName() && !upstreamNameList.contains(importOrder.getUpstreamName())) {
                upstreamNameList.add(importOrder.getUpstreamName());
            }

            // 如果导入的下游名称为空则按就近原则取上部最近上游名称
            downstreamName = null == importOrder.getDownstreamName() ? downstreamName : importOrder.getDownstreamName();
            importOrder.setDownstreamName(downstreamName);

            // 分析订单详情
            analysisOrderDtail(respVO, downstreamName, actualOrderList, i, importOrder, comprehensiveOrderInfoDO);
        }
        // 保存订单详细列表
        orderDetailInfoMapper.insertBatch(actualOrderList);

        // 保存综合订单信息
        comprehensiveOrderInfoDO.setTotalOrderNumber(actualOrderList.size());
        comprehensiveOrderInfoMapper.insert(comprehensiveOrderInfoDO);

        // 保存或更新下游信息
        saveOrUpdateDownstreamList(downstreamNameList, downstreamAddressHashMap, downstreamdeliveryMethodHashMap);

        // 保存或更新上游信息
        saveOrUpdateUpstreamList(upstreamNameList);

        return respVO;
    }

    /**
     * 保存或更新下游信息
     *
     * @param downstreamNameList
     * @param downstreamHashMap
     * @param downstreamdeliveryMethodHashMap
     */
    private void saveOrUpdateDownstreamList(List<String> downstreamNameList, HashMap<String, String> downstreamHashMap,
                                            HashMap<String, Integer> downstreamdeliveryMethodHashMap) {
        for (int i = 0; i < downstreamNameList.size(); i++) {
            String downstreamName = downstreamNameList.get(i);
            DownstreamInfoDO downstreamInfoDO = downstreamInfoMapper.selectOne(DownstreamInfoDO::getDownstreamName, downstreamName);
            if (null == downstreamInfoDO) {
                String localDateStr = LocalDateTimeUtil.format(LocalDateTime.now(), DatePattern.PURE_DATETIME_FORMATTER);
                String firstLetter = PinyinUtil.getFirstLetter(downstreamName, "").toUpperCase();
                DownstreamInfoDO downstreamInfoDOByAlias = downstreamInfoMapper.selectOne(DownstreamInfoDO::getDownstreamAlias, firstLetter);
                firstLetter = null == downstreamInfoDOByAlias ? firstLetter : firstLetter + "_" + i;
                String code = firstLetter + localDateStr + i;
                byte deliveryMethod = null == downstreamdeliveryMethodHashMap.get(downstreamName) ?
                        (byte) 1 : Byte.valueOf(downstreamdeliveryMethodHashMap.get(downstreamName).toString());
                DownstreamInfoDO downstreamInfoNotExistDO = new DownstreamInfoDO();
                downstreamInfoNotExistDO.setDownstreamCode(code);
                downstreamInfoNotExistDO.setDownstreamName(downstreamName);
                downstreamInfoNotExistDO.setDownstreamAlias(firstLetter);
                downstreamInfoNotExistDO.setDownstreamAddress(downstreamHashMap.get(downstreamName));
                downstreamInfoNotExistDO.setDeliveryMethod(deliveryMethod);
                downstreamInfoMapper.insert(downstreamInfoNotExistDO);
            } else {
                byte deliveryMethod = null == downstreamdeliveryMethodHashMap.get(downstreamName) ?
                        (byte) 1 : Byte.valueOf(downstreamdeliveryMethodHashMap.get(downstreamName).toString());
                if (deliveryMethod != downstreamInfoDO.getDeliveryMethod()) {
                    downstreamInfoDO.setDeliveryMethod(deliveryMethod);
                }
                String address = downstreamHashMap.get(downstreamName);
                if (null != address && address.equals(downstreamInfoDO.getDownstreamAddress())) {
                    downstreamInfoDO.setDownstreamAddress(address);
                }
                downstreamInfoMapper.updateById(downstreamInfoDO);
            }
        }
    }

    /**
     * 保存或更新上游信息
     *
     * @param upstreamNameList
     */
    private void saveOrUpdateUpstreamList(List<String> upstreamNameList) {
        for (int i = 0; i < upstreamNameList.size(); i++) {
            String upstreamName = upstreamNameList.get(i);
            UpstreamInfoDO upstreamInfoDO = upstreamInfoMapper.selectOne(UpstreamInfoDO::getUpstreamName, upstreamName);
            if (null == upstreamInfoDO) {
                String localDateStr = LocalDateTimeUtil.format(LocalDateTime.now(), DatePattern.PURE_DATETIME_FORMATTER);
                String firstLetter = PinyinUtil.getFirstLetter(upstreamName, "").toUpperCase();
                UpstreamInfoDO uptreamInfoDOByAlias = upstreamInfoMapper.selectOne(UpstreamInfoDO::getUpstreamAlias, firstLetter);
                firstLetter = null == uptreamInfoDOByAlias ? firstLetter : firstLetter + "-" + i;
                String code = firstLetter + "-" + localDateStr + i;
                UpstreamInfoDO upstreamInfoNotExistDO = new UpstreamInfoDO();
                upstreamInfoNotExistDO.setUpstreamCode(code);
                upstreamInfoNotExistDO.setUpstreamName(upstreamName);
                upstreamInfoNotExistDO.setUpstreamAlias(firstLetter);
                upstreamInfoMapper.insert(upstreamInfoNotExistDO);
            }
        }
    }


    /**
     * 分析订单详情信息
     *
     * @param respVO
     * @param downstreamName
     * @param actualOrderList
     * @param sort
     * @param importOrder
     */
    private void analysisOrderDtail(ComprehensiveOrderInfoImportRespVO respVO, String downstreamName,
                                    List<OrderDetailInfoDO> actualOrderList, int sort, ComprehensiveOrderImportExcelVO importOrder,
                                    ComprehensiveOrderInfoDO comprehensiveOrderInfoDO) {
        // + 1是因为第一行是列名，第二行开始才是正式订单信息
        String rowNumber = String.valueOf(sort + 1);
        // 订单图片不能为空
        if (StrUtil.isBlank(importOrder.getPicture())) {
            respVO.getFailureOrders().put(rowNumber, "无法读取到订单图片");
            return;
        }
        // 订单尺码不能为空
        if (StrUtil.isBlank(importOrder.getSize())) {
            respVO.getFailureOrders().put(rowNumber, "无法读取到订单尺码");
            return;
        }
        // 订单售价不能为空
        if (null == importOrder.getSellingPrice()) {
            respVO.getFailureOrders().put(rowNumber, "无法读取到订单售价");
            return;
        }
        // 分析订单图片： 保存图片到文件管理并返回图片地址
        String orderPictureUrl = analysisOrderPicture(importOrder.getPicture(), respVO, rowNumber);
        if (null == orderPictureUrl) {
            return;
        }

        // 每行订单的基本信息组装
        OrderDetailInfoDO orderDetailInfoDO = new OrderDetailInfoDO();
        BigDecimal sellingPrice = importOrder.getSellingPrice();
        BigDecimal orderPlanProfitAmount = new BigDecimal(10.00);
        LocalDate orderDate = LocalDate.now();
        orderDetailInfoDO.setOrderDate(orderDate);
        orderDetailInfoDO.setComprehensiveOrderCode(comprehensiveOrderInfoDO.getComprehensiveOrderCode());
        orderDetailInfoDO.setOrderGoodsPictureUrl(orderPictureUrl);
        orderDetailInfoDO.setOrderSalesAmount(sellingPrice);
        orderDetailInfoDO.setOrderCostAmount(sellingPrice.subtract(orderPlanProfitAmount));
        orderDetailInfoDO.setOrderPlanProfitAmount(orderPlanProfitAmount);
        orderDetailInfoDO.setUpstreamName(importOrder.getUpstreamName());
        orderDetailInfoDO.setDownstreamName(downstreamName);
        orderDetailInfoDO.setOrderStatus((byte) 0);
        orderDetailInfoDO.setSettlementFlag((byte) 0);
        orderDetailInfoDO.setNote(importOrder.getNote());
        orderDetailInfoDO.setSize(importOrder.getSize());
        orderDetailInfoDO.setCreateSort(sort);

        // 分析订单尺码， 示例：32 35.5*12。 合并订单分解成多个订单
        analysisOrderSize(respVO, actualOrderList, rowNumber, orderDetailInfoDO, comprehensiveOrderInfoDO);
    }

    /**
     * 分析订单图片
     *
     * @param picturePath
     * @param respVO
     * @param rowNumber
     * @return
     */
    private String analysisOrderPicture(String picturePath, ComprehensiveOrderInfoImportRespVO respVO, String rowNumber) {
        // 从本地图片上传到文件库，上传对象存储或者数据库之后删掉本地图片
        String pictureUrl = null;
        FileInputStream pictureInputStream = null;
        File pictureFile = new File(picturePath);
        try {
            pictureInputStream = new FileInputStream(pictureFile);
            pictureUrl = fileService.createFile(pictureFile.getName(), null, IoUtil.readBytes(pictureInputStream));
        } catch (Exception e) {
            respVO.getFailureOrders().put(rowNumber, e.getMessage());
        } finally {
            if (pictureInputStream != null) {
                pictureFile.delete();
                try {
                    pictureInputStream.close();
                } catch (IOException e) {
                    respVO.getFailureOrders().put(rowNumber, e.getMessage());
                }
            }
        }
        return pictureUrl;
    }

    /**
     * 分析订单尺码
     *
     * @param respVO
     * @param actualOrderList
     * @param rowNumber
     * @param orderDetailInfoDO
     */
    private void analysisOrderSize(ComprehensiveOrderInfoImportRespVO respVO, List<OrderDetailInfoDO> actualOrderList,
                                   String rowNumber, OrderDetailInfoDO orderDetailInfoDO, ComprehensiveOrderInfoDO comprehensiveOrderInfoDO) {
        // 示例：35.5*12  说明：同一款式有多个尺码分解成多个订单，空字符串" "默认跳过不读取
        List<String> sizeArr = StrSplitter.split(orderDetailInfoDO.getSize(), " ", 0, true, true);
        for (String size : sizeArr) {
            if (!size.contains("*")) {
                OrderDetailInfoDO orderDetailInfoChildDO = new OrderDetailInfoDO();
                BeanUtils.copyProperties(orderDetailInfoDO, orderDetailInfoChildDO);
                orderDetailInfoChildDO.setSize(size);
                actualOrderList.add(orderDetailInfoChildDO);
                respVO.getCreateOrders().put(rowNumber, "成功");
                comprehensiveOrderInfoDO.setTotalOrderSalesAmount(
                        comprehensiveOrderInfoDO.getTotalOrderSalesAmount().add(orderDetailInfoChildDO.getOrderSalesAmount()));
                comprehensiveOrderInfoDO.setTotalOrderCostAmount(
                        comprehensiveOrderInfoDO.getTotalOrderCostAmount().add(orderDetailInfoChildDO.getOrderCostAmount()));
                comprehensiveOrderInfoDO.setTotalOrderPlanProfitAmount(
                        comprehensiveOrderInfoDO.getTotalOrderPlanProfitAmount().add(orderDetailInfoChildDO.getOrderPlanProfitAmount()));
                continue;
            }
            List<String> childSizeList = StrSplitter.split(size, "*", 0, true, true);
            int count = NumberUtil.parseInt(childSizeList.get(childSizeList.size() - 1));
            size = childSizeList.get(0);
            for (int i = 0; i < count; i++) {
                OrderDetailInfoDO orderDetailInfoChildDO = new OrderDetailInfoDO();
                BeanUtils.copyProperties(orderDetailInfoDO, orderDetailInfoChildDO);
                orderDetailInfoChildDO.setSize(size);
                actualOrderList.add(orderDetailInfoChildDO);
                comprehensiveOrderInfoDO.setTotalOrderSalesAmount(
                        comprehensiveOrderInfoDO.getTotalOrderSalesAmount().add(orderDetailInfoChildDO.getOrderSalesAmount()));
                comprehensiveOrderInfoDO.setTotalOrderCostAmount(
                        comprehensiveOrderInfoDO.getTotalOrderCostAmount().add(orderDetailInfoChildDO.getOrderCostAmount()));
                comprehensiveOrderInfoDO.setTotalOrderPlanProfitAmount(
                        comprehensiveOrderInfoDO.getTotalOrderPlanProfitAmount().add(orderDetailInfoChildDO.getOrderPlanProfitAmount()));
            }
            respVO.getCreateOrders().put(rowNumber, "成功");
        }
    }
}
