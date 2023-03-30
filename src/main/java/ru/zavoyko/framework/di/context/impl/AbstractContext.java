package ru.zavoyko.framework.di.context.impl;

import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.exceptions.DIComponentDefinitionException;
import ru.zavoyko.framework.di.source.Definition;

import java.util.Set;

public abstract class AbstractContext implements Context {

    public Definition getAndCheckOneDefinitionOrThrowException(Class<?> type) {
        if (type.isInterface()) {
            final var definitions = getComponentsDefinitions().stream()
                    .filter(definition -> definition.getComponentAliases().contains(type.getCanonicalName()))
                    .toList();
            if (definitions.size() > 1) {
                throw new DIComponentDefinitionException("More than one implementation found for " + type.getCanonicalName());
            }
            return definitions.stream().findFirst()
                    .orElseThrow( () -> new DIComponentDefinitionException("No definition found for interface: " + type.getCanonicalName()));
        }

        return getComponentsDefinitions().stream()
                .filter(definition -> type.getCanonicalName().equals(definition.getName()))
                .findFirst()
                .orElseThrow( () -> new DIComponentDefinitionException("No definition found for type: " + type.getCanonicalName()));
    }


    protected Definition getOneDefinitionByAliasNameOrThrowException(String aliasName) {
        return getComponentsDefinitions().stream()
                .filter(definition -> definition.getName().equals(aliasName))
                .findFirst()
                .orElse(
                        getComponentsDefinitions().stream()
                                .filter(definition -> definition.getComponentAliases().contains(aliasName))
                                .findFirst()
                                .orElseThrow(() -> new DIComponentDefinitionException("No definition found for alias: " + aliasName)));
    }

    protected abstract Set<Definition> getComponentsDefinitions();

}
