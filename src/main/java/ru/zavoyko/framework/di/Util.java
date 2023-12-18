package ru.zavoyko.framework.di;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Util {

    public static Set<Field> getFields(Object object) {
        Set<Field> fields = new HashSet<>();
        getFields(object.getClass(), fields);
        return  fields;
    }

    private static void getFields(Class objectClass, Set<Field> fields) {
        if (objectClass==Object.class) {
            return;
        }
        fields.addAll(Arrays.stream(objectClass.getDeclaredFields()).collect(Collectors.toSet()));
        getFields(objectClass.getSuperclass(), fields);
    }


    public static <T> T createInstance(Class<T> tClass) {
        try {
            return tClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
            throw new DIException(
                    String.format("Failed to create new instance of the %s class", tClass.getName()),
                    ex
            );
        }
    }

}
