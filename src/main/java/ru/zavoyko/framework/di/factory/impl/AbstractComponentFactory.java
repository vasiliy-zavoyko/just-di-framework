package ru.zavoyko.framework.di.factory.impl;

import ru.zavoyko.framework.di.processors.actions.ActionsProcessor;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.factory.ComponentFactory;
import ru.zavoyko.framework.di.processors.ComponentProcessor;
import ru.zavoyko.framework.di.utils.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.util.Set;


public abstract class AbstractComponentFactory implements ComponentFactory {

    protected void runInitMethod(Object instance) {
        ReflectionUtils.getAllMethodsByAnnotation(instance.getClass(), PostConstruct.class).forEach(method ->
                ReflectionUtils.invokeMethod(method, instance, null)
        );
    }

    protected void setDependencies(final Object instance) {
        for (ComponentProcessor processor : getComponentProcessors()) {
            processor.process(getContext(), instance);
        }
    }

    protected Object setActions(Object newInstance) {
        Object instance = newInstance;
        for (ActionsProcessor processor : getActionProcessors()) {
            instance = processor.applyAction(getContext(), instance);
        }
        return instance;
    }

    protected abstract Set<ComponentProcessor> getComponentProcessors();

    protected abstract Set<ActionsProcessor> getActionProcessors();

    protected abstract Context getContext();

}
