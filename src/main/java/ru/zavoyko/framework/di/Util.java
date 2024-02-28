package ru.zavoyko.framework.di;

import lombok.NoArgsConstructor;
import ru.zavoyko.framework.di.exception.DIException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.io.Resources.getResource;
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

    public static Map<String, String> getProperties(String fileName) {
        try {
            return new BufferedReader(new InputStreamReader(getResource(fileName).openStream()))
                    .lines()
                    .map(String::trim)
                    .map(str -> str.split("="))
                    .collect(Collectors.toMap(pair -> pair[0].trim(), pair -> pair[1].trim()));
        } catch (IOException e) {
            throw new DIException("Property file not Found or Accessible:", e);
        }
    }

}
