package com.liu.nyxs.common.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author ：haoshen
 * @date ：2023-11-29
 * @description : 对象类型转换器
 */
@SuppressWarnings({"rawtypes"})
public interface ObjectConverter<T> extends Converter<T, Object> {

    //无需实现
    @Override
    default Object convert(T source) {
        return source;
    }

    /**
     * @param source 源对象
     * @param targetType 目标类型
     * @return 转换后的对象
     * 如果转换失败，会返回null
     */
    Object convert(T source, Type targetType);

    @Override
    default Type[] getConverterTypes() {
        Class<? extends ObjectConverter> clazz = this.getClass();
        Type[] interfaces = clazz.getGenericInterfaces();
        Type converterType = null;
        for (Type interfaceType : interfaces) {
            Type rawType = ((ParameterizedType) interfaceType).getRawType();
            if (ObjectConverter.class.equals(rawType)) {
                converterType = interfaceType;
                break;
            }
        }
        ParameterizedType parameterizedType = (ParameterizedType) converterType;
        return parameterizedType.getActualTypeArguments();
    }

}
