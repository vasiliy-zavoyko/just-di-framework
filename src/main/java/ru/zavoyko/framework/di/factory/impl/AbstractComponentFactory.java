package ru.zavoyko.framework.di.factory.impl;

import ru.zavoyko.framework.di.actions.ActionsProcessor;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.exceptions.DIFrameworkComponentBindException;
import ru.zavoyko.framework.di.factory.ComponentFactory;
import ru.zavoyko.framework.di.processors.ComponentProcessor;
import ru.zavoyko.framework.di.source.Definition;
import ru.zavoyko.framework.di.utils.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


public abstract class AbstractComponentFactory implements ComponentFactory {

    @Override
    public Optional<Definition> getDefinitionByAliasName(String name) {
        return getDefinitions().values().stream()
                .filter(Definition::isComponent)
                .filter(definition -> definition.getComponentAliases().contains(name))
                .findFirst();
    }

    public Definition getAndCheckDefinition(Class<?> type) {
        if (type.isInterface()) {
            final var definitions = getComponentsDefinitions().stream()
                    .filter(definition -> definition.getComponentAliases().contains(type.getCanonicalName()))
                    .toList();
            if (definitions.size() > 1) {
                throw new DIFrameworkComponentBindException("More than one implementation found for " + type.getCanonicalName());
            }
            return definitions.stream().findFirst()
                    .orElseThrow( () -> new DIFrameworkComponentBindException("No definition found for interface: " + type.getCanonicalName()));
        }
        return Optional.ofNullable(getDefinitions().get(type.getCanonicalName()))
                .orElseThrow( () -> new DIFrameworkComponentBindException("No definition found for type: " + type.getCanonicalName()));
    }

    protected <T> Definition getDefinitionByClass(Class<T> componentClass) {
        if (componentClass.isInterface()) {
            final var definitions = getComponentsDefinitions().stream()
                    .filter(definition -> definition.getComponentAliases().contains(componentClass.getCanonicalName()))
                    .toList();
            if (definitions.size() > 1) {
                throw new DIFrameworkComponentBindException("More than one implementation found for " + componentClass.getCanonicalName());
            }
            return definitions.stream().findFirst()
                    .orElseThrow(() -> new DIFrameworkComponentBindException("No implementation found for " + componentClass.getCanonicalName()));
        } else {
            return getDefinitionByAliasName(componentClass.getCanonicalName())
                    .orElseThrow(() -> new DIFrameworkComponentBindException("No definition found for type: " + componentClass.getCanonicalName()));
        }
    }

    protected void runInitMethod(Object instance) {
        ReflectionUtils.getAllMethodsByAnnotation(instance.getClass(), PostConstruct.class).forEach(method -> {
                ReflectionUtils.invokeMethod(method, instance, null);
        });
    }

    protected void setDependencies(Object instance) {
        Object newInstance = instance;
        for (ComponentProcessor processor : getComponentProcessors()) {
            processor.process(getContext(), newInstance);
        }
    }

    protected Object setActions(Object newInstance) {
        Object instance = newInstance;
        for (ActionsProcessor processor : getActionProcessors()) {
            instance = processor.applyAction(getContext(), instance);
        }
        return instance;
    }

    protected abstract Map<String, Definition> getDefinitions();

    protected abstract Set<ComponentProcessor> getComponentProcessors();

    protected abstract Set<ActionsProcessor> getActionProcessors();

    protected abstract Context getContext();

}
