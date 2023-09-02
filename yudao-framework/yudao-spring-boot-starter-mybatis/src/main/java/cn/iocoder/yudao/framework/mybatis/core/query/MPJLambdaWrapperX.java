package cn.iocoder.yudao.framework.mybatis.core.query;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.util.collection.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * 拓展 MyBatis Plus Join MPJLambdaWrapper 类，主要增加如下功能：
 * <p>
 * 1. 拼接条件的方法，增加 xxxIfPresent 方法，用于判断值不存在的时候，不要拼接到条件中。
 *
 * @param <T> 数据类型
 */
public class MPJLambdaWrapperX<T> extends MPJLambdaWrapper<T> {

    public MPJLambdaWrapperX<T> likeIfPresent(SFunction<T, ?> column, String val) {
        if (StringUtils.hasText(val)) {
            return (MPJLambdaWrapperX<T>) super.like(column, val);
        }
        return this;
    }

    public MPJLambdaWrapperX<T> inIfPresent(SFunction<T, ?> column, Collection<?> values) {
        if (ObjectUtil.isAllNotEmpty(values) && !ArrayUtil.isEmpty(values)) {
            return (MPJLambdaWrapperX<T>) super.in(column, values);
        }
        return this;
    }

    public MPJLambdaWrapperX<T> inIfPresent(SFunction<T, ?> column, Object... values) {
        if (ObjectUtil.isAllNotEmpty(values) && !ArrayUtil.isEmpty(values)) {
            return (MPJLambdaWrapperX<T>) super.in(column, values);
        }
        return this;
    }

    public MPJLambdaWrapperX<T> eqIfPresent(SFunction<T, ?> column, Object val) {
        if (ObjectUtil.isNotEmpty(val)) {
            return (MPJLambdaWrapperX<T>) super.eq(column, val);
        }
        return this;
    }

    public MPJLambdaWrapperX<T> neIfPresent(SFunction<T, ?> column, Object val) {
        if (ObjectUtil.isNotEmpty(val)) {
            return (MPJLambdaWrapperX<T>) super.ne(column, val);
        }
        return this;
    }

    public MPJLambdaWrapperX<T> gtIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (MPJLambdaWrapperX<T>) super.gt(column, val);
        }
        return this;
    }

    public MPJLambdaWrapperX<T> geIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (MPJLambdaWrapperX<T>) super.ge(column, val);
        }
        return this;
    }

    public MPJLambdaWrapperX<T> ltIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (MPJLambdaWrapperX<T>) super.lt(column, val);
        }
        return this;
    }

    public MPJLambdaWrapperX<T> leIfPresent(SFunction<T, ?> column, Object val) {
        if (val != null) {
            return (MPJLambdaWrapperX<T>) super.le(column, val);
        }
        return this;
    }

    public MPJLambdaWrapperX<T> betweenIfPresent(SFunction<T, ?> column, Object val1, Object val2) {
        if (val1 != null && val2 != null) {
            return (MPJLambdaWrapperX<T>) super.between(column, val1, val2);
        }
        if (val1 != null) {
            return (MPJLambdaWrapperX<T>) ge(column, val1);
        }
        if (val2 != null) {
            return (MPJLambdaWrapperX<T>) le(column, val2);
        }
        return this;
    }

    public MPJLambdaWrapperX<T> betweenIfPresent(SFunction<T, ?> column, Object[] values) {
        Object val1 = ArrayUtils.get(values, 0);
        Object val2 = ArrayUtils.get(values, 1);
        return betweenIfPresent(column, val1, val2);
    }

    // ========== 重写父类方法，方便s链式调用 ==========

    @Override
    public MPJLambdaWrapperX<T> selectAll(Class clazz) {
        super.selectAll(clazz);
        return this;
    }

    public <X> MPJLambdaWrapperX<T> selectX(SFunction<X, ?>... columns){
        super.select(columns);
        return this;
    }

    public <X> MPJLambdaWrapperX<T> leftJoinX(Class<X> clazz, SFunction<X, ?> left, SFunction<T, ?> right){
        super.leftJoin(clazz, left, right);
        return (MPJLambdaWrapperX<T>)this;
    }

    @Override
    public <X> MPJLambdaWrapperX<T> orderByAsc(SFunction<X, ?> column){
        super.orderByAsc(column);
        return this;
    }

    @Override
    public <X> MPJLambdaWrapperX<T> orderByDesc(SFunction<X, ?> column){
        super.orderByAsc(column);
        return this;
    }

}
