package ru.zavoyko.framework.di.factory.impl;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.processors.actions.ActionsProcessor;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.context.impl.BasicContext;
import ru.zavoyko.framework.di.processors.ComponentProcessor;
import ru.zavoyko.framework.di.source.ComponentSource;
import ru.zavoyko.framework.di.source.Definition;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Builder
@RequiredArgsConstructor(access = PRIVATE)
public class BasicComponentFactory extends AbstractComponentFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicComponentFactory.class);

    @Nonnull
    private final BasicContext context;
    @Nonnull
    private final Map<String, ComponentSource> componentSourceMap;

    @Override
    public Object createComponent(Definition definition) {
        var newInstance = getInstance(definition);
        setDependencies(newInstance);
        runInitMethod(newInstance);
        newInstance = setActions(newInstance);
        return newInstance;
    }

    @Override
    public Set<Definition> getComponentsDefinitions() {
        return componentSourceMap.values().stream()
                .map(ComponentSource::getComponentDefinitions)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    public Object getInstance(Definition definition) {
        return componentSourceMap.get(definition.getComponentSourceName()).getInstanceByDefinition(definition);
    }

    @Override
    public Set<ComponentProcessor> getComponentProcessors() {
        return componentSourceMap.values().stream()
                .map(ComponentSource::getComponentProcessors)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ActionsProcessor> getActionProcessors() {
        return componentSourceMap.values().stream()
                .map(ComponentSource::getActionProcessors)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public Context getContext() {
        return context;
    }

}
