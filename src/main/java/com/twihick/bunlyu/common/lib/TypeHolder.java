package com.twihick.bunlyu.common.lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class TypeHolder<V> {

    private static final Logger LOGGER = LogManager.getLogger(TypeHolder.class);

    @Nullable
    private V value;

    public TypeHolder(@Nullable V value) {
        this.value = value;
    }

    public TypeHolder() {
        this(null);
    }

    @Nullable
    public V get() {
        return this.value;
    }

    public void set(@Nullable V value) {
        this.value = value;
    }

    public void lock() {
        try {
            Field field = this.getClass().getDeclaredField("value");
            field.setAccessible(true);
            Field modifiers = Field.class.getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, Modifier.FINAL);
        }catch(NoSuchFieldException | IllegalAccessException e) {
            LOGGER.error(e);
        }
    }

    public void lockAndSet(@Nullable V value) {
        this.set(value);
        this.lock();
    }

}
