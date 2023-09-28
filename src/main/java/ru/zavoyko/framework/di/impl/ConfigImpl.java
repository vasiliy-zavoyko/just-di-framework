package ru.zavoyko.framework.di.impl;

import org.reflections.Reflections;
import ru.zavoyko.framework.di.Config;
import ru.zavoyko.framework.di.exception.DIFException;

import static ru.zavoyko.framework.di.utils.DIFObjectUtils.checkNonNullOrThrowException;

public class ConfigImpl implements Config {

    private final Reflections reflections;

    public ConfigImpl(String pkg) {
        this.reflections = new Reflections(pkg);
    }

    @Override
    public <T> Class<? extends T> getImplClass(final Class<T> type) {
        checkNonNullOrThrowException(type, "Type can not be null");
        final var typesOf = reflections.getSubTypesOf(type);
        if (typesOf.size() != 1) {
            throw new DIFException(type + " has 0 or more than 1 implementations", new IllegalArgumentException(type.getCanonicalName()));
        }
        return typesOf.iterator().next();
    }

}
