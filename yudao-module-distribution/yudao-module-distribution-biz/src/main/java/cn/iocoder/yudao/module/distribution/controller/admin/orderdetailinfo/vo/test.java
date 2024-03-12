package cn.iocoder.yudao.module.distribution.controller.admin.orderdetailinfo.vo;

import cn.hutool.core.io.IoUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class test {
    public static void main(String[] args) throws IOException {
        int a = 199;
        System.out.printf(String.valueOf(a % 9));
//        int width = 320;
//        int height = 116;
//        String fontName = "华文楷体";
//        // 得到图片缓冲区, INT精确度达到一定,RGB三原色
//        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        bi.createGraphics();
//        // 得到它的绘制环境(这张图片的笔)
//        Graphics2D g2 = (Graphics2D) bi.getGraphics();
//        // 设置背景颜色,并填充满张图片
//        g2.setColor(Color.WHITE);
//        g2.fillRect(0, 0, width, height);
//        // 设置图片字体颜色
//        g2.setColor(Color.black);
//
//        // 设置标题的字体,字号,大小
//        g2.setFont(new Font(fontName, Font.BOLD, 40));
//        g2.drawString("禁 止 撕 毁", width / 2 - 100, 40);
//        // 抗锯齿
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//        // 画标签一维码
//        String orderCodeUrl = "http://62.234.51.147:48080/admin-api/infra/file/5/get/ed0c31854253ecbaea91bac9a1fec4e053b47cf05df4014eba851e785fd86ec4.png";
//        InputStream oneCodeInputStream = new URL(orderCodeUrl).openConnection().getInputStream();
//        BufferedImage image3 = ImageIO.read(oneCodeInputStream);
//        g2.drawImage(image3, 15, 60, 290, 50, null);
//
//        String fileName = new Random().nextInt(99999999) + ".png";
//        File file = new File("D:\\data\\" + fileName);
//        ImageIO.write(bi, "png", file);
    }
}
