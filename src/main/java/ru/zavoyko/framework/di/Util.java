package ru.zavoyko.framework.di;

import lombok.NoArgsConstructor;
import ru.zavoyko.framework.di.exception.DIException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;


@NoArgsConstructor(access = PRIVATE)
public class Util {

    public static Set<Field> getFields(Object object) {
        final var fields = new HashSet<Field>();
        Class<?> objectClass = object.getClass();

        while (objectClass != null && objectClass != Object.class) {
            Collections.addAll(fields, objectClass.getDeclaredFields());
            objectClass = objectClass.getSuperclass();
        }

        return fields;
    }

    public static Set<Method> getMethods(Object object) {
        final var methods = new HashSet<Method>();
        Class<?> objectClass = object.getClass();

        while (objectClass != null && objectClass != Object.class) {
            Collections.addAll(methods, objectClass.getMethods());
            objectClass = objectClass.getSuperclass();
        }

        return methods;
    }

    public static <T> T createInstance(Class<T> tClass) {
        try {
            return tClass.getDeclaredConstructor().newInstance();
        } catch (
                InstantiationException
                | IllegalAccessException
                | InvocationTargetException
                | NoSuchMethodException ex
        ) {
            throw new DIException(String.format("Failed to create new instance of the %s class", tClass.getName()), ex);
        }
    }

    public static <T> T castAndReturn(Class<T> clazz, Object object) {
        return clazz.cast(object);
    }

}
