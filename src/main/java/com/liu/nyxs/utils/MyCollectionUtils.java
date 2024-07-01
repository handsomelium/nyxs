package com.liu.nyxs.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author ：haoshen
 * @date ：2023-05-05
 * @description：
 * @version: $
 */
@SuppressWarnings({"rawTypes", "unchecked"})
@Slf4j
public class MyCollectionUtils {

    //判断数组是否为空
    public static Boolean isEmpty(Object[] array) {
        return Objects.isNull(array) || array.length == 0 || Objects.isNull(array[0]);
    }

    public static Boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    //String转List
    public static List string2List(String str, Character delimiter, ConvertType targetType) {
        if (Objects.isNull(str) || Objects.isNull(delimiter)) {
            log.debug("str and delimiter can not be null !");
            return null;
        }
        if (StringUtils.isBlank(str)) {
            return Collections.emptyList();
        }
        List res = new ArrayList<>(str.length() >> 2 + 1);
        int pre = 0;
        for (int i = 1; i <= str.length(); i++) {
            if (i == str.length() || str.charAt(i) == delimiter) {
                String s = str.substring(pre, i);
                s = s.trim();
                pre = i + 1;
                try {
                    if (ConvertType.Long.equals(targetType)) {
                        res.add(Long.valueOf(s));
                    } else if (ConvertType.Integer.equals(targetType)) {
                        res.add(Integer.valueOf(s));
                    } else if (ConvertType.String.equals(targetType)) {
                        res.add(s);
                    }
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return res;
    }

    public static <T> List<T> string2List(String str, Character delimiter, Class<T> targetType) {
        if (Objects.isNull(str) || Objects.isNull(delimiter)) {
            log.debug("str ann delimiter can not be null!");
            return null;
        }
        if (StringUtils.isBlank(str)) {
            return Collections.emptyList();
        }
        if (!ConvertType.contains(targetType)) {
            throw new RuntimeException("targetType only can be Integer or Long or String!");
        }
        List<T> res = new ArrayList<>(str.length() >> 2 + 1);
        int pre = 0;
        for (int i = 1; i <= str.length(); i++) {
            if (i == str.length() || str.charAt(i) == delimiter) {
                String s = str.substring(pre, i);
                s = s.trim();
                pre = i + 1;
                try {
                    if (Long.class.isAssignableFrom(targetType)) {
                        res.add((T) Long.valueOf(s));
                    } else if (Integer.class.isAssignableFrom(targetType)) {
                        res.add((T) Integer.valueOf(s));
                    } else if (String.class.isAssignableFrom(targetType)) {
                        res.add((T) s);
                    }
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return res;
    }

    //将List转化成以某个字段为key的Map
    public static <T> Map<String, T> asMap(Class<T> clazz, List<T> src, String fieldKey) {
        if (CollectionUtils.isEmpty(src)) {
            return Collections.emptyMap();
        }
        Field field = MyReflectUtils.getPrivateField(clazz, fieldKey);
        if (Objects.isNull(fieldKey)) {
            throw new RuntimeException("key不存在！");
        }
        Map<String, T> res = new HashMap<>();
        src.forEach(item -> {
            try {
                String value = field.get(item).toString();
                res.put(value, item);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return res;
    }
    public static <T> List<T> createList(Class<T> type, Integer size, CreateStrategy strategy) {
        if (Objects.isNull(type)) {
            throw new RuntimeException("type can not be null");
        }
        size = Objects.isNull(size) || size < 1 ? 1 : size;
        List<T> res = new ArrayList<>(size);
        if (CreateStrategy.FILL_ELEMENT.equals(strategy)) {
            while (size-- > 0) {
                res.add(MyReflectUtils.createObject(type));
            }
        }
        return res;
    }
    @Getter
    @AllArgsConstructor
    public enum ConvertType {
        Long(java.lang.Long.class),
        Integer(java.lang.Integer.class),
        String(java.lang.String.class);

        private final Class<?> clazz;

        public static boolean contains(Class<?> type) {
            for (ConvertType convertType : ConvertType.values()) {
                if (convertType.clazz.equals(type)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum CreateStrategy {
        FILL_ELEMENT("填充对象"),
        SINGLETON("无元素");

        private final String remark;
    }

    //Map转List
    public static <T> List<T> map2List(Map<String, T> map) {
        return new ArrayList<>(map.values());
    }


    //检查数组是否为空
    public static boolean isArrayEmpty(Object[] array) {
        return Objects.isNull(array) || array.length == 0 || Objects.isNull(array[0]);
    }

    public static boolean isArrayNotEmpty(Object[] array) {
        return !isArrayEmpty(array);
    }


    /**
     * 去重
     */
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
