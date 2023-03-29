package ru.zavoyko.framework.di.processors.component.impl;

import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.factory.ObjectFactory;
import ru.zavoyko.framework.di.exceptions.DIFrameworkInstansiationException;
import ru.zavoyko.framework.di.inject.InjectByType;
import ru.zavoyko.framework.di.processors.component.ComponentProcessor;
import ru.zavoyko.framework.di.util.ReflectionUtils;

public class InjectByTypeComponentProcessor implements ComponentProcessor {


    @Override
    public void process(Context context, Object component) {
        ReflectionUtils.getFieldAnnotatedBy(component, InjectByType.class).stream()
                .forEach(field -> {
                    final var annotation = field.getAnnotation(InjectByType.class);
                    var annotationValue = annotation.value();
                    if (annotationValue == Object.class) {
                        annotationValue = field.getType();
                    }
                    final Object value = context.getComponent(annotationValue);
                    field.setAccessible(true);
                    try {
                        field.set(component, value);
                    } catch (IllegalAccessException e) {
                        throw new DIFrameworkInstansiationException("Can't set instance to field " + field.getName());
                    }
                });
    }

}
