package ru.zavoyko.framework.di.processors;

import ru.zavoyko.framework.di.processors.ComponentProcessor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractComponentProcessor implements ComponentProcessor {

        protected ArrayList<Field> getFields(Class<?> clazz) {
            return new ArrayList<>(List.of(clazz.getDeclaredFields()));
        }

}

