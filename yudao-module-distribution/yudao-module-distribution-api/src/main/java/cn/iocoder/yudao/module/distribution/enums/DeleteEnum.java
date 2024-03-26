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
public enum DeleteEnum implements IntArrayValuable{

    /**
     * 删除
     */
    YES(1, "删除"),

    /**
     * 未删除
     */
    NO(0, "未删除");


    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(DeleteEnum::getCode).toArray();

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

    public static DeleteEnum valueOfType(Integer code) {
        return ArrayUtil.firstMatch(o -> o.getCode().equals(code), values());
    }

}
