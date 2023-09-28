package ru.zavoyko.framework.di.impl;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import ru.zavoyko.framework.di.BeanProcessor;
import ru.zavoyko.framework.di.Config;
import ru.zavoyko.framework.di.Context;
import ru.zavoyko.framework.di.exception.DIFException;
import ru.zavoyko.framework.di.utils.DIFObjectUtils;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static ru.zavoyko.framework.di.utils.DIFObjectUtils.checkNonNullOrThrowException;

@Slf4j
public class ConfigImpl implements Config {

    public static ConfigImpl getInstance(List<String> packagesToScan) {
        checkNonNullOrThrowException(packagesToScan, "Packages list is null or empty");
        return new ConfigImpl(packagesToScan);
    }

    private final List<Reflections> reflections;
    private Context context;

    private ConfigImpl(List<String> pkg) {
        this.reflections = pkg.stream().map(Reflections::new).toList();
    }

    private void setContext(Context contextToSet) {
        this.context = contextToSet;
    }

    @Override
    public <T> Class<? extends T> getImplClass(final Class<T> type) {
        checkNonNullOrThrowException(type, "Type can not be null");
        log.debug("Locking for the implementation of the type " + type);
        final List<Class<? extends T>> typesOf = reflections.stream()
                .map(ref -> ref.getSubTypesOf(type))
                .flatMap(Collection::stream)
                .toList();
        if (typesOf.size() != 1) {
            log.error(type + " has " + typesOf.size() + " implementations");
            throw new DIFException(type + " has 0 or more than 1 implementations", new IllegalArgumentException(type.getCanonicalName()));
        }
        return typesOf.iterator().next();
    }

    @Override
    public List<BeanProcessor> getBeanProcessors() {
        var subTypesOfBeanProcessor = reflections.stream()
                .map(ref -> ref.getSubTypesOf(BeanProcessor.class))
                .flatMap(Collection::stream)
                .toList();
        return subTypesOfBeanProcessor.stream()
                .map(DIFObjectUtils::instantiate)
                .collect(toList());
    }

}
