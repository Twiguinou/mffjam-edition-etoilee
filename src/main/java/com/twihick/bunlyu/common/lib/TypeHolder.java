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
        try {
            Field field = this.getClass().getField("value");
            field.setAccessible(true);
            if(!Modifier.isFinal(field.getModifiers()))
                this.value = value;
            field.setAccessible(false);
        }catch(NoSuchFieldException e) {
            LOGGER.error(e);
        }
    }

    public void lock() {
        try {
            Field field = this.getClass().getField("value");
            field.setAccessible(true);
            Field modifiers = Field.class.getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, Modifier.FINAL);
            field.setAccessible(false);
            modifiers.setAccessible(false);
        }catch(NoSuchFieldException | IllegalAccessException e) {
            LOGGER.error(e);
        }
    }

    public void lockAndSet(@Nullable V value) {
        this.set(value);
        this.lock();
    }

}
