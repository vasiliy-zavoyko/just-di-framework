package ru.zavoyko.framework.di.util;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.zavoyko.framework.di.exceptions.DIFrameworkInstansiationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReflectionUtils {

    public static Set<Field> getFieldAnnotatedBy(Object object, Class<? extends Annotation> annotation) {
        return getAllFields(object.getClass() , new HashSet<Field>()).stream()
                .filter(field -> field.isAnnotationPresent(annotation))
                .collect(HashSet::new, HashSet::add, HashSet::addAll);
    }

    public static Set<Method> getMethodAnnotatedBy(Object object, Class<? extends Annotation> annotation) {
        return getAllMethods(object.getClass() , new HashSet<Method>()).stream()
                .filter(method -> method.isAnnotationPresent(annotation))
                .collect(HashSet::new, HashSet::add, HashSet::addAll);
    }

    public static Set<Method> getAllMethods(Class<?> clazz, Set<Method> methodHashSet) {
        try {
            methodHashSet.addAll(Arrays.asList(clazz.getDeclaredMethods()));
            if (clazz.getSuperclass() == Object.class) {
                return methodHashSet;
            }
            return getAllMethods(clazz.getSuperclass(), methodHashSet);
        } catch (SecurityException e) {
            throw new DIFrameworkInstansiationException("Can't get methods from class " + clazz.getName());
        }
    }

    public static Set<Field> getAllFields(Class<?> clazz, Set<Field> fieldHashSet) {
        try {
            fieldHashSet.addAll(Arrays.asList(clazz.getDeclaredFields()));
            if (clazz.getSuperclass() == Object.class) {
                return fieldHashSet;
            }
            return getAllFields(clazz.getSuperclass(), fieldHashSet);
        } catch (SecurityException e) {
            throw new DIFrameworkInstansiationException("Can't get fields from class " + clazz.getName());
        }
    }

    public static <T> T createObject(Class<T> type) {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}