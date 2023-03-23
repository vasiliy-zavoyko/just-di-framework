package ru.zavoyko.framework.di.processors.impl;

import lombok.SneakyThrows;
import ru.zavoyko.framework.di.context.impl.BasicContext;
import ru.zavoyko.framework.di.inject.InjectProperty;
import ru.zavoyko.framework.di.processors.impl.java.JavaComponentProcessor;
import ru.zavoyko.framework.di.properties.PropertiesLoader;

import java.lang.reflect.Field;
import java.util.Map;

public class InjectPropertyComponentProcessorImpl extends JavaComponentProcessor {

    private Map<String, String> properties;

    @SneakyThrows
    public InjectPropertyComponentProcessorImpl() {
        properties = new PropertiesLoader().loadProperties("application.properties");
    }

    @Override
    @SneakyThrows
    public void process(BasicContext context, Object component) {
        final var implClass = component.getClass();
        for (Field field : getFields(implClass)) {
            if (field.isAnnotationPresent(InjectProperty.class)) {
                final var annotation = field.getAnnotation(InjectProperty.class);
                final var value = (annotation.value().isEmpty()) ?
                        properties.get(field.getName()) : properties.get(annotation.value());
                field.setAccessible(true);
                field.set(component, value);
            }
        }
    }


}
