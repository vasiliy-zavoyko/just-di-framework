package ru.zavoyko.framework.di.impl;

import org.reflections.Reflections;
import ru.zavoyko.framework.di.Config;
import ru.zavoyko.framework.di.exception.DIFException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static ru.zavoyko.framework.di.utils.DIFObjectUtils.checkNonNullOrThrowException;

public class ConfigImpl implements Config {

    private final Reflections reflections;
    private final Map<Class, Class> classClassMap;

    public ConfigImpl(String pkg, Map<Class, Class> classClassMapToSet) {
        this.reflections = new Reflections(pkg);
        this.classClassMap = new HashMap<>(classClassMapToSet);
    }

    @Override
    public <T> Class<? extends T> getImplClass(final Class<T> type) {
        checkNonNullOrThrowException(type, "Type can not be null");
        return classClassMap.computeIfAbsent(type, aClass -> {
            final Set<Class<? extends T>> typesOf = reflections.getSubTypesOf(type);
            if (typesOf.size() != 1) {
                throw new DIFException(type + " has 0 or more than 1 implementations", new IllegalArgumentException(type.getCanonicalName()));
            }
            return typesOf.iterator().next();
        });
    }

}
