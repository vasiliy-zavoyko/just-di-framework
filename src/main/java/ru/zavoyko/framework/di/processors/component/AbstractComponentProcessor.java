package ru.zavoyko.framework.di.processors.component;

import ru.zavoyko.framework.di.processors.ComponentProcessor;

import java.lang.reflect.Field;
import java.util.List;

public abstract class AbstractComponentProcessor implements ComponentProcessor {

        protected List<Field> getFields(final Class<?> clazz, final List<Field> fields) {
            if (clazz.getSuperclass() == null) {
                return fields;
            }
            fields.addAll(List.of(clazz.getDeclaredFields()));
            return getFields(clazz.getSuperclass(), fields);
        }

}

