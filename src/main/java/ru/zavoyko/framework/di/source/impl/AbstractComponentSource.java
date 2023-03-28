package ru.zavoyko.framework.di.source.impl;

import ru.zavoyko.framework.di.actions.ActionsProcessor;
import ru.zavoyko.framework.di.inject.java.TypeToInject;
import ru.zavoyko.framework.di.processors.ComponentProcessor;
import ru.zavoyko.framework.di.source.ComponentSource;
import ru.zavoyko.framework.di.source.Definition;

import java.util.*;

public abstract class AbstractComponentSource implements ComponentSource {

    @Override
    public Set<Definition> findAllDefinitions() {
        final var definitions = new HashSet<Definition>();

        for (var item : getTypeToInjectClasses()) {
            final var componentDefinition = getComponentDefinition(item);
            definitions.add(componentDefinition);
        }

        for (var item : getComponentProcessorClasses()) {
            final var componentDefinition = getComponentDefinition(item);
            definitions.add(componentDefinition);
        }

        for (var item : getActionsProcessorClasses()) {
            final var componentDefinition = getComponentDefinition(item);
            definitions.add(componentDefinition);
        }

        return definitions;
    }

    protected abstract Set<Class<?>> getTypeToInjectClasses();

    protected abstract Set<Class<? extends ComponentProcessor>> getComponentProcessorClasses();

    protected abstract Set<Class<? extends ActionsProcessor>> getActionsProcessorClasses();

    protected Definition getComponentDefinition(Class<?> clazz) {
        if (clazz.isAnnotationPresent(TypeToInject.class)) {
            return getComponentDefinitionForTypeToInject(clazz);
        } else {
            return getComponentDefinitionForComponentProcessor(clazz);
        }
    }

    private Definition getComponentDefinitionForComponentProcessor(Class<?> clazz) {
        final var canonicalName = clazz.getCanonicalName();
        final var typeName = clazz.getTypeName();
        final var aliases = new ArrayList<>(getAliases(clazz, new LinkedList<>()));
        return BasicDefinition.builder()
                .isComponent(false)
                .isLazy(false)
                .isSingleton(true)
                .type(clazz)
                .componentSourceName(getPackageToScan())
                .componentName(canonicalName)
                .componentAliases(aliases)
                .componentClassName(typeName)
                .build();
    }

    private Definition getComponentDefinitionForTypeToInject(Class<?> clazz) {
        final var typeToInject = clazz.getAnnotation(TypeToInject.class);
        final var canonicalName = clazz.getCanonicalName();
        final var typeName = clazz.getTypeName();
        final var aliases = new ArrayList<>(getAliases(clazz, new LinkedList<>()));
        return BasicDefinition.builder()
                .isComponent(true)
                .isLazy(typeToInject.isLazy())
                .isSingleton(typeToInject.isSingleton())
                .type(clazz)
                .componentSourceName(getPackageToScan())
                .componentName(canonicalName)
                .componentAliases(aliases)
                .componentClassName(typeName)
                .build();
    }

    protected LinkedList<String> getAliases(Class<?> item, LinkedList<String> aliases) {
        if (item.getSuperclass() == null) {
            return aliases;
        }
        Arrays.stream(item.getInterfaces()).map(Class::getCanonicalName).forEach(aliases::add);
        aliases.add(item.getCanonicalName());
        return getAliases(item.getSuperclass(), aliases);
    }

}
