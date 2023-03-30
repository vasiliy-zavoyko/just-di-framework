package ru.zavoyko.framework.di.utils;

import lombok.NoArgsConstructor;
import ru.zavoyko.framework.di.exceptions.DIFrameworkInstantiationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ReflectionUtils {

    public static <T> T createInstance(final Class<T> clazz)  {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new DIFrameworkInstantiationException("Failed to create instance of " + clazz, e);
        }
    }

    public static List<Method> getMethods(final Class<?> clazz) {
        return List.of(clazz.getDeclaredMethods());
    }

    public static Object invokeMethod(final Method method, final Object instance, final Object[] args) {
        try {
            return method.invoke(instance, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new DIFrameworkInstantiationException("Failed to invoke method " + method + " on " + instance, e);
        }
    }

    public static Set<Method> getAllMethodsByAnnotation(final Class<?> clazz, final Class<? extends Annotation> annotation) {
        final var methods = new HashSet<Method>();
        for (Method method : getMethods(clazz)) {
            if (method.isAnnotationPresent(annotation)) {
                methods.add(method);
            }
        }
        return methods;
    }

}
