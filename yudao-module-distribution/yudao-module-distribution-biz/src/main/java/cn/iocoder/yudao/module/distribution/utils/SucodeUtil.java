package cn.iocoder.yudao.module.distribution.utils;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 苏码生成工具
 */
public class SucodeUtil {
    private SucodeUtil(){}

    public static String getSucode(String priceStr){
        if(StrUtil.isEmpty(priceStr)){
            return priceStr;
        }
        return priceStr.replaceAll("0","〇")
                // .replaceAll("0","〇")
                .replaceAll("1","〡")
                .replaceAll("2","〢")
                .replaceAll("3","〣")
                .replaceAll("4","〤")
                .replaceAll("5","〥")
                .replaceAll("6","〦")
                .replaceAll("7","〧")
                .replaceAll("8","〨")
                .replaceAll("9","〩");
    }

    public static String getRandomSucode(){
        Integer[] integers = NumberUtil.generateBySet(0, 9, 1);
        return getSucode(integers[0].toString());
    }
}
