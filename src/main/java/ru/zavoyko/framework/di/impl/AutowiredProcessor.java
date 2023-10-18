package ru.zavoyko.framework.di.impl;

import lombok.SneakyThrows;
import ru.zavoyko.framework.di.Autowired;
import ru.zavoyko.framework.di.Context;
import ru.zavoyko.framework.di.Processor;

import java.lang.reflect.Field;

public class AutowiredProcessor implements Processor {

    @Override
    @SneakyThrows
    public void process(Context context, Object instance) {
        final Class<?> instanceClass = instance.getClass();
        final var declaredFields = instanceClass.getDeclaredFields();

        for (var field : declaredFields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                final var annotation = field.getAnnotation(Autowired.class);
                final var aClass = annotation.value() == Object.class ? field.getType() : annotation.value();
                final var bean = context.getBean(aClass);
                field.setAccessible(true);
                field.set(instance,bean );
            }
        }

    }

}
