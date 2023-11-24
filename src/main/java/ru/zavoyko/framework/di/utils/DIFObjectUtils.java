package ru.zavoyko.framework.di.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.exception.DIFException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
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

    public static <T> T instantiate(Class<T> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException e) {
            log.error("The class {} is not instantiable (it’s abstract or an interface)", clazz, e);
            throw new DIFException("Can't create object for class: " + clazz, e);
        } catch (IllegalAccessException e) {
            log.error("The constructor of class {} is not accessible", clazz, e);
            throw new DIFException("Can't create object for class: " + clazz, e);
        } catch (InvocationTargetException e) {
            log.error("The constructor of class {} threw an exception", clazz, e);
            throw new DIFException("Can't create object for class: " + clazz, e);
        } catch (NoSuchMethodException e) {
            log.error("The class {} does not have a no-argument constructor", clazz, e);
            throw new DIFException("Can't create object for class: " + clazz, e);
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

    public static Set<Method> getDeclaredMethods(Class<?> clazz) {
        final var methods = new HashSet<Method>();
        getDeclared(clazz, methods);
        return methods;
    }

    private static void getDeclared(Class<?> clazz, HashSet<Method> methods) {
        Collections.addAll(methods, clazz.getDeclaredMethods());
        if (clazz.getSuperclass() != Object.class && clazz.getSuperclass() != null) {
            getDeclared(clazz.getSuperclass(), methods);
        }
    }

}
