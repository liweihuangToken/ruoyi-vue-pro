package cn.iocoder.yudao.framework.mybatis.core.query;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.collection.ArrayUtils;
import cn.iocoder.yudao.framework.mybatis.core.util.MyBatisUtils;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.github.yulichang.wrapper.enums.DefaultFuncEnum;
import org.apache.ibatis.annotations.Param;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

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

    public <R> MPJLambdaWrapperX<T> eqIfPresent(SFunction<R, ?> column, Object val) {
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

    public <X> MPJLambdaWrapperX<T> selectX(SFunction<X, ?>... columns) {
        super.select(columns);
        return this;
    }

    @Override
    public <S> MPJLambdaWrapperX<T> selectCount(SFunction<S, ?> column) {
        return (MPJLambdaWrapperX<T>) super.selectCount(column);
    }

    @Override
    public <S, X> MPJLambdaWrapperX<T> selectCount(SFunction<S, ?> column, SFunction<X, ?> alias) {
        return (MPJLambdaWrapperX<T>) super.selectCount(column, alias);
    }

    public <S, X> MPJLambdaWrapperX<T> selectSumX(SFunction<S, ?> column, SFunction<X, ?> alias) {
        return (MPJLambdaWrapperX) super.selectSum(column, alias);
    }

    public <S, X> MPJLambdaWrapperX<T> selectSumX(SFunction<S, ?> column, String alias) {
        return (MPJLambdaWrapperX) super.selectSum(column, alias);
    }

    public <S> MPJLambdaWrapperX<T> selectMaxX(SFunction<S, ?> column) {
        return (MPJLambdaWrapperX) super.selectMax(column);
    }

    public <S, X> MPJLambdaWrapperX<T> selectMaxX(SFunction<S, ?> column, SFunction<X, ?> alias) {
        return (MPJLambdaWrapperX) super.selectMax(column, alias);
    }

    public <S> MPJLambdaWrapperX selectMinX(SFunction<S, ?> column) {
        return (MPJLambdaWrapperX) super.selectMin(column);
    }

    public <S, X> MPJLambdaWrapperX selectMinX(SFunction<S, ?> column, SFunction<X, ?> alias) {
        return (MPJLambdaWrapperX) super.selectMin(column, alias);
    }

    public <X> MPJLambdaWrapperX<T> leftJoinX(Class<X> clazz, SFunction<X, ?> left, SFunction<T, ?> right) {
        super.leftJoin(clazz, left, right);
        return (MPJLambdaWrapperX<T>) this;
    }

    @Override
    public <R> MPJLambdaWrapperX<T> groupBy(SFunction<R, ?> column) {
        return (MPJLambdaWrapperX) super.groupBy(column);
    }

    @Override
    public <X> MPJLambdaWrapperX<T> orderByAsc(SFunction<X, ?> column) {
        super.orderByAsc(column);
        return this;
    }

    @Override
    public <X> MPJLambdaWrapperX<T> orderByDesc(SFunction<X, ?> column) {
        super.orderByAsc(column);
        return this;
    }

    @Override
    public MPJLambdaWrapperX<T> or() {
        return (MPJLambdaWrapperX) super.or();
    }
}
