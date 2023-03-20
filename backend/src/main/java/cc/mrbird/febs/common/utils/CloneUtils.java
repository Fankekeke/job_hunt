package cc.mrbird.febs.common.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * 对象拷贝
 *
 * @author 王泽宁
 * @date 2021/7/05 09:15
 */
public class CloneUtils {

    /**
     * 拷贝对象
     *
     * @param source 拷贝源
     * @param classTargetType 对象类型
     * @return 返回对象
     */
    public static <T, E> E clone(T source, Class<E> classTargetType) {

        if (source == null) {
            return null;
        }
        E targetInstance;
        try {
            targetInstance = classTargetType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        BeanUtils.copyProperties(source, targetInstance);
        return targetInstance;
    }

    /**
     * 拷贝数组对象
     * @param sourceList 拷贝源
     * @param classType 对象类型
     * @return 返回对象
     */
    public static <T, E> List<E> batchClone(List<T> sourceList, Class<E> classType) {
        if (sourceList == null) {
            return null;
        }
        List<E> result = new ArrayList<>();
        for (T t : sourceList) {
            result.add(clone(t, classType));
        }
        return result;
    }

    /**
     * 浅拷贝对象
     *
     * @param sourceObj   被拷贝对象
     * @param targetClass 拷贝类型
     */
    public static <T> T convertToTarget(Object sourceObj, Class<T> targetClass) {
        T targetObj;
        try {
            targetObj = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        BeanUtils.copyProperties(sourceObj, targetObj);
        return targetObj;
    }

    /**
     * 浅拷贝数组对象
     *
     * @param sourceList  被拷贝数组
     * @param targetClass 拷贝类型
     */
    public static <T> List<T> convertToTargetList(List<?> sourceList, Class<T> targetClass) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        if (targetClass == null) {
            return null;
        }
        List<T> targetList = new ArrayList<>();
        for (Object sourceObj : sourceList) {
            T targetObj = convertToTarget(sourceObj, targetClass);
            targetList.add(targetObj);
        }
        return targetList;
    }

    /**
     * 浅拷贝对象(性能++)
     *
     * @param source  被拷贝数组
     * @param dest 拷贝类型
     */
    public static <S, T> T convert(S source, Class<T> dest, Function<T, T> function) {
        if (source == null) {
            return null;
        }
        try {
            T result = dest.newInstance();
            final BeanCopier copier = BeanCopier.create(source.getClass(), dest, false);
            copier.copy(source, result, null);
            if (function != null) {
                function.apply(result);
            }
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 浅拷贝对象(性能++)
     *
     * @param copier 代理对象
     * @param dest 目标类型
     * @param function 功能校验
     */
    public static <S, T> T convert(BeanCopier copier, S source, Class<T> dest, Function<T, T> function) {
        if (source == null) {
            return null;
        }
        try {
            T result = dest.newInstance();
            copier.copy(source, result, null);
            if (function != null) {
                function.apply(result);
            }
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 浅拷贝数组(性能++)
     * @param sourceList 被拷贝数组
     * @param dest 目标类型
     * @param function 功能校验
     * @return 目标类型数组
     */
    public static <S, T> List<T> convertList(List<S> sourceList, Class<T> dest, Function<T, T> function) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return Collections.emptyList();
        }
        try {
            List<T> resultList = new ArrayList<>();
            final BeanCopier copier = BeanCopier.create(sourceList.get(0).getClass(), dest, false);
            for (S source : sourceList) {
                T result = dest.newInstance();
                copier.copy(source, result, null);
                resultList.add(result);
            }
            if (function != null) {
                resultList.forEach(function::apply);
            }
            return resultList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * 创建代理对象
     * @param source 源类型
     * @param dest 目标类型
     * @return 代理对象
     */
    public static <S, T> BeanCopier createCopier(Class<S> source, Class<T> dest) {
        return BeanCopier.create(source, dest, false);
    }
}
