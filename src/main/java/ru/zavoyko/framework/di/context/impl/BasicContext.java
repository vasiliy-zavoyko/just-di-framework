package ru.zavoyko.framework.di.context.impl;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.actions.ActionsProcessor;
import ru.zavoyko.framework.di.factory.ComponentFactory;
import ru.zavoyko.framework.di.processors.ComponentProcessor;
import ru.zavoyko.framework.di.source.Definition;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;

public class BasicContext extends AbstractContext {

    private static final Logger logger = LoggerFactory.getLogger(BasicContext.class);

    private final Map<Class, Object> singletonMap;
    private ComponentFactory factory;

    public BasicContext() {
        this.singletonMap = new ConcurrentHashMap<>();
    }

    @Override
    public void setFactory(ComponentFactory factory) {
        this.factory = factory;
    }

    @Override
    public void initContext() {
        logger.warn("Initializing context");
        logger.warn("Preparing singletons");
        factory.getComponentsDefinitions().stream()
                .filter(Definition::isSingleton)
                .filter(definition -> !definition.isLazy())
                .forEach(definition -> {
                    if (singletonMap.containsKey(definition.getType())) {
                        return;
                    }
                    final var component = this.getComponent(definition.getType());
                    singletonMap.put(definition.getType(), component);
                });
        logger.warn("Singletons: {}", singletonMap);
        logger.warn("Context initialized");
    }

    @Override
    public <T> T getComponent(Class<T> type) {
        Class<? extends T> instance = type;
        if (type.isInterface()) {
            for(var singleton : singletonMap.values()) {
                if (type.isInstance(singleton)) {
                    return (T) singleton;
                }
            }
        }
        final var definition = factory.getAndCheckDefinition(instance);
        if (definition.isSingleton()) {
            if (singletonMap.containsKey(instance)) {
                return (T) singletonMap.get(instance);
            } else {
                final var component = factory.createComponent(instance);
                singletonMap.put(definition.getType(), component);
                return component;
            }
        }
        return factory.createComponent(instance);
    }

}
