package ru.zavoyko.framework.di.processors.component.impl.java;

import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.inject.InjectByType;

public class InjectByTypeComponentProcessorImpl extends JavaComponentProcessor {

    @Override
    public void process(Context context, Object component) {
        for (var field : component.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectByType.class)) {
                String type;
                final var annotation = field.getAnnotation(InjectByType.class);
                if (annotation.type().isEmpty()) {
                    type = field.getType().getName();
                } else {
                    type = annotation.type();
                }
                final var instance = context.getComponent(type);
                makeAccessible(field);
                setField(component, field, instance);
            }
        }
    }

}
