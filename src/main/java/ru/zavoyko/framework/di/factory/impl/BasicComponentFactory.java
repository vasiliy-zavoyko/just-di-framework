package ru.zavoyko.framework.di.factory.impl;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.actions.ActionsProcessor;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.context.impl.BasicContext;
import ru.zavoyko.framework.di.exceptions.DIFrameworkComponentBindException;
import ru.zavoyko.framework.di.processors.ComponentProcessor;
import ru.zavoyko.framework.di.source.ComponentSource;
import ru.zavoyko.framework.di.source.Definition;
import ru.zavoyko.framework.di.utils.ReflectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.util.*;

@RequiredArgsConstructor
@Builder
public class BasicComponentFactory extends AbstractComponentFactory {

    private static final Logger logger = LoggerFactory.getLogger(BasicComponentFactory.class);

    @Nonnull
    private final BasicContext context;
    @Nonnull
    private final Map<String, ComponentSource> componentSourceMap;
    @Nonnull
    @Getter
    private final Map<String, Definition> definitions;
    @Nonnull
    @Getter
    private final Set<ComponentProcessor> componentProcessors;
    @Nonnull
    @Getter
    private final Set<ActionsProcessor> actionsProcessors;
    @Nonnull
    @Getter
    private final Set<Definition> componentsDefinitions;

    @Override
    public <T> T createComponent(Class<T> componentClass) {
        Definition deferredDefinition = getDefinitionByClass(componentClass);
        var newInstance = getInstanceByDefinition(deferredDefinition);
        setDependencies(newInstance);
        runInitMethod(newInstance);
        newInstance = setActions(newInstance);
        if (componentClass.isInstance(newInstance)) {
            return (T) newInstance ;
        }
        throw new DIFrameworkComponentBindException("No implementation found for " + componentClass.getCanonicalName());
    }


    public Object getInstanceByDefinition(Definition definition) {
        return componentSourceMap.get(definition.getComponentSourceName()).getInstanceByDefinition(definition);
    }

    @Override
    public Set<ComponentProcessor> getComponentProcessors() {
        return componentProcessors;
    }

    public void setComponentProcessors(Set<ComponentProcessor> componentProcessorsToSet) {
        this.componentProcessors.addAll(componentProcessors);
    }

    @Override
    public Set<ActionsProcessor> getActionProcessors() {
        return actionsProcessors;
    }

    public void setActionsProcessors(Set<ActionsProcessor> actionsProcessorsToSet) {
        this.actionsProcessors.addAll(actionsProcessorsToSet);
    }

    @Override
    public Context getContext() {
        return context;
    }

}
