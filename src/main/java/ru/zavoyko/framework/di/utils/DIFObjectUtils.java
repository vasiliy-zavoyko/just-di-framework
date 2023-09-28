package ru.zavoyko.framework.di.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.zavoyko.framework.di.exception.DIFException;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DIFObjectUtils {

    public static <T> T checkNonNullOrThrowException(T t) {
        return checkNonNullOrThrowException(t, "Object is null");
    }

    public static <T> T checkNonNullOrThrowException(T t, String msg) {
        if (t == null) {
            throw new DIFException(msg, new NullPointerException());
        }
        return t;
    }

    public static boolean isBlank(String string) {
        if (string == null || string.isEmpty() || string.trim().isEmpty()) {
            return true;
        }
        return false;
    }

    public static <T> T castToType(Object obj, Class<T> clazz) {
        try {
            return clazz.cast(obj);
        } catch (ClassCastException classCastException) {
            throw new DIFException("Can not cast " + obj.getClass() + " to " + clazz);
        }
    }

    public static Set<Field> getDeclaredFields(Class<?> object) {
        final Set<Field> fields = new HashSet<>();
        getDeclaredFields(object, fields);
        return fields;
    }

    private static void getDeclaredFields(final Class<?> clazz, final Set<Field> fields) {
        Collections.addAll(fields, clazz.getDeclaredFields());
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null && superclass != Object.class) {
            getDeclaredFields(superclass, fields);
        }
    }

}
