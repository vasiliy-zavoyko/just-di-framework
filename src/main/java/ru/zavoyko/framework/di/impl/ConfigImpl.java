package ru.zavoyko.framework.di.impl;

import org.reflections.Reflections;
import ru.zavoyko.framework.di.ActionProcessor;
import ru.zavoyko.framework.di.BeanProcessor;
import ru.zavoyko.framework.di.Config;
import ru.zavoyko.framework.di.exception.DIFException;
import ru.zavoyko.framework.di.utils.DIFObjectUtils;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static ru.zavoyko.framework.di.utils.DIFObjectUtils.checkNonNullOrThrowException;

public class ConfigImpl implements Config {

    public static ConfigImpl config(String pkg) {
        return new ConfigImpl(pkg);
    }

    private final Reflections reflections;

    public ConfigImpl(String pkg) {
        this.reflections = new Reflections(pkg);
    }

    @Override
    public <T> Class<? extends T> getImplClass(final Class<T> type) {
        checkNonNullOrThrowException(type, "Type can not be null");
        final Set<Class<? extends T>> typesOf = reflections.getSubTypesOf(type);
        if (typesOf.size() != 1) {
            throw new DIFException(type + " has 0 or more than 1 implementations", new IllegalArgumentException(type.getCanonicalName()));
        }
        return typesOf.iterator().next();
    }

    @Override
    public List<BeanProcessor> getBeanProcessors() {
        var subTypesOfBeanProcessor = reflections.getSubTypesOf(BeanProcessor.class);
        return subTypesOfBeanProcessor.stream()
                .map(DIFObjectUtils::instantiate)
                .collect(toList());
    }

    @Override
    public List<ActionProcessor> getActionProcessors() {
        var subTypesOfBeanProcessor = reflections.getSubTypesOf(ActionProcessor.class);
        return subTypesOfBeanProcessor.stream()
                .map(DIFObjectUtils::instantiate)
                .collect(toList());
    }

}
