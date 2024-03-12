package cn.iocoder.yudao.module.distribution.service.orderdetailinfo;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.distribution.controller.admin.orderstatustrackinfo.vo.OrderStatusTrackInfoExportReqVO;
import cn.iocoder.yudao.module.distribution.dal.dataobject.comprehensiveorderinfo.ComprehensiveOrderInfoDO;
import cn.iocoder.yudao.module.distribution.dal.dataobject.orderstatustrackinfo.OrderStatusTrackInfoDO;
import cn.iocoder.yudao.module.distribution.dal.mysql.comprehensiveorderinfo.ComprehensiveOrderInfoMapper;
import cn.iocoder.yudao.module.distribution.dal.mysql.orderstatustrackinfo.OrderStatusTrackInfoMapper;
import cn.iocoder.yudao.module.distribution.enums.DeliveryMethodEnum;
import cn.iocoder.yudao.module.distribution.enums.OrderStatusEnum;
import cn.iocoder.yudao.module.distribution.utils.BarcodeUtil;
import cn.iocoder.yudao.module.distribution.utils.FontUtil;
import cn.iocoder.yudao.module.distribution.utils.SucodeUtil;
import cn.iocoder.yudao.module.infra.service.file.FileService;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.springframework.validation.annotation.Validated;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

import cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo.*;
import cn.iocoder.yudao.module.distribution.dal.dataobject.orderdetailinfo.OrderDetailInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

import cn.iocoder.yudao.module.distribution.convert.orderdetailinfo.OrderDetailInfoConvert;
import cn.iocoder.yudao.module.distribution.dal.mysql.orderdetailinfo.OrderDetailInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.distribution.enums.ErrorCodeConstants.*;

/**
 * 配货订单明细 Service 实现类
 *
 * @author 李伟煌
 */
@Service
@Validated
public class OrderDetailInfoServiceImpl implements OrderDetailInfoService {

    @Resource
    private OrderDetailInfoMapper orderDetailInfoMapper;

    @Resource
    private ComprehensiveOrderInfoMapper comprehensiveOrderInfoMapper;

    @Resource
    private OrderStatusTrackInfoMapper orderStatusTrackInfoMapper;

    @Resource
    private FileService fileService;

    @Override
    public Long createOrderDetailInfo(OrderDetailInfoCreateReqVO createReqVO) {
        // 插入
        OrderDetailInfoDO orderDetailInfo = OrderDetailInfoConvert.INSTANCE.convert(createReqVO);
        orderDetailInfoMapper.insert(orderDetailInfo);
        // 返回
        return orderDetailInfo.getId();
    }

    @Override
    public void updateOrderDetailInfo(OrderDetailInfoUpdateReqVO updateReqVO) throws IOException {
        // 校验存在
        validateOrderDetailInfoExists(updateReqVO.getId());
        OrderDetailInfoDO oldOrderDetailInfoDO = orderDetailInfoMapper.selectById(updateReqVO.getId());
        // 更新
        BigDecimal planProfitAmount = updateReqVO.getOrderSalesAmount().subtract(updateReqVO.getOrderCostAmount());
        updateReqVO.setOrderPlanProfitAmount(planProfitAmount);
        OrderDetailInfoDO updateObj = OrderDetailInfoConvert.INSTANCE.convert(updateReqVO);
        orderDetailInfoMapper.updateById(updateObj);

        // 重新生成标签图片
        OrderDetailInfoDO orderDetailInfoDO = orderDetailInfoMapper.selectById(updateReqVO.getId());
        if (!StrUtil.isEmpty(orderDetailInfoDO.getOrderCode())) {
            OrderDetailInfoExportReqVO orderDetailInfoExportReqVO = new OrderDetailInfoExportReqVO();
            orderDetailInfoExportReqVO.setOrderCode(orderDetailInfoDO.getOrderCode());
            List<OrderDetailInfoFacingObjectRespVO> orderDetailInfoFacingObjectRespVOList = orderDetailInfoMapper.selectListFacingDownstream(orderDetailInfoExportReqVO);
            for (OrderDetailInfoFacingObjectRespVO orderDetailInfoFacingObjectRespVO : orderDetailInfoFacingObjectRespVOList) {
                // 生成订单编码一维码图片并上传文件管理
                String orderCode = orderDetailInfoFacingObjectRespVO.getOrderCode();
                byte[] bytes = BarcodeUtil.generateBarCode128(orderCode, null, 8.00d, 4.00d, true, null);
                String codePictrueName = orderCode + ".png";
                String orderCodeUrl = fileService.createFile(codePictrueName, null, bytes);
                String pictrueUrl = assignOrderInfoLabel(orderDetailInfoFacingObjectRespVO, orderCodeUrl, orderCode);
                orderDetailInfoDO.setOrderOnedimensionalCodePictureUrl(pictrueUrl);
                orderDetailInfoMapper.updateById(orderDetailInfoDO);
            }
        }

        // 更新综合订单信息
        ComprehensiveOrderInfoDO comprehensiveOrderInfoDO = comprehensiveOrderInfoMapper.selectOne(ComprehensiveOrderInfoDO::getComprehensiveOrderCode, orderDetailInfoDO.getComprehensiveOrderCode());
        comprehensiveOrderInfoDO.setTotalOrderCostAmount(
                comprehensiveOrderInfoDO.getTotalOrderCostAmount().subtract(oldOrderDetailInfoDO.getOrderCostAmount()).add(orderDetailInfoDO.getOrderCostAmount())
        );
        comprehensiveOrderInfoDO.setTotalOrderSalesAmount(
                comprehensiveOrderInfoDO.getTotalOrderSalesAmount().subtract(oldOrderDetailInfoDO.getOrderSalesAmount()).add(orderDetailInfoDO.getOrderSalesAmount())
        );
        comprehensiveOrderInfoDO.setTotalOrderPlanProfitAmount(
                comprehensiveOrderInfoDO.getTotalOrderPlanProfitAmount().subtract(oldOrderDetailInfoDO.getOrderPlanProfitAmount()).add(orderDetailInfoDO.getOrderPlanProfitAmount())
        );
        comprehensiveOrderInfoMapper.updateById(comprehensiveOrderInfoDO);
    }

    @Override
    public void deleteOrderDetailInfo(Long id) {
        // 校验存在
        validateOrderDetailInfoExists(id);
        // 更新新综合订单信息
        OrderDetailInfoDO oldOrderDetailInfoDO = orderDetailInfoMapper.selectById(id);
        ComprehensiveOrderInfoDO comprehensiveOrderInfoDO = comprehensiveOrderInfoMapper.selectOne(ComprehensiveOrderInfoDO::getComprehensiveOrderCode, oldOrderDetailInfoDO.getComprehensiveOrderCode());
        comprehensiveOrderInfoDO.setTotalOrderCostAmount(
                comprehensiveOrderInfoDO.getTotalOrderCostAmount().subtract(oldOrderDetailInfoDO.getOrderCostAmount())
        );
        comprehensiveOrderInfoDO.setTotalOrderSalesAmount(
                comprehensiveOrderInfoDO.getTotalOrderSalesAmount().subtract(oldOrderDetailInfoDO.getOrderSalesAmount())
        );
        comprehensiveOrderInfoDO.setTotalOrderPlanProfitAmount(
                comprehensiveOrderInfoDO.getTotalOrderPlanProfitAmount().subtract(oldOrderDetailInfoDO.getOrderPlanProfitAmount())
        );
        comprehensiveOrderInfoDO.setTotalOrderNumber(comprehensiveOrderInfoDO.getTotalOrderNumber() - 1);
        comprehensiveOrderInfoMapper.updateById(comprehensiveOrderInfoDO);
        // 删除
        orderDetailInfoMapper.deleteById(id);
    }

    private void validateOrderDetailInfoExists(Long id) {
        if (orderDetailInfoMapper.selectById(id) == null) {
            throw exception(ORDER_DETAIL_INFO_NOT_EXISTS);
        }
    }

    @Override
    public OrderDetailInfoDO getOrderDetailInfo(Long id) {
        return orderDetailInfoMapper.selectById(id);
    }

    @Override
    public List<OrderDetailInfoDO> getOrderDetailInfoList(Collection<Long> ids) {
        return orderDetailInfoMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<OrderDetailInfoDO> getOrderDetailInfoPage(OrderDetailInfoPageReqVO pageReqVO) {
        return orderDetailInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<OrderDetailInfoDO> getOrderDetailInfoList(OrderDetailInfoExportReqVO exportReqVO) {
        return orderDetailInfoMapper.selectList(exportReqVO);
    }

    @Override
    public String assignOrderInfoLabel(OrderDetailInfoFacingObjectRespVO orderDetailInfoFacingObjectRespVO,
                                       String orderCodeUrl, String orderCode) throws IOException {
        // 图片宽高
        int width = 480;
        int height = 310;
        // String fontPath = "fonts/叶根友疾风草书.ttf";
        // String fontPath = "fonts/方正黄草_GBK.ttf";
        // String fontPath = "fonts/李洤标准草书.ttf";
        String fontPath = "fonts/叶根友刀锋黑草-企业版.ttf";
        // 得到图片缓冲区, INT精确度达到一定,RGB三原色
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bi.createGraphics();
        // 得到它的绘制环境(这张图片的笔)
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        // 设置背景颜色,并填充满张图片
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);
        // 设置图片字体颜色
        g2.setColor(Color.black);
        // 边框加粗
        g2.setStroke(new BasicStroke(2.0f));
        // 画标签的边框（所以之后边框线条就不用画了）
        g2.drawRect(15, 15, width - 30, height - 30);

        // 开始画出边框外的其他表格线条，x、y为坐标
        // 从上到下第2个横线
        g2.drawLine(15, 84, 465, 84);
        // 从上到下第3个横线
        g2.drawLine(15, 133, 260, 133);
        // 从上到下第4个横线
        g2.drawLine(15, 182, 260, 182);
        // 从上到下第4个横线
        g2.drawLine(15, 231, 465, 231);
        // 从左到右第二个竖线
        g2.drawLine(105, 84, 105, 231);
        // 从左到右第三个竖线
        g2.drawLine(260, 84, 260, 231);

        // 设置标题的字体,字号,大小
        Font titleFont = FontUtil.loadLocalFont(fontPath, Font.BOLD, 35);
        g2.setFont(titleFont);
        // 抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 计算文字长度,计算居中的X点坐标
        String downstreamName = orderDetailInfoFacingObjectRespVO.getDownstreamName();
        FontMetrics fm = g2.getFontMetrics(titleFont);
        int titleWidth = fm.stringWidth(downstreamName);
        int titleWidthX = (width - titleWidth) / 2;
        g2.drawString(downstreamName, titleWidthX, 50);

        // 设置标题下编码小字字体、字号、大小, 小标题为随机花码 + 售价花码 + 随机花码 + 成本花码 + 随机花码
        g2.setFont(new Font("华文楷体", Font.ITALIC, 20));
        StringBuffer littleTitleSb = new StringBuffer("ATUCODE：");
        littleTitleSb.append(SucodeUtil.getRandomSucode())
                .append(SucodeUtil.getSucode(NumberUtil.toStr(orderDetailInfoFacingObjectRespVO.getOrderSalesAmount())))
                .append("一")
                .append(SucodeUtil.getSucode(NumberUtil.toStr(orderDetailInfoFacingObjectRespVO.getOrderCostAmount())))
                .append(SucodeUtil.getRandomSucode());
        g2.drawString(littleTitleSb.toString(), (width - 280) / 2, 75);

        // 设置表格内其他参数
        g2.setFont(FontUtil.loadLocalFont(fontPath, Font.BOLD, 23));
        g2.drawString("尺码", 20, 114);
        g2.drawString(orderDetailInfoFacingObjectRespVO.getSize(), 115, 114);

        g2.drawString("备注", 20, 163);
        String note = null == orderDetailInfoFacingObjectRespVO.getNote() ? "" : orderDetailInfoFacingObjectRespVO.getNote();
        g2.drawString(note, 115, 163);

        g2.drawString("标签", 20, 212);
        g2.drawString(orderDetailInfoFacingObjectRespVO.getUpstreamAlias(), 115, 212);

        // 画订单图片进标签
        InputStream inputStream = new URL(orderDetailInfoFacingObjectRespVO.getOrderGoodsPictureUrl()).openConnection().getInputStream();
        BufferedImage image2 = ImageIO.read(inputStream);
        g2.drawImage(image2, 263, 87, 199, 141, null);

        // 画标签一维码
        InputStream oneCodeInputStream = new URL(orderCodeUrl).openConnection().getInputStream();
        BufferedImage image3 = ImageIO.read(oneCodeInputStream);
        g2.drawImage(image3, 25, 236, 430, 57, null);

        // 释放对象并生成标签图片 png表示保存格式
        g2.dispose();

        // 为标签添加水印
        InputStream fontStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("fonts/shuiyin.png");
        BufferedImage bufferedImage = Thumbnails.of(bi).watermark(Positions.CENTER, ImageIO.read(fontStream), 0.45f).scale(1f).asBufferedImage();
        String fileName = "lable_" + orderCode + ".png";
        String fileUrl = fileService.createFile(fileName, null, IoUtil.readBytes(bufferedImageToInputStream(bufferedImage)));
        return fileUrl;
    }

    @Override
    public String assignOrderInfoBarcodeLabel(OrderDetailInfoFacingObjectRespVO orderDetailInfoFacingObjectRespVO,
                                              String orderCodeUrl, String orderCode) throws IOException {
        int width = 320;
        int height = 115;
        String fontName = "华文楷体";
        // 得到图片缓冲区, INT精确度达到一定,RGB三原色
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bi.createGraphics();
        // 得到它的绘制环境(这张图片的笔)
        Graphics2D g2 = (Graphics2D) bi.getGraphics();
        // 设置背景颜色,并填充满张图片
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);
        // 设置图片字体颜色
        g2.setColor(Color.black);

        // 设置标题的字体,字号,大小
        g2.setFont(new Font(fontName, Font.BOLD, 40));
        g2.drawString("禁 止 撕 毁", width / 2 - 100, 45);
        // 抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 画标签一维码
        InputStream oneCodeInputStream = new URL(orderCodeUrl).openConnection().getInputStream();
        BufferedImage image3 = ImageIO.read(oneCodeInputStream);
        g2.drawImage(image3, 15, 50, 290, 50, null);

        g2.dispose();
        String fileName = "barcode_" + orderCode + ".png";
        String fileUrl = fileService.createFile(fileName, null, IoUtil.readBytes(bufferedImageToInputStream(bi)));
        return fileUrl;
    }

    @Override
    public List<OrderDetailInfoLableExcelVO> exportOrderDetailInfoLableExcel(OrderDetailInfoExportReqVO orderDetailInfoExportReqVO) throws MalformedURLException {
        // 面向下游排序获取订单信息
        List<OrderDetailInfoLableExcelVO> orderDetailInfoLableRespVoAllList = new ArrayList<>();
        List<OrderDetailInfoFacingObjectRespVO> orderDetailInfoFacingObjectRespVOList = orderDetailInfoMapper.selectListFacingUpstream(orderDetailInfoExportReqVO);
        List<List<OrderDetailInfoFacingObjectRespVO>> urlPageList = ListUtil.split(orderDetailInfoFacingObjectRespVOList, 36);
        for (List<OrderDetailInfoFacingObjectRespVO> urlPage : urlPageList) {
            List<OrderDetailInfoLableExcelVO> orderDetailInfoLableRespVoList = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                orderDetailInfoLableRespVoList.add(new OrderDetailInfoLableExcelVO());
            }
            List<List<OrderDetailInfoFacingObjectRespVO>> urlRowList = ListUtil.split(urlPage, 9);
            for (int i = 0; i < urlRowList.size(); i++) {
                List<OrderDetailInfoFacingObjectRespVO> urlRow = urlRowList.get(i);
                for (int j = 0; j < urlRow.size(); j++) {
                    String url = urlRow.get(j).getOrderOnedimensionalCodePictureUrl();
                    if (i == 0) {
                        orderDetailInfoLableRespVoList.get(j).setUrl1(new URL(url));
                    }
                    if (i == 1) {
                        orderDetailInfoLableRespVoList.get(j).setUrl2(new URL(url));
                    }
                    if (i == 2) {
                        orderDetailInfoLableRespVoList.get(j).setUrl3(new URL(url));
                    }
                    if (i == 3) {
                        orderDetailInfoLableRespVoList.get(j).setUrl4(new URL(url));
                    }
                }
            }
            orderDetailInfoLableRespVoAllList.addAll(orderDetailInfoLableRespVoList);
        }
        return orderDetailInfoLableRespVoAllList;
    }

    @Override
    public List<OrderDetailInfoBarcodeLableExcelVO> exportOrderDetailInfoBarCodeLableExcel(OrderDetailInfoExportReqVO orderDetailInfoExportReqVO) throws MalformedURLException {
        // 面向下游排序获取订单信息
        List<OrderDetailInfoBarcodeLableExcelVO> orderDetailInfoBarcodeLableRespVoAllList = new ArrayList<>();
        List<OrderDetailInfoFacingObjectRespVO> orderDetailInfoFacingObjectRespVOList = orderDetailInfoMapper.selectListFacingDownstream(orderDetailInfoExportReqVO);
        List<List<OrderDetailInfoFacingObjectRespVO>> urlPageList = ListUtil.split(orderDetailInfoFacingObjectRespVOList, 144);
        for (List<OrderDetailInfoFacingObjectRespVO> urlPage : urlPageList) {
            List<OrderDetailInfoBarcodeLableExcelVO> orderDetailInfoBarcodeLableRespVoList = new ArrayList<>();
            for (int i = 0; i < 24; i++) {
                orderDetailInfoBarcodeLableRespVoList.add(new OrderDetailInfoBarcodeLableExcelVO());
            }
            List<List<OrderDetailInfoFacingObjectRespVO>> urlRowList = ListUtil.split(urlPage, 24);
            for (int i = 0; i < urlRowList.size(); i++) {
                List<OrderDetailInfoFacingObjectRespVO> urlRow = urlRowList.get(i);
                for (int j = 0; j < urlRow.size(); j++) {
                    String url = urlRow.get(j).getOrderBarcodePictureUrl();
                    if (i == 0) {
                        orderDetailInfoBarcodeLableRespVoList.get(j).setUrl1(new URL(url));
                    }
                    if (i == 1) {
                        orderDetailInfoBarcodeLableRespVoList.get(j).setUrl2(new URL(url));
                    }
                    if (i == 2) {
                        orderDetailInfoBarcodeLableRespVoList.get(j).setUrl3(new URL(url));
                    }
                    if (i == 3) {
                        orderDetailInfoBarcodeLableRespVoList.get(j).setUrl4(new URL(url));
                    }
                    if (i == 4) {
                        orderDetailInfoBarcodeLableRespVoList.get(j).setUrl5(new URL(url));
                    }
                    if (i == 5) {
                        orderDetailInfoBarcodeLableRespVoList.get(j).setUrl6(new URL(url));
                    }
                }
            }
            orderDetailInfoBarcodeLableRespVoAllList.addAll(orderDetailInfoBarcodeLableRespVoList);
        }
        return orderDetailInfoBarcodeLableRespVoAllList;
    }

    @Override
    public PageResult<OrderDetailInfoUpstreamRespVO> selectUpstreamOrderPage(OrderDetailInfoUpstreamReqVO reqVO) {
        // 如果没有传所属日期则取最近订单的所属日期
        if (null == reqVO.getOrderDate() || 0 == reqVO.getOrderDate().length) {
            List<OrderDetailInfoUpstreamRespVO> list = orderDetailInfoMapper.selectUpstreamOrderPage(reqVO).getList();
            if (null != list && !list.isEmpty()) {
                LocalDate[] orderDateArr = new LocalDate[2];
                orderDateArr[0] = orderDateArr[1] = list.get(0).getOrderDate();
                reqVO.setOrderDate(orderDateArr);
            }
        }
        PageResult<OrderDetailInfoUpstreamRespVO> orderDetailInfoUpstreamRespVOPageResult = orderDetailInfoMapper.selectUpstreamOrderPage(reqVO);
        List<OrderDetailInfoUpstreamRespVO> list = orderDetailInfoUpstreamRespVOPageResult.getList();
        if (null != list && !list.isEmpty()) {
            OrderDetailInfoExportReqVO orderDetailInfoExportReqVO = new OrderDetailInfoExportReqVO();
            for (OrderDetailInfoUpstreamRespVO orderDetailInfoUpstreamRespVO : list) {
                orderDetailInfoExportReqVO.setOrderDate(reqVO.getOrderDate());
                orderDetailInfoExportReqVO.setUpstreamName(orderDetailInfoUpstreamRespVO.getUpstreamName());
                List<OrderDetailInfoFacingObjectRespVO> orderDetailInfoFacingObjectRespVOList = orderDetailInfoMapper.selectListFacingUpstream(orderDetailInfoExportReqVO);
                orderDetailInfoUpstreamRespVO.setOrderDetailInfoFacingObjectRespVOList(orderDetailInfoFacingObjectRespVOList);
            }
        }
        return orderDetailInfoUpstreamRespVOPageResult;
    }

    @Override
    public List<OrderDetailInfoFacingObjectRespVO> exportOrderDetailInfoFacingObjectExcel(OrderDetailInfoFacingObjectExportReqVO orderDetailInfoFacingObjectExportReqVO) throws MalformedURLException {
        // 面向下游排序获取订单信息
        List<OrderDetailInfoFacingObjectExcelVO> orderDetailInfoFacingObjectExcelVOAllList = new ArrayList<>();
        List<OrderDetailInfoFacingObjectRespVO> orderDetailInfoFacingObjectRespVOList = new ArrayList<>();
        if (orderDetailInfoFacingObjectExportReqVO.getIsFacingDownstream()) {
            orderDetailInfoFacingObjectRespVOList = orderDetailInfoMapper.selectListFacingDownstream(orderDetailInfoFacingObjectExportReqVO);
        } else {
            orderDetailInfoFacingObjectRespVOList = orderDetailInfoMapper.selectListFacingUpstream(orderDetailInfoFacingObjectExportReqVO);
        }
        HashMap<String, Integer> downstreamOrderCountMap = new HashMap<>();
        HashMap<String, Integer> upstreamOrderCountMap = new HashMap<>();
        HashMap<String, BigDecimal> downstreamOrderTotolPriceMap = new HashMap<>();
        HashMap<String, BigDecimal> upstreamOrderTotolPriceMap = new HashMap<>();
        // 进行人员订单计数
        for (OrderDetailInfoFacingObjectRespVO orderDetailInfoFacingObjectRespVO : orderDetailInfoFacingObjectRespVOList) {
            if (orderDetailInfoFacingObjectExportReqVO.getIsFacingDownstream()) {
                String downstreamName = orderDetailInfoFacingObjectRespVO.getDownstreamName();
                if (null == downstreamOrderCountMap.get(downstreamName)) {
                    downstreamOrderCountMap.put(downstreamName, 1);
                    downstreamOrderTotolPriceMap.put(downstreamName, orderDetailInfoFacingObjectRespVO.getOrderSalesAmount());
                } else {
                    downstreamOrderCountMap.put(downstreamName, downstreamOrderCountMap.get(downstreamName) + 1);
                    downstreamOrderTotolPriceMap.put(downstreamName, downstreamOrderTotolPriceMap.get(downstreamName)
                            .add(orderDetailInfoFacingObjectRespVO.getOrderSalesAmount()));
                }
            } else {
                String upstreamName = orderDetailInfoFacingObjectRespVO.getUpstreamName();
                if (null == upstreamOrderCountMap.get(upstreamName)) {
                    upstreamOrderCountMap.put(upstreamName, 1);
                    upstreamOrderTotolPriceMap.put(upstreamName, orderDetailInfoFacingObjectRespVO.getOrderCostAmount());
                } else {
                    upstreamOrderCountMap.put(upstreamName, upstreamOrderCountMap.get(upstreamName) + 1);
                    upstreamOrderTotolPriceMap.put(upstreamName, upstreamOrderTotolPriceMap.get(upstreamName)
                            .add(orderDetailInfoFacingObjectRespVO.getOrderCostAmount()));
                }
            }
        }
        Boolean isFacingDownstream = orderDetailInfoFacingObjectExportReqVO.getIsFacingDownstream();
        for (OrderDetailInfoFacingObjectRespVO orderDetailInfoFacingObjectRespVO : orderDetailInfoFacingObjectRespVOList) {
            String name = isFacingDownstream ?
                    orderDetailInfoFacingObjectRespVO.getDownstreamName() : orderDetailInfoFacingObjectRespVO.getUpstreamName();
            // 给下游送货方式简码
            String deliveryCode = DeliveryMethodEnum.valueOfType(Integer.valueOf(orderDetailInfoFacingObjectRespVO.getDeliveryMethod())).getCode();
            // 去上游提货方式简码
            String pickupCode = DeliveryMethodEnum.valueOfType(Integer.valueOf(orderDetailInfoFacingObjectRespVO.getPickupMethod())).getCode();
            String code = isFacingDownstream ? deliveryCode : pickupCode;
            name = new StringBuffer(name)
                    .append("\n(总计：")
                    .append(isFacingDownstream ? downstreamOrderCountMap.get(name) : upstreamOrderCountMap.get(name))
                    .append("，")
                    .append(SucodeUtil.getSucode(NumberUtil.toStr(isFacingDownstream ?
                            downstreamOrderTotolPriceMap.get(name) : upstreamOrderTotolPriceMap.get(name)
                    )))
                    .append("，")
                    .append(code)
                    .append(")").toString();
            orderDetailInfoFacingObjectRespVO.setTotalInfo(name);
        }
        return orderDetailInfoFacingObjectRespVOList;
    }


    @Override
    public List<OrderDetailInfoFacingObjectExcelVO> orderDetailInfoFacingObjectSort(
            List<OrderDetailInfoFacingObjectRespVO> orderDetailInfoFacingObjectRespVOList, boolean isFacingDownstream) throws MalformedURLException {
        List<OrderDetailInfoFacingObjectExcelVO> orderDetailInfoFacingObjectExcelVOAllList = new ArrayList<>();
        // 分解查询结果按每页按列顺序输出
        List<List<OrderDetailInfoFacingObjectRespVO>> urlPageList = ListUtil.split(orderDetailInfoFacingObjectRespVOList, 36);
        for (List<OrderDetailInfoFacingObjectRespVO> urlPage : urlPageList) {
            List<OrderDetailInfoFacingObjectExcelVO> orderDetailInfoFacingObjectExcelVOList = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                orderDetailInfoFacingObjectExcelVOList.add(new OrderDetailInfoFacingObjectExcelVO());
            }
            List<List<OrderDetailInfoFacingObjectRespVO>> urlRowList = ListUtil.split(urlPage, 9);
            for (int i = 0; i < urlRowList.size(); i++) {
                List<OrderDetailInfoFacingObjectRespVO> urlRow = urlRowList.get(i);
                for (int j = 0; j < urlRow.size(); j++) {
                    OrderDetailInfoFacingObjectRespVO orderDetailInfoFacingObjectRespVO = urlRow.get(j);
                    String name = isFacingDownstream ?
                            orderDetailInfoFacingObjectRespVO.getDownstreamName() : orderDetailInfoFacingObjectRespVO.getUpstreamName();
                    String url = orderDetailInfoFacingObjectRespVO.getOrderOnedimensionalCodePictureUrl();
                    if (i == 0) {
                        orderDetailInfoFacingObjectExcelVOList.get(j).setObjectName1(name);
                        orderDetailInfoFacingObjectExcelVOList.get(j).setUrl1(new URL(url));
                    }
                    if (i == 1) {
                        orderDetailInfoFacingObjectExcelVOList.get(j).setObjectName2(name);
                        orderDetailInfoFacingObjectExcelVOList.get(j).setUrl2(new URL(url));
                    }
                    if (i == 2) {
                        orderDetailInfoFacingObjectExcelVOList.get(j).setObjectName3(name);
                        orderDetailInfoFacingObjectExcelVOList.get(j).setUrl3(new URL(url));
                    }
                    if (i == 3) {
                        orderDetailInfoFacingObjectExcelVOList.get(j).setObjectName4(name);
                        orderDetailInfoFacingObjectExcelVOList.get(j).setUrl4(new URL(url));
                    }
                }
            }
            orderDetailInfoFacingObjectExcelVOAllList.addAll(orderDetailInfoFacingObjectExcelVOList);
        }
        return orderDetailInfoFacingObjectExcelVOAllList;
    }

    @Override
    public String updateOrderPictrue(InputStream inputStream) {
        return fileService.createFile(
                UUID.randomUUID().toString().replaceAll("-", "") + ".png", null, IoUtil.readBytes(inputStream));
    }

    @Override
    public PageResult<OrderDetailInfoDownstreamRespVO> selectDownstreamOrderPage(OrderDetailInfoDownstreamReqVO reqVO) {
        // 如果没有传所属日期则取最近订单的所属日期
        if (null == reqVO.getOrderDate() || 0 == reqVO.getOrderDate().length) {
            List<OrderDetailInfoDownstreamRespVO> list = orderDetailInfoMapper.selectDownstreamOrderPage(reqVO).getList();
            if (null != list && !list.isEmpty()) {
                LocalDate[] orderDateArr = new LocalDate[2];
                orderDateArr[0] = orderDateArr[1] = list.get(0).getOrderDate();
                reqVO.setOrderDate(orderDateArr);
            }
        }
        PageResult<OrderDetailInfoDownstreamRespVO> orderDetailInfoDownstreamRespVOPageResult = orderDetailInfoMapper.selectDownstreamOrderPage(reqVO);
        List<OrderDetailInfoDownstreamRespVO> list = orderDetailInfoDownstreamRespVOPageResult.getList();
        if (null != list && !list.isEmpty()) {
            OrderDetailInfoExportReqVO orderDetailInfoExportReqVO = new OrderDetailInfoExportReqVO();
            for (OrderDetailInfoDownstreamRespVO orderDetailInfoDownstreamRespVO : list) {
                orderDetailInfoExportReqVO.setOrderDate(reqVO.getOrderDate());
                orderDetailInfoExportReqVO.setDownstreamName(orderDetailInfoDownstreamRespVO.getDownstreamName());
                List<OrderDetailInfoFacingObjectRespVO> orderDetailInfoFacingObjectRespVOList = orderDetailInfoMapper.selectListFacingDownstream(orderDetailInfoExportReqVO);
                orderDetailInfoDownstreamRespVO.setOrderDetailInfoFacingObjectRespVOList(orderDetailInfoFacingObjectRespVOList);
            }
        }
        return orderDetailInfoDownstreamRespVOPageResult;
    }

    @Override
    public OrderDetailInfoFacingObjectRespVO getOrderDetailInfoByCode(String code) {
        OrderDetailInfoFacingObjectRespVO orderDetailInfoByCode = orderDetailInfoMapper.getOrderDetailInfoByCode(code);
        if (null != orderDetailInfoByCode) {
            OrderStatusTrackInfoExportReqVO orderStatusTrackInfoExportReqVO = new OrderStatusTrackInfoExportReqVO();
            orderStatusTrackInfoExportReqVO.setOrderCode(orderDetailInfoByCode.getOrderCode());
            List<OrderStatusTrackInfoDO> orderStatusTrackInfoDOList = orderStatusTrackInfoMapper.selectList(orderStatusTrackInfoExportReqVO);
            if (!orderStatusTrackInfoDOList.isEmpty()) {
                orderDetailInfoByCode.setOrderStatusTrackInfoDOList(orderStatusTrackInfoDOList);
            }
            // 统计信息
            OrderDetailInfoExportReqVO orderDetailInfoExportReqVO = new OrderDetailInfoExportReqVO();
            LocalDate[] localDates = new LocalDate[2];
            localDates[0] = localDates[1] = orderDetailInfoByCode.getOrderDate();
            orderDetailInfoExportReqVO.setOrderDate(localDates);
            orderDetailInfoExportReqVO.setUpstreamName(orderDetailInfoByCode.getUpstreamName());
            List<OrderDetailInfoDO> orderDetailInfoDOList = orderDetailInfoMapper.selectList(orderDetailInfoExportReqVO);
            int orderTotalCount = orderDetailInfoDOList.size();
            int putStorageCount = 0, noPutStorageCount = 0, untreatedCount = 0;
            BigDecimal putStorageOrderTotalAmont = BigDecimal.ZERO;
            for (OrderDetailInfoDO orderDetailInfoDO: orderDetailInfoDOList) {
                Integer orderStatus = Integer.valueOf(orderDetailInfoDO.getOrderStatus());
                if(OrderStatusEnum.ORDER_STATUS_REGISTRATION.getCode() != orderStatus
                        && OrderStatusEnum.ORDER_STATUS_NOT_WAREHOUSING.getCode() != orderStatus){
                    putStorageCount ++;
                    putStorageOrderTotalAmont = putStorageOrderTotalAmont.add(orderDetailInfoDO.getOrderCostAmount());
                }
                if(OrderStatusEnum.ORDER_STATUS_NOT_WAREHOUSING.getCode() == orderStatus){
                    noPutStorageCount ++;
                }
                if(OrderStatusEnum.ORDER_STATUS_REGISTRATION.getCode() == orderStatus){
                    untreatedCount ++;
                }
            }
            OrderDetailStatisticsInfoVO orderDetailStatisticsInfoVO = new OrderDetailStatisticsInfoVO();
            orderDetailStatisticsInfoVO.setOrderTotalCount(orderTotalCount);
            orderDetailStatisticsInfoVO.setPutStorageCount(putStorageCount);
            orderDetailStatisticsInfoVO.setNoPutStorageCount(noPutStorageCount);
            orderDetailStatisticsInfoVO.setUntreatedCount(untreatedCount);
            orderDetailStatisticsInfoVO.setPutStorageOrderTotalAmont(putStorageOrderTotalAmont);
            orderDetailInfoByCode.setOrderDetailStatisticsInfoVO(orderDetailStatisticsInfoVO);
        }
        return orderDetailInfoByCode;
    }

    @Override
    public void updateOrderStatus(OrderDetailInfoUpdateReqVO updateReqVO) {
        // 校验订单状态的有效性
        // 校验订单的有效性
        OrderDetailInfoDO orderDetailInfo = this.getOrderDetailInfo(updateReqVO.getId());
        if (null != orderDetailInfo && null != orderDetailInfo.getId()) {
            // 校验货物
            // 变更订单状态
            OrderDetailInfoDO orderDetailInfoDO = new OrderDetailInfoDO();
            orderDetailInfoDO.setId(updateReqVO.getId());
            orderDetailInfoDO.setOrderStatus(updateReqVO.getOrderStatus());
            orderDetailInfoDO.setGoodsCode(updateReqVO.getGoodsCode());
            orderDetailInfoMapper.updateById(orderDetailInfoDO);

            // 添加订单跟踪信息
            OrderStatusTrackInfoDO orderStatusTrackInfoDO = new OrderStatusTrackInfoDO();
            orderStatusTrackInfoDO.setOrderCode(orderDetailInfo.getOrderCode());
            orderStatusTrackInfoDO.setOrderAfterChangeStatus(updateReqVO.getOrderStatus());
            orderStatusTrackInfoMapper.insert(orderStatusTrackInfoDO);
        }
    }

    public static InputStream bufferedImageToInputStream(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", os);
        return new ByteArrayInputStream(os.toByteArray());
    }

}
