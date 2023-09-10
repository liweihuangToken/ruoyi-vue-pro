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
public enum DeliveryMethodEnum implements IntArrayValuable{

    /**
     * 自取
     */
    DELIVERY_METHOD_SELF(1, "S"),

    /**
     * 配送
     */
    DELIVERY_METHOD_DELIVERY(2, "D"),

    /**
     * 代发
     */
    DELIVERY_METHOD_REPLACE(3, "R"),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(DeliveryMethodEnum::getType).toArray();

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 类型的标识
     */
    private final String code;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    public static DeliveryMethodEnum valueOfType(Integer type) {
        return ArrayUtil.firstMatch(o -> o.getType().equals(type), values());
    }

}
