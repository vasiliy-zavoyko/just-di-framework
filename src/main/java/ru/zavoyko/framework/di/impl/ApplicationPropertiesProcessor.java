package ru.zavoyko.framework.di.impl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.BeanProcessor;
import ru.zavoyko.framework.di.Context;
import ru.zavoyko.framework.di.anotations.InjectProperty;

import java.lang.reflect.Field;
import java.util.Map;

import static ru.zavoyko.framework.di.impl.ApplicationPropertiesLoader.loadProperties;
import static ru.zavoyko.framework.di.utils.DIFObjectUtils.getDeclaredFields;
import static ru.zavoyko.framework.di.utils.DIFObjectUtils.isBlank;
import static ru.zavoyko.framework.di.utils.DIFObjectUtils.setField;

@Slf4j
public class ApplicationPropertiesProcessor implements BeanProcessor {

    private final Map<String, String> props;

    public ApplicationPropertiesProcessor() {
        this.props = loadProperties();
    }
    @Override
    @SneakyThrows
    public void process(Context context, Object bean) {
        final Class<?> newInstanceClass = bean.getClass();
        log.info("Processing bean of class: {}", newInstanceClass.getName());
        final var declaredFields = getDeclaredFields(newInstanceClass);
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(InjectProperty.class)) {
                final var annotation = field.getAnnotation(InjectProperty.class);
                String propertyName;
                if (isBlank(annotation.name())) {
                    propertyName = field.getName();
                } else {
                    propertyName = annotation.name();
                }
                final var properties = ApplicationPropertiesLoader.loadProperties();
                final var value = properties.get(propertyName);

                log.debug("Injecting property: {} with value: {} to field: {} of bean: {}", propertyName, value, field.getName(), newInstanceClass.getName());
                try {
                    setField(field, bean, value);
                    log.debug("Property: {} injected successfully", propertyName);
                } catch (Exception e) {
                    log.error("Error injecting property: {} to field: {} of bean: {}", propertyName, field.getName(), newInstanceClass.getName(), e);
                }
            }
        }
        log.info("Processing completed for bean of class: {}", newInstanceClass.getName());
    }

}
