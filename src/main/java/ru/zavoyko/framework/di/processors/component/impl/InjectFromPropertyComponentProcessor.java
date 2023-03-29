package ru.zavoyko.framework.di.processors.component.impl;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.factory.ObjectFactory;
import ru.zavoyko.framework.di.exceptions.DIFrameworkInstansiationException;
import ru.zavoyko.framework.di.popertis.InjectFromProperty;
import ru.zavoyko.framework.di.processors.component.ComponentProcessor;
import ru.zavoyko.framework.di.util.ReflectionUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.io.Resources.getResource;

public class InjectFromPropertyComponentProcessor implements ComponentProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(InjectFromPropertyComponentProcessor.class);
    private final Map<String, String> propertyMap;
    public InjectFromPropertyComponentProcessor() {
        LOGGER.debug("InjectFromPropertyComponentProcessor object created, class: {}", this.getClass().getName());
        final var resource = getResource("application.properties");
        try {
            this.propertyMap = new BufferedReader(new InputStreamReader(resource.openStream()))
                    .lines()
                    .map(String::trim)
                    .map(line -> line.split("="))
                    .collect(Collectors.toMap(line -> line[0].trim(), line -> line[1].trim()));

        } catch (IOException e) {
            throw new DIFrameworkInstansiationException("Can't read properties file");
        }
    }

    @SneakyThrows
    @Override
    public void process(Context context, Object component) {
        ReflectionUtils.getFieldAnnotatedBy(component, InjectFromProperty.class)
                .forEach(field -> {
                    final var annotation = field.getAnnotation(InjectFromProperty.class);
                    final var annotationValue = annotation.value();
                    String value = null;
                    if (annotationValue.isEmpty()) {
                        value = field.getName();
                    } else {
                        value = annotationValue;
                    }
                    final var property = propertyMap.get(value);
                    field.setAccessible(true);
                    try {
                        field.set(component, property);
                    } catch (IllegalAccessException e) {
                        throw new DIFrameworkInstansiationException("Can't set value to field " + field.getName());
                    }
                });
    }

}
