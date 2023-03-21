package ru.zavoyko.framework.di.processors.impl.java;

import ru.zavoyko.framework.di.processors.impl.AbstractComponentProcessor;

import java.lang.reflect.Field;

public abstract class JavaComponentProcessor extends AbstractComponentProcessor {

    protected void makeAccessible(Field field) {
        field.setAccessible(true);
    }

    protected void setField(Object component, Field field, Object fieldInstance) {
        try {
            field.set(component, fieldInstance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
