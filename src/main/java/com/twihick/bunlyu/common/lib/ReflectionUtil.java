package com.twihick.bunlyu.common.lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

public final class ReflectionUtil {

    private static final Logger LOGGER = LogManager.getLogger(ReflectionUtil.class);

    public static <V> V getStaticFieldValue(Class<?> clazz, String fieldName) {
        Field field = null;
        V value = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            value = (V) field.get(null);
        }catch(NoSuchFieldException | IllegalAccessException e) {
            LOGGER.error(e);
        }
        return value;
    }

}
