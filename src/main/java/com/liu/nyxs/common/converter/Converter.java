package com.liu.nyxs.common.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author ：haoshen
 * @date ：2023-03-09
 * @description 类型转换器
 */
@FunctionalInterface
public interface Converter<T, F> {
    F convert(T source);

    /**
     * 获取转换类型
     *
     * @return Type[0]是转换前的类型，Type[1]是转换后的类型
     */
    default Type[] getConverterTypes() {
        Class<? extends Converter> clazz = this.getClass();
        Type[] interfaces = clazz.getGenericInterfaces();
        Type converterType = null;
        for (Type interfaceType : interfaces) {
            Type rawType = ((ParameterizedType) interfaceType).getRawType();
            if (Converter.class.equals(rawType)) {
                converterType = interfaceType;
                break;
            }
        }
        ParameterizedType parameterizedType = (ParameterizedType) converterType;
        return parameterizedType.getActualTypeArguments();
    }

}
