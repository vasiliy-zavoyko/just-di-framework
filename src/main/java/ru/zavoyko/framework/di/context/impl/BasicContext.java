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
    @Getter
    private final Map<String, Definition> definitions;
    @Getter
    private Set<ComponentProcessor> componentProcessors;
    @Getter
    private Set<ActionsProcessor> actionsProcessors;
    @Getter
    private Set<Definition> componentsDefinitions;

    public BasicContext(Map<String, Definition> definitionsMap) {
        this.singletonMap = new ConcurrentHashMap<>();
        this.definitions = unmodifiableMap(definitionsMap);
    }

    @Override
    public void setFactory(ComponentFactory factory) {
        this.factory = factory;
    }

    @Override
    public void initContext() {
        logger.warn("Initializing context");
        logger.warn("Preparing component processors");
        final var componentProcessors = new HashSet<ComponentProcessor>();
        definitions.values().stream()
                .filter(item -> !item.isComponent())
                .map(Definition::getType)
                .filter(ComponentProcessor.class::isAssignableFrom)
                .map(this::createComponentProcessor)
                .forEach(componentProcessors::add);
        this.componentProcessors = unmodifiableSet(componentProcessors);
        logger.warn("Component processors: {}", componentProcessors);

        logger.warn("Preparing actions processors");
        final var actionsProcessors = new HashSet<ActionsProcessor>();
        definitions.values().stream()
                .filter(item -> !item.isComponent())
                .map(Definition::getType)
                .filter(ActionsProcessor.class::isAssignableFrom)
                .map(this::createActionProcessor)
                .forEach(actionsProcessors::add);
        this.actionsProcessors = unmodifiableSet(actionsProcessors);
        logger.warn("Actions processors: {}", actionsProcessors);

        logger.warn("Preparing components");
        final var componentDefinitions = new HashSet<Definition>();
        definitions.values().stream()
                .filter(Definition::isComponent)
                .forEach(componentDefinitions::add);
        this.componentsDefinitions = unmodifiableSet(componentDefinitions);
        logger.warn("Components: {}", componentsDefinitions);

        logger.warn("Preparing singletons");
        componentsDefinitions.stream()
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
        final var definition = getAndCheckDefinition(instance);
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

    @Override
    protected Map<String, Definition> definitions() {
        return definitions;
    }

    @Override
    protected Set<Definition> componentsDefinitions() {
        return componentsDefinitions;
    }

}
