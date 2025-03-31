package com.room.tools;

import java.util.LinkedList;
import java.util.List;

/**
 * 扩展方法
 */
public class Extension {

    /**
     * 判断字符串是否为空或者空串
     */
    public static boolean isNotNullOrEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    /**
     * 判断字符串是否是空或者空串
     */
    public static boolean isNullOrEmpty(String str) {
        return !isNotNullOrEmpty(str);
    }
    /**
     * 判断Id是否存在
     */
    public static boolean isNullOrZero(Integer value) {
        return value==null||value==0;
    }

    /**
     * 拷贝一个列表到另外一个列表
     */
    public static <T> List<T> copyBeanList(List<?> resourceList, Class<T> target) {
        List<T> targetList = new LinkedList<>();
        if (resourceList.isEmpty()) {
            return targetList;
        }
        resourceList.forEach(e -> {
            T o = null;
            try {
                o = target.newInstance();
            } catch (InstantiationException | IllegalAccessException ex) {
                ex.printStackTrace();
            }
            org.springframework.beans.BeanUtils.copyProperties(e, o);
            targetList.add(o);
        });
        return targetList;
    }
}