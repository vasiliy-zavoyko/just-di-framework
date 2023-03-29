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

    private final Map<Definition, Object> singletonMap;

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
                    logger.warn("Initializing singleton {}", definition.getName());
                    if (singletonMap.containsKey(definition)) {
                        return;
                    }
                    final var component = factory.createComponent(definition);
                    singletonMap.put(definition, component);
                });
        logger.warn("Singletons: {}", singletonMap);
        logger.warn("Context initialized");
    }

    @Override
    public <T> T getComponent(final Class<T> type) {

        T component = null;

        final var checkDefinition = getAndCheckOneDefinitionOrThrowException(type);
        if (checkDefinition.isSingleton()) {
            if (singletonMap.containsKey(checkDefinition)) {
                return (T) singletonMap.get(checkDefinition);
            } else {
                component = (T) factory.createComponent(checkDefinition);
                singletonMap.put(checkDefinition, component);
            }
        } else {
            component = (T) factory.createComponent(checkDefinition);
        }

        if (type.isAssignableFrom(component.getClass())) {
            return component;
        }
        throw new RuntimeException("No implementation found for " + type.getCanonicalName());
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
