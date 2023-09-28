package ru.zavoyko.framework.di.impl;

import lombok.SneakyThrows;
import ru.zavoyko.framework.di.BeanProcessor;
import ru.zavoyko.framework.di.anotations.InjectProperty;

import java.lang.reflect.Field;
import java.util.Map;

import static ru.zavoyko.framework.di.impl.ApplicationPropertiesLoader.loadProperties;
import static ru.zavoyko.framework.di.utils.DIFObjectUtils.getDeclaredFields;
import static ru.zavoyko.framework.di.utils.DIFObjectUtils.isBlank;

public class ApplicationPropertiesProcessor implements BeanProcessor {

    private final Map<String, String> props;

    public ApplicationPropertiesProcessor() {
        this.props = loadProperties();
    }
    @Override
    @SneakyThrows
    public void process(Object instance) {
        final Class<?> newInstanceClass = instance.getClass();
        final var declaredFields = getDeclaredFields(newInstanceClass);
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(InjectProperty.class)) {
                final var annotation = field.getAnnotation(InjectProperty.class);
                String propertyName;
                if (isBlank(annotation.value())) {
                    propertyName = field.getName();
                } else {
                    propertyName = annotation.value();
                }
                final var properties = ApplicationPropertiesLoader.loadProperties();
                final var value = properties.get(propertyName);

                field.setAccessible(true);
                field.set(instance, value);
            }
        }
    }

}
