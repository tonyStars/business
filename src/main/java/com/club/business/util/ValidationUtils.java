package com.club.business.util;

import com.club.business.util.exception.BusinessException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 判断javaBean中必填属性是否为空
 *
 * @author Tom
 * @date 2019-12-12
 */
public class ValidationUtils {

    private final static String PIX_GET = "get";

    /**
     * @Description 只校验checkedFieldNames中传入的字段，判断对象中对应的字段值非空
     *
     * @param object 待检测对象
     * @param checkedFieldNames 被校验变量属性
     * @throws Exception 如果被校验字段的值为空，抛出此异常
     */
    public static void checkNotEmpty(Object object, Map<String,String> checkedFieldNames) throws Exception {
        if (null == object) {
            throw new BusinessException("参数为空！");
        }
        List<String> fieldNames = new ArrayList<>();
        Set<String> set = checkedFieldNames.keySet(); //取出所有的key值
        for (String key:set) {
            fieldNames.add(key);
        }
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        String fieldName = "";
        StringBuilder methodName = null;
        Method method = null;
        for (Field field : fields) {
            fieldName = field.getName();
            if (!hasElement(fieldName, fieldNames)) {
                continue;
            }
            methodName = new StringBuilder(PIX_GET);
            methodName = methodName.append(fieldName.substring(0, 1).toUpperCase())
                    .append(fieldName.substring(1));
            method = clazz.getDeclaredMethod(methodName.toString());
            Object result = method.invoke(object);
            if (null == result || "".equals(result)) {
                throw new BusinessException("参数 【".concat(checkedFieldNames.get(fieldName)).concat("】 为空！"));
            }
        }
    }

    /**
     *  检测container数组是否包含element元素
     *
     * @return boolean，true 包含
     */
    private static boolean hasElement(String element, List<String> containers) {
        if (containers.contains(element)) {
            return true;
        }
        return false;
    }

}
