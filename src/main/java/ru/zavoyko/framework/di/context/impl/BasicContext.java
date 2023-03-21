package ru.zavoyko.framework.di.context.impl;

import lombok.Getter;
import ru.zavoyko.framework.di.factory.BasicComponentFactory;
import ru.zavoyko.framework.di.factory.ComponentFactory;
import ru.zavoyko.framework.di.processors.ComponentProcessor;
import ru.zavoyko.framework.di.source.Definition;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;

public class BasicContext extends AbstractContext {

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
        final var componentProcessors = new HashSet<ComponentProcessor>();
        definitions.values().stream()
                .filter(item -> !item.isComponent())
                .map(Definition::getType)
                .map(this::createProcessor)
                .forEach(componentProcessors::add);
        this.processors = unmodifiableSet(componentProcessors);

        final var componentDefinitions = new HashSet<Definition>();
        definitions.values().stream()
                .filter(Definition::isComponent)
                .forEach(componentDefinitions::add);
        this.componentsDefinitions = unmodifiableSet(componentDefinitions);

        componentsDefinitions.stream()
                .filter(Definition::isSingleton)
                .filter(definition -> !definition.isLazy())
                .forEach(definition -> singltonsMap.put(definition.getType(), factory.createComponent(definition.getType())));
    }

    @Override
    public <T> T getComponent(Class<T> type) {
        Class<? extends T> instance = type;
        final var definition = getAndCheckDefinition(instance);
        if (definition.isSingleton()) {
            return (T) singltonsMap.computeIfAbsent(instance, key -> factory.createComponent(key));
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
