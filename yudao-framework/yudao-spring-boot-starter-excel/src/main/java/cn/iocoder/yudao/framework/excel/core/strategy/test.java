package cn.iocoder.yudao.framework.excel.core.strategy;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Arrays;

public class test {
    public static void main(String[] args) {
        int[] ints = StrUtil.splitToInt(NumberUtil.toStr(13), " ");
        for (int i = 0; i < ints.length; i++) {
            System.out.println(NumberUtil.decimalFormat(",#", 112));
            System.out.println("args = " + ints[i]);
        }
    }
}
