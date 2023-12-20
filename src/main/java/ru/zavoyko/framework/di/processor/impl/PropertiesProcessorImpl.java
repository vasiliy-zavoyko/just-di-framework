package ru.zavoyko.framework.di.processor.impl;

import lombok.SneakyThrows;
import ru.zavoyko.framework.di.annotations.Value;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.exception.DIException;
import ru.zavoyko.framework.di.processor.Processor;

public class PropertiesProcessorImpl implements Processor {

    @Override
    @SneakyThrows
    public void process(final Object object, Context context) {
        for (final var field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Value.class) ) {
                var value = field.getAnnotation(Value.class).keyToFind();
                if (value.isEmpty()) {
                    value = field.getName();
                }

                if (context.getProperty(value) != null) {
                    final var valueToSet = context.getProperty(value);
                    field.setAccessible(true);
                    field.set(object, valueToSet);
                } else {
                    throw new DIException(String.format("No key found, key = %s",value));
                }
            }
        }
    }

}
