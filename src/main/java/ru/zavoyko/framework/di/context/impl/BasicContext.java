package ru.zavoyko.framework.di.context.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.exceptions.DIFrameworkComponentBindException;
import ru.zavoyko.framework.di.factory.ComponentFactory;
import ru.zavoyko.framework.di.source.Definition;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BasicContext extends AbstractContext {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicContext.class);

    private final Map<Definition, Object> singletonMap;

    private ComponentFactory factory;

    public BasicContext() {
        this.singletonMap = new ConcurrentHashMap<>();

    }

    @Override
    public void setFactory(ComponentFactory componentFactory) {
        this.factory = componentFactory;
    }

    @Override
    public void initContext() {
        LOGGER.warn("Initializing context");
        LOGGER.warn("Preparing singletons");
        factory.getComponentsDefinitions().stream()
                .filter(Definition::isSingleton)
                .filter(definition -> !definition.isLazy())
                .forEach(definition -> {
                    LOGGER.warn("Initializing singleton {}", definition.getName());
                    if (singletonMap.containsKey(definition)) {
                        return;
                    }
                    final var component = factory.createComponent(definition);
                    singletonMap.put(definition, component);
                });
        LOGGER.warn("Singletons: {}", singletonMap);
        LOGGER.warn("Context initialized");
    }

    @Override
    public <T> T getComponent(final Class<T> type) {
        T component;

        try {
            final var checkDefinition = getAndCheckOneDefinitionOrThrowException(type);
            if (checkDefinition.isSingleton()) {
                if (singletonMap.containsKey(checkDefinition)) {
                    return type.cast(singletonMap.get(checkDefinition));
                } else {
                    component = type.cast(factory.createComponent(checkDefinition));
                    singletonMap.put(checkDefinition, component);
                }
            } else {
                component = type.cast(factory.createComponent(checkDefinition));
            }

        } catch (ClassCastException e) {
            throw new DIFrameworkComponentBindException("No implementation found for " + type.getCanonicalName(), e);
        }

        if (type.isAssignableFrom(component.getClass())) {
            return component;
        }
        throw new DIFrameworkComponentBindException("No implementation found for " + type.getCanonicalName());
    }

    @Override
    protected Set<Definition> getComponentsDefinitions() {
        return factory.getComponentsDefinitions();
    }

    public Object getComponent(String name) {
        final var checkDefinition = getOneDefinitionByAliasNameOrThrowException(name);
        if (checkDefinition.isSingleton()) {
            if (singletonMap.containsKey(checkDefinition)) {
                return singletonMap.get(checkDefinition);
            } else {
                final var component = factory.createComponent(checkDefinition);
                singletonMap.put(checkDefinition, component);
                return component;
            }
        }
        return factory.createComponent(checkDefinition);
    }

}
