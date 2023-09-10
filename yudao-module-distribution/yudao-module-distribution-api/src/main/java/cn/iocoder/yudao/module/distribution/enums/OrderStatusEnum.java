package cn.iocoder.yudao.module.distribution.enums;

import cn.hutool.core.util.ArrayUtil;
import cn.iocoder.yudao.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 社交平台的类型枚举
 *
 * @author 芋道源码
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum implements IntArrayValuable{

    /**
     * 订单登记
     */
    ORDER_STATUS_REGISTRATION(0, "订单登记"),

    /**
     * 货物入库
     */
    ORDER_STATUS_WAREHOUSING(1, "已入库"),

    /**
     * 未入库
     */
    ORDER_STATUS_NOT_WAREHOUSING(2, "无货"),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(OrderStatusEnum::getCode).toArray();

    /**
     * 类型
     */
    private final Integer code;
    /**
     * 类型的标识
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    public static OrderStatusEnum valueOfType(Integer code) {
        return ArrayUtil.firstMatch(o -> o.getCode().equals(code), values());
    }

}
