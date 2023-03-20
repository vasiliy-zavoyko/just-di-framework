package ru.zavoyko.framework.di.processors;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

abstract class AbstractComponentProcessor implements ComponentProcessor {

        protected ArrayList<Field> getFields(Class<?> clazz) {
            return new ArrayList<>(List.of(clazz.getDeclaredFields()));
        }

}

