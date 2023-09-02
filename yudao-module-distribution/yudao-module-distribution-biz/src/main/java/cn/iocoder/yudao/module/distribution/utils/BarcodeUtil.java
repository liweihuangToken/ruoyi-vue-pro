package cn.iocoder.yudao.module.distribution.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.AbstractBarcodeBean;
import org.krysalis.barcode4j.impl.codabar.CodabarBean;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.impl.datamatrix.DataMatrixBean;
import org.krysalis.barcode4j.impl.fourstate.RoyalMailCBCBean;
import org.krysalis.barcode4j.impl.fourstate.USPSIntelligentMailBean;
import org.krysalis.barcode4j.impl.int2of5.Interleaved2Of5Bean;
import org.krysalis.barcode4j.impl.pdf417.PDF417Bean;
import org.krysalis.barcode4j.impl.postnet.POSTNETBean;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.impl.upcean.EAN8Bean;
import org.krysalis.barcode4j.impl.upcean.UPCABean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 条形码生成工具
 */
@Slf4j
public class BarcodeUtil {
    /**
     * 生成code39条形码
     *
     * @param message       要生成的文本
     * @param fontSize      条形码字体大小
     * @param height        条形码的高度
     * @param width         条形码的宽度
     * @param withQuietZone 是否两边留白
     * @param quietZone     withQuietZone为true可用 留白宽度
     * @return
     */
    public static byte[] generateBarCode39(String message, Double fontSize, Double height, Double width, boolean withQuietZone, Double quietZone) {
        if (StringUtils.isEmpty(message)) {
            return null;
        }
        return generateBar(new Code39Bean(), message, fontSize, height, width, withQuietZone, quietZone);
    }

    /**
     * 生成code128条形码
     *
     * @param message       要生成的文本
     * @param fontSize      条形码字体大小
     * @param height        条形码的高度
     * @param width         条形码的宽度
     * @param withQuietZone 是否两边留白
     * @param quietZone     withQuietZone为true可用 留白宽度
     * @return
     */
    public static byte[] generateBarCode128(String message, Double fontSize, Double height, Double width, boolean withQuietZone, Double quietZone) {
        if (StringUtils.isEmpty(message)) {
            return null;
        }
        return generateBar(new Code128Bean(), message, fontSize, height, width, withQuietZone, quietZone);
    }

    /**
     * 生成EAN13条形码
     *
     * @param message       要生成的文本  Valid are 0-9 only length: 11-12
     * @param fontSize      条形码字体大小
     * @param height        条形码的高度
     * @param width         条形码的宽度
     * @param withQuietZone 是否两边留白
     * @param quietZone     withQuietZone为true可用 留白宽度
     * @return
     */
    public static byte[] generateBarEAN13(String message, Double fontSize, Double height, Double width, boolean withQuietZone, Double quietZone) {
        if (StringUtils.isEmpty(message)) {
            return null;
        }
        return generateBar(new EAN13Bean(), message, fontSize, height, width, withQuietZone, quietZone);
    }

    /**
     * 生成EAN8条形码
     *
     * @param message       要生成的文本 Valid are 0-9 only length: 7-8
     * @param fontSize      条形码字体大小
     * @param height        条形码的高度
     * @param width         条形码的宽度
     * @param withQuietZone 是否两边留白
     * @param quietZone     withQuietZone为true可用 留白宽度
     * @return
     */
    public static byte[] generateBarEAN8(String message, Double fontSize, Double height, Double width, boolean withQuietZone, Double quietZone) {
        if (StringUtils.isEmpty(message)) {
            return null;
        }
        return generateBar(new EAN8Bean(), message, fontSize, height, width, withQuietZone, quietZone);
    }

    /**
     * 生成Codabar条形码
     *
     * @param message       要生成的文本 Valid are 0-9 and a-e
     * @param fontSize      条形码字体大小
     * @param height        条形码的高度
     * @param width         条形码的宽度
     * @param withQuietZone 是否两边留白
     * @param quietZone     withQuietZone为true可用 留白宽度
     * @return
     */
    public static byte[] generateBarCodabar(String message, Double fontSize, Double height, Double width, boolean withQuietZone, Double quietZone) {
        if (StringUtils.isEmpty(message)) {
            return null;
        }
        return generateBar(new CodabarBean(), message, fontSize, height, width, withQuietZone, quietZone);
    }

    /**
     * 生成UPCA条形码
     *
     * @param message       要生成的文本 Valid are 0-9 lenth:11-12
     * @param fontSize      条形码字体大小
     * @param height        条形码的高度
     * @param width         条形码的宽度
     * @param withQuietZone 是否两边留白
     * @param quietZone     withQuietZone为true可用 留白宽度
     * @return
     */
    public static byte[] generateBarUPCA(String message, Double fontSize, Double height, Double width, boolean withQuietZone, Double quietZone) {
        if (StringUtils.isEmpty(message)) {
            return null;
        }
        return generateBar(new UPCABean(), message, fontSize, height, width, withQuietZone, quietZone);
    }

    /**
     * 生成POSTNET条形码
     *
     * @param message       要生成的文本 Valid are 0-9
     * @param fontSize      条形码字体大小
     * @param height        条形码的高度
     * @param width         条形码的宽度
     * @param withQuietZone 是否两边留白
     * @param quietZone     withQuietZone为true可用 留白宽度
     * @return
     */
    public static byte[] generateBarPOSTNET(String message, Double fontSize, Double height, Double width, boolean withQuietZone, Double quietZone) {
        if (StringUtils.isEmpty(message)) {
            return null;
        }
        return generateBar(new POSTNETBean(), message, fontSize, height, width, withQuietZone, quietZone);
    }

    /**
     * 生成RoyalMailCBC条形码
     *
     * @param message       要生成的文本 Valid are 0-9
     * @param fontSize      条形码字体大小
     * @param height        条形码的高度
     * @param width         条形码的宽度
     * @param withQuietZone 是否两边留白
     * @param quietZone     withQuietZone为true可用 留白宽度
     * @return
     */
    public static byte[] generateBarRoyalMailCBC(String message, Double fontSize, Double height, Double width, boolean withQuietZone, Double quietZone) {
        if (StringUtils.isEmpty(message)) {
            return null;
        }
        return generateBar(new RoyalMailCBCBean(), message, fontSize, height, width, withQuietZone, quietZone);
    }

    /**
     * 生成USPSIntelligentMail条形码
     *
     * @param message       要生成的文本 Valid are 0-9 length:20
     * @param fontSize      条形码字体大小
     * @param height        条形码的高度
     * @param width         条形码的宽度
     * @param withQuietZone 是否两边留白
     * @param quietZone     withQuietZone为true可用 留白宽度
     * @return
     */
    public static byte[] generateBarUSPSIntelligentMail(String message, Double fontSize, Double height, Double width, boolean withQuietZone, Double quietZone) {
        if (StringUtils.isEmpty(message)) {
            return null;
        }
        return generateBar(new USPSIntelligentMailBean(), message, fontSize, height, width, withQuietZone, quietZone);
    }

    /**
     * 生成PDF417条形码
     *
     * @param message       要生成的文本 Valid are 0-9 length:20
     * @param fontSize      条形码字体大小
     * @param height        条形码的高度
     * @param width         条形码的宽度
     * @param withQuietZone 是否两边留白
     * @param quietZone     withQuietZone为true可用 留白宽度
     * @return
     */
    public static byte[] generateBarPDF417(String message, Double fontSize, Double height, Double width, boolean withQuietZone, Double quietZone) {
        if (StringUtils.isEmpty(message)) {
            return null;
        }
        return generateBar(new PDF417Bean(), message, fontSize, height, width, withQuietZone, quietZone);
    }

    /**
     * 生成DataMatrix条形码
     *
     * @param message       要生成的文本 Valid are 0-9 length:20
     * @param fontSize      条形码字体大小
     * @param height        条形码的高度
     * @param width         条形码的宽度
     * @param withQuietZone 是否两边留白
     * @param quietZone     withQuietZone为true可用 留白宽度
     * @return
     */
    public static byte[] generateBarDataMatrix(String message, Double fontSize, Double height, Double width, boolean withQuietZone, Double quietZone) {
        if (StringUtils.isEmpty(message)) {
            return null;
        }
        return generateBar(new DataMatrixBean(), message, fontSize, height, width, withQuietZone, quietZone);
    }

    /**
     * 生成Interleaved2Of5条形码
     *
     * @param message       要生成的文本 Valid are 0-9
     * @param fontSize      条形码字体大小
     * @param height        条形码的高度
     * @param width         条形码的宽度
     * @param withQuietZone 是否两边留白
     * @param quietZone     withQuietZone为true可用 留白宽度
     * @return
     */
    public static byte[] generateBarInterleaved2Of5(String message, Double fontSize, Double height, Double width, boolean withQuietZone, Double quietZone) {
        if (StringUtils.isEmpty(message)) {
            return null;
        }
        return generateBar(new Interleaved2Of5Bean(), message, fontSize, height, width, withQuietZone, quietZone);
    }

    /**
     * 生成条形码
     *
     * @param bean          条形码类型
     * @param message       要生成的文本
     * @param fontSize      条形码字体大小
     * @param height        条形码的高度
     * @param width         条形码的宽度
     * @param withQuietZone 是否两边留白
     * @param quietZone     withQuietZone为true可用 留白宽度
     * @return
     */
    private static byte[] generateBar(AbstractBarcodeBean bean, String message, Double fontSize, Double height, Double width, boolean withQuietZone, Double quietZone) {

        // 条码数据显示位置
        bean.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);

        if (fontSize != null) {
            bean.setFontSize(fontSize);
        } else {
            bean.setFontSize(5);
        }
        // 两端留白宽度
        if (quietZone != null) {
            bean.setQuietZone(quietZone);
        }

        // 精细度
        final int dpi = 256;

        // 设置条码每一条的宽度
        // UnitConv 是barcode4j 提供的单位转换的实体类，用于毫米mm,像素px,英寸in,点pt之间的转换
        // 设置条形码高度和宽度
        if (height != null) {
            bean.setBarHeight(height);
        } else {
            bean.setBarHeight(20);
        }
        if (width != null) {
            bean.setModuleWidth(UnitConv.in2mm(width / dpi));
        } else {
            bean.setModuleWidth(UnitConv.in2mm(5.0f / dpi));
        }

        // 设置两侧是否留白
        bean.doQuietZone(withQuietZone);

        String format = "image/png";
        ByteArrayOutputStream ous = null;
        byte[] imageByte;
        try {
            ous = new ByteArrayOutputStream();
            // 输出到流
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(ous, format, dpi,
                    BufferedImage.TYPE_BYTE_BINARY, false, 0);
            // 生成条形码
            bean.generateBarcode(canvas, message);
            // 结束绘制
            canvas.finish();
            imageByte = ous.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (null != ous) {
                    ous.close();
                }
            } catch (Exception e) {
                log.error("ByteArrayOutputStream流关闭失败，失败原因为：{{}}", e.getMessage());
            }
        }
        return imageByte;
    }
}
