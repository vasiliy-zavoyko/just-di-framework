package ru.zavoyko.framework.di.impl;

import lombok.SneakyThrows;
import ru.zavoyko.framework.di.Context;
import ru.zavoyko.framework.di.Processor;
import ru.zavoyko.framework.di.Value;

import java.lang.reflect.Field;
import java.util.Map;

public class PropertyProcessor implements Processor {

    private final Map<String , String> stringMap = new PropertyLoader().load();


    @Override
    @SneakyThrows
    public void process(Context context, Object object) {
        final Class<?> aClass = object.getClass();
        final var declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(Value.class)) {
                final var annotation = declaredField.getAnnotation(Value.class);
                final var key = annotation.name().isEmpty() ? declaredField.getName() : annotation.name();
                final var value = stringMap.get(key);
                declaredField.setAccessible(true);
                declaredField.set(object, value);
            }
        }
    }

}
