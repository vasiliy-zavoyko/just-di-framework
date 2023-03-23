package ru.zavoyko.framework.di.processors.impl.java;

import ru.zavoyko.framework.di.context.impl.BasicContext;
import ru.zavoyko.framework.di.inject.InjectByType;

public class InjectByTypeComponentProcessorImpl extends JavaComponentProcessor {

    @Override
    public void process(BasicContext context, Object component) {
        for (var field : component.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectByType.class)) {
                var fieldClass = field.getType();
                final var instance = context.getComponent(fieldClass);
                makeAccessible(field);
                setField(component, field, instance);
            }
        }
    }

}
