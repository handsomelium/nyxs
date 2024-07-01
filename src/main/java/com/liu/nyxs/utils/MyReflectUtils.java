package com.liu.nyxs.utils;

import com.liu.nyxs.common.UnKnownType;
import com.liu.nyxs.common.converter.ObjectConverter;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：haoshen
 * @date ：2023-04-27
 * @description 自定义反射工具类
 */
@Slf4j
@SuppressWarnings({"unchecked", "rawtypes"})
public class MyReflectUtils {

    //获取指定私有属性（包括父类）
    public static Field getPrivateField(Class<?> clazz, String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Class<?> superclass = clazz.getSuperclass();
            if (Objects.nonNull(superclass)) {
                field = getPrivateField(superclass, fieldName);
            }
        }
        return field;
    }

    //获取当前类及其父类的所有私有属性
    public static Field[] getAllDeclaredFields(Class<?> clazz) {
        return getAllDeclaredFields(clazz, null);
    }

    //获取当前类及其父类的所有私有属性（superCount表示最多向上几层）
    public static Field[] getAllDeclaredFields(Class<?> clazz, Integer superCount) {
        if (Objects.isNull(superCount) || superCount < 0) {
            superCount = Integer.MAX_VALUE;
        }
        List<Field> fields = getFields(clazz, new AtomicInteger(0), superCount, null);
        return fields.toArray(new Field[0]);
    }

    @SafeVarargs
    private static List<Field> getFields(Class<?> clazz, AtomicInteger count, Integer superCount, List<Field> fields,
                                         Class<? extends Annotation>... annotations) {
        superCount = Objects.isNull(superCount) || superCount < 0 ? Integer.MAX_VALUE : superCount;
        //递归结束条件
        if (Objects.isNull(clazz) || count.get() > superCount) {
            return fields;
        }
        final boolean checkAnnotation = !MyCollectionUtils.isArrayEmpty(annotations);
        fields = Objects.isNull(fields) ? new ArrayList<>() : fields;
        for (Field field : clazz.getDeclaredFields()) {
            if (checkAnnotation) {
                //只要存在一个注解，则会返回
                for (Class<? extends Annotation> annotation : annotations) {
                    if (field.isAnnotationPresent(annotation)) {
                        fields.add(field);
                        break;
                    }
                }
                continue;
            }
            fields.add(field);
        }
        count.addAndGet(1);
        getFields(clazz.getSuperclass(), count, superCount, fields, annotations);
        return fields;
    }

    private static List<Field> getFields(Class<?> clazz, AtomicInteger count, Integer superCount, List<Field> fields) {
        if (Objects.isNull(fields)) {
            fields = new ArrayList<>();
        }
        if (Objects.isNull(clazz) || count.get() > superCount) {
            return fields;
        }
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        count.addAndGet(1);
        getFields(clazz.getSuperclass(), count, superCount, fields);
        return fields;
    }

    //调用构造器创建对象（空参）
    public static <T> T createObject(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("can not find empty constructor, create object fail");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getAllDeclaredFieldsName(Class<?> clazz) {
        List<String> res = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            res.add(field.getName());
        }
        return res;
    }

    //获取当前类及其父类的所有标注了指定注解的私有属性
    public static List<Field> getAllAnnotationFields(Class<?> clazz, Class<? extends Annotation> annotation) {
        return getFields(clazz, new AtomicInteger(0), Integer.MAX_VALUE, null, annotation);
    }

    public static List<String> getAllTableFieldsName(Class<?> clazz, String prefix) {
        List<String> res = new ArrayList<>();
        prefix = Objects.isNull(prefix) ? "" : prefix;
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = "";
            if (field.isAnnotationPresent(TableId.class)) {
                fieldName = field.getAnnotation(TableId.class).value();
            } else if (field.isAnnotationPresent(TableField.class) && field.getAnnotation(TableField.class).exist()) {
                fieldName = field.getAnnotation(TableField.class).value();
            }
            if (StringUtils.isNotBlank(fieldName)) {
                res.add(prefix.concat(fieldName));
            }
        }
        return res;
    }


    //复制属性
    public static void copyProp(Map<String, Object> src, Object target, ObjectConverter<?>... converters) {
        Assert.notNull(src, "src is null !");
        Assert.notNull(target, "target is null !");
        Map<Type, ObjectConverter> converterMap;
        if (converters != null && converters.length > 0) {
            converterMap = getConverterMap(converters);
        } else {
            converterMap = null;
        }
        Class<?> clazz = target.getClass();
        for (Map.Entry<String, Object> entry : src.entrySet()) {
            String fieldName = entry.getKey();
            Optional.ofNullable(getPrivateField(clazz, fieldName)).ifPresent(field -> {
                Optional.ofNullable(entry.getValue()).ifPresent(val -> {
                    Class<?> valType = val.getClass();
                    Class<?> targetType = field.getType();
                    Object targetVal = entry.getValue();
                    if (!valType.isAssignableFrom(targetType) && converterMap != null) {
                        targetVal = convert(valType, targetType, val, converterMap);
                    }
                    field.setAccessible(true);
                    try {
                        field.set(target, targetVal);
                    } catch (Exception e) {
                        log.error("类型赋值异常 => ", e);
                    }
                });
            });
        }
    }

    private static Map<Type, ObjectConverter> getConverterMap(ObjectConverter<?>[] converters) {
        Map<Type, ObjectConverter> converterMap = new HashMap<>();
        for (ObjectConverter converter : converters) {
            Type[] converterTypes = converter.getConverterTypes();
            Type sourceType = converterTypes[0];
            if (sourceType != null && sourceType != Object.class) {
                converterMap.put(sourceType, converter);
            }
        }
        return converterMap;
    }

    //处理类型转换
    private static Object convert(Class<?> sourceType, Class<?> targetType, Object val,
                                  Map<Type, ObjectConverter> converterMap) {
        return Optional.ofNullable(converterMap.getOrDefault(sourceType, null))
                .map(converter -> converter.convert(val, targetType))
                .orElse(null);
    }

    //获取集合的泛型
    public static Class<?> getCollGenericType(Field field, Object parentObject) {
        Class<?> fieldType;
        try {
            field.setAccessible(true);
            fieldType = parentObject == null || field.get(parentObject) == null ? field.getType() :
                    field.get(parentObject).getClass();
        } catch (IllegalAccessException e) {
            fieldType = field.getType();
        }
        if (fieldType.isArray()) {
            Class<?> componentType = fieldType.getComponentType();
            return componentType.isPrimitive() ? UnKnownType.class : componentType;
        } else if (Collection.class.isAssignableFrom(fieldType)) {
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType) {
                ParameterizedType type = (ParameterizedType) genericType;
                Type[] typeArgs = type.getActualTypeArguments();
                if (typeArgs.length > 0) {
                    Type typeArg = typeArgs[0];
                    if (typeArg instanceof Class) {
                        return (Class<?>) typeArg;
                    } else if (typeArg instanceof WildcardType) {
                        WildcardType wildcardType = (WildcardType) typeArg;
                        Type[] upperBounds = wildcardType.getUpperBounds();
                        Type[] lowerBounds = wildcardType.getLowerBounds();
                        if (upperBounds.length > 0) {
                            return upperBounds[0] == Object.class ? UnKnownType.class : (Class<?>) upperBounds[0];
                        } else if (lowerBounds.length > 0) {
                            return (Class<?>) lowerBounds[0];
                        } else {
                            return UnKnownType.class;
                        }
                    }
                }
            }
        } else {
            return UnKnownType.class;
        }
        return UnKnownType.class;
    }

    //判断是否为接口
    public static boolean isInterface(Class<?> type) {
        return type.isInterface();
    }

    //判断是否为抽象类
    public static boolean isAbstract(Class<?> type) {
        return Modifier.isAbstract(type.getModifiers());
    }

    //设置字段值
    public static void setField(Class<?> type, String fieldName, Object object, Object fieldVal) {
        Field field = getPrivateField(type, fieldName);
        if (field != null) {
            if (fieldVal != null) {
                Class<?> fieldType = field.getType();
                Class<?> fieldClass = fieldVal.getClass();
                if (fieldType.isAssignableFrom(fieldClass)) {
                    field.setAccessible(true);
                    try {
                        field.set(object, fieldVal);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

}
