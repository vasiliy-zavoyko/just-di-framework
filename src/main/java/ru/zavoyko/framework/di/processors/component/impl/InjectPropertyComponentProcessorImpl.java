package ru.zavoyko.framework.di.processors.component.impl;

import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.exceptions.DIFrameworkComponentBindException;
import ru.zavoyko.framework.di.inject.InjectProperty;
import ru.zavoyko.framework.di.processors.component.impl.java.JavaComponentProcessor;
import ru.zavoyko.framework.di.properties.PropertiesLoader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

public class InjectPropertyComponentProcessorImpl extends JavaComponentProcessor {

    private Map<String, String> properties;

    public InjectPropertyComponentProcessorImpl() {
        properties = new PropertiesLoader().loadProperties("application.properties");
    }

    @Override
    public void process(Context context, Object component)  {
        try {
            final var implClass = component.getClass();
            final var fields = getFields(implClass, new ArrayList<>());
            for (Field field : fields) {
                if (field.isAnnotationPresent(InjectProperty.class)) {
                    final var annotation = field.getAnnotation(InjectProperty.class);
                    final var value = (annotation.value().isEmpty()) ?
                            properties.get(field.getName()) : properties.get(annotation.value());
                    field.setAccessible(true);
                    field.set(component, value);
                }
            }
        } catch (IllegalAccessException e) {
            throw new DIFrameworkComponentBindException(e);
        }
    }

}
