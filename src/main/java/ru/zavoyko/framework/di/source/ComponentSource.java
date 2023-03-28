package ru.zavoyko.framework.di.source;


import lombok.SneakyThrows;
import ru.zavoyko.framework.di.actions.ActionsProcessor;
import ru.zavoyko.framework.di.processors.ComponentProcessor;
import ru.zavoyko.framework.di.source.impl.java.exceptions.JavaObjectSourceProcessorException;
import ru.zavoyko.framework.di.utils.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public interface ComponentSource {

    Set<Definition> findAllDefinitions();

    String getPackageToScan();

    default Object getInstanceByDefinition(Definition definition) {
        return ReflectionUtils.createInstance(definition.getType());
    }

    default ComponentProcessor createComponentProcessor(Class<? extends ComponentProcessor> processor) {
        try {
            return processor.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new JavaObjectSourceProcessorException("Failed to create processor " + processor, e);
        }
    }

    default ActionsProcessor createActionProcessor(Class<? extends ActionsProcessor> processor) {
        try {
            return processor.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new JavaObjectSourceProcessorException("Failed to create processor " + processor, e);
        }
    }

    default Set<Definition> getComponentDefinitions() {
        final var definitions = new HashSet<Definition>();
        findAllDefinitions().stream()
                .filter(Definition::isComponent)
                .forEach(definitions::add);
        return definitions;
    }

    default Set<ComponentProcessor> getComponentProcessors() {
        final var componentProcessors = new HashSet<ComponentProcessor>();
        findAllDefinitions().stream()
                .filter(item -> !item.isComponent())
                .map(Definition::getType)
                .filter(ComponentProcessor.class::isAssignableFrom)
                .map(this::createComponentProcessor)
                .forEach(componentProcessors::add);
        return componentProcessors;
    }

    default Set<ActionsProcessor> getActionProcessors() {
        final var actionsProcessors = new HashSet<ActionsProcessor>();
        findAllDefinitions().stream()
                .filter(item -> !item.isComponent())
                .map(Definition::getType)
                .filter(ActionsProcessor.class::isAssignableFrom)
                .map(this::createActionProcessor)
                .forEach(actionsProcessors::add);
        return actionsProcessors;
    }

}
