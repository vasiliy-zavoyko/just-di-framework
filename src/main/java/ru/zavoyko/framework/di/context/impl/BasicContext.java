package ru.zavoyko.framework.di.context.impl;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.factory.BasicComponentFactory;
import ru.zavoyko.framework.di.factory.ComponentFactory;
import ru.zavoyko.framework.di.processors.ComponentProcessor;
import ru.zavoyko.framework.di.source.Definition;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;

public class BasicContext extends AbstractContext {

    Logger logger = LoggerFactory.getLogger(BasicContext.class);

    private final Map<Class, Object> singltonsMap;
    private ComponentFactory factory;
    @Getter
    private final Map<String, Definition> definitions;
    @Getter
    private Set<ComponentProcessor> processors;
    @Getter
    private Set<Definition> componentsDefinitions;

    public BasicContext(Map<String, Definition> definitionsMap) {
        this.singltonsMap = new ConcurrentHashMap<>();
        this.definitions = unmodifiableMap(definitionsMap);
    }

    @Override
    public void setFactory(ComponentFactory factory) {
        this.factory = factory;
    }

    @Override
    public void initContext() {
        logger.warn("Initializing context");
        final var componentProcessors = new HashSet<ComponentProcessor>();
        definitions.values().stream()
                .filter(item -> !item.isComponent())
                .map(Definition::getType)
                .map(this::createProcessor)
                .forEach(componentProcessors::add);
        this.processors = unmodifiableSet(componentProcessors);
        logger.warn("Processors: {}", processors);

        final var componentDefinitions = new HashSet<Definition>();
        definitions.values().stream()
                .filter(Definition::isComponent)
                .forEach(componentDefinitions::add);
        this.componentsDefinitions = unmodifiableSet(componentDefinitions);
        logger.warn("Components: {}", componentsDefinitions);

        componentsDefinitions.stream()
                .filter(Definition::isSingleton)
                .filter(definition -> !definition.isLazy())
                .forEach(definition -> {
                    if (singltonsMap.containsKey(definition.getType())) {
                        return;
                    }
                    final var component = this.getComponent(definition.getType());
                    singltonsMap.put(definition.getType(), component);
                });
        logger.warn("Singletons: {}", singltonsMap);
        logger.warn("Context initialized");
    }

    @Override
    public <T> T getComponent(Class<T> type) {
        Class<? extends T> instance = type;
        if (type.isInterface()) {
            for(var singl : singltonsMap.values()) {
                if (type.isInstance(singl)) {
                    return (T) singl;
                }
            }
        }
        final var definition = getAndCheckDefinition(instance);
        if (definition.isSingleton()) {
            if (singltonsMap.containsKey(instance)) {
                return (T) singltonsMap.get(instance);
            } else {
                final var component = factory.createComponent(instance);
                singltonsMap.put(component.getClass(), component);
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
