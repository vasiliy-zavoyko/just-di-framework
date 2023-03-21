package ru.zavoyko.framework.di.factory;

import lombok.SneakyThrows;
import ru.zavoyko.framework.di.context.impl.BasicContext;
import ru.zavoyko.framework.di.exceptions.ComponentBindException;
import ru.zavoyko.framework.di.processors.ComponentProcessor;
import ru.zavoyko.framework.di.source.Definition;

import java.util.Set;

public class BasicComponentFactory implements ComponentFactory {

    private BasicContext context;


    public BasicComponentFactory(BasicContext context) {
        this.context = context;
    }

    @Override
    @SneakyThrows
    public <T> T createComponent(Class<T> componentClass) {
        Definition deferredDefinition = getDefinitionByClass(componentClass);
        var newInstance = getInstanceByDefinition(deferredDefinition);
        setDependencies(newInstance);
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
        componentProcessors().forEach(processor -> processor.process(context, instance));
    }

    @SneakyThrows
    public Object getInstanceByDefinition(  Definition defenition) {
        return defenition.getType().getDeclaredConstructor().newInstance();
    }

    public Set<ComponentProcessor> componentProcessors() {
        return context.getProcessors();
    }

}
