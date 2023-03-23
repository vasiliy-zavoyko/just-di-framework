package ru.zavoyko.framework.di.factory;

import lombok.SneakyThrows;
import ru.zavoyko.framework.di.actions.ActionsProcessor;
import ru.zavoyko.framework.di.context.impl.BasicContext;
import ru.zavoyko.framework.di.exceptions.ComponentBindException;
import ru.zavoyko.framework.di.processors.ComponentProcessor;
import ru.zavoyko.framework.di.source.ComponentSource;
import ru.zavoyko.framework.di.source.Definition;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class BasicComponentFactory implements ComponentFactory {

    private BasicContext context;
    private Map<String, ComponentSource> componentSourceMap;

    public BasicComponentFactory(BasicContext context, Map<String, ComponentSource> componentSourceMap) {
        this.context = context;
        this.componentSourceMap = Collections.unmodifiableMap(componentSourceMap);
    }

    @Override
    @SneakyThrows
    public <T> T createComponent(Class<T> componentClass) {
        Definition deferredDefinition = getDefinitionByClass(componentClass);
        var newInstance = getInstanceByDefinition(deferredDefinition);
        setDependencies(newInstance);
        newInstance = setActions(newInstance);
        if (componentClass.isInstance(newInstance)) {
            return (T) newInstance ;
        }
        throw new ComponentBindException("No implementation found for " + componentClass.getCanonicalName());
    }

    public <T> Definition getDefinitionByClass(Class<T> componentClass) {
        if (componentClass.isInterface()) {
            final var definitions = context.getComponentsDefinitions().stream()
                    .filter(definition -> definition.getComponentAliases().contains(componentClass.getCanonicalName()))
                    .toList();
            if (definitions.size() > 1) {
                throw new ComponentBindException("More than one implementation found for " + componentClass.getCanonicalName());
            }
            return definitions.stream().findFirst()
                    .orElseThrow(() -> new ComponentBindException("No implementation found for " + componentClass.getCanonicalName()));
        } else {
            return context.getDefinitionByAliasName(componentClass.getCanonicalName())
                    .orElseThrow(() -> new ComponentBindException("No definition found for type: " + componentClass.getCanonicalName()));
        }
    }

    public void setDependencies(Object instance) {
        Object newInstance = instance;
        for (ComponentProcessor processor : componentProcessors()) {
            processor.process(context, newInstance);
        }
    }

    private Object setActions(Object newInstance) {
        Object instance = newInstance;
        for (ActionsProcessor processor : actionProcessors()) {
            instance = processor.applyAction(context, instance);
        }
        return instance;
    }

    @SneakyThrows
    public Object getInstanceByDefinition(Definition definition) {
        return componentSourceMap.get(definition.getComponentSourceName()).getInstanceByDefinition(definition);
    }

    public Set<ComponentProcessor> componentProcessors() {
        return context.getComponentProcessors();
    }

    public Set<ActionsProcessor> actionProcessors() {
        return context.getActionsProcessors();
    }

}
