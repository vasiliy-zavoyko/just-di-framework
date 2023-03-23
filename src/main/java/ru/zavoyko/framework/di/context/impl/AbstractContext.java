package ru.zavoyko.framework.di.context.impl;

import ru.zavoyko.framework.di.actions.ActionsProcessor;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.exceptions.ComponentBindException;
import ru.zavoyko.framework.di.processors.ComponentProcessor;
import ru.zavoyko.framework.di.source.Definition;
import ru.zavoyko.framework.di.source.impl.java.exceptions.JavaObjectSourceProcessorException;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractContext implements Context {

    @Override
    public Optional<Definition> getDefinitionByAliasName(String name) {
        return definitions().values().stream()
                .filter(Definition::isComponent)
                .filter(definition -> definition.getComponentAliases().contains(name))
                .findFirst();
    }

    protected Definition getAndCheckDefinition(Class<?> type) {
        if (type.isInterface()) {
            final var definitions = componentsDefinitions().stream()
                    .filter(definition -> definition.getComponentAliases().contains(type.getCanonicalName()))
                    .toList();
            if (definitions.size() > 1) {
                throw new ComponentBindException("More than one implementation found for " + type.getCanonicalName());
            }
            return definitions.stream().findFirst()
                    .orElseThrow( () -> new ComponentBindException("No definition found for interface: " + type.getCanonicalName()));
        }
        return Optional.ofNullable(definitions().get(type.getCanonicalName()))
                .orElseThrow( () -> new ComponentBindException("No definition found for type: " + type.getCanonicalName()));
    }

    protected ComponentProcessor createComponentProcessor(Class<? extends ComponentProcessor> processor) {
        try {
            return processor.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new JavaObjectSourceProcessorException("Failed to create processor " + processor, e);
        }
    }

    protected ActionsProcessor createActionProcessor(Class<? extends ActionsProcessor> processor) {
        try {
            return processor.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new JavaObjectSourceProcessorException("Failed to create processor " + processor, e);
        }
    }

    protected abstract Map<String, Definition> definitions();

    protected abstract Set<Definition> componentsDefinitions();

}
