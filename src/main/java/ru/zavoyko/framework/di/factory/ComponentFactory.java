package ru.zavoyko.framework.di.factory;

import lombok.SneakyThrows;
import ru.zavoyko.framework.di.source.Definition;

import java.util.Optional;
import java.util.Set;

public interface ComponentFactory {
    @SneakyThrows
    <T> T createComponent(Class<T> componentClass);

    Optional<Definition> getDefinitionByAliasName(String name);

    Set<Definition> getComponentsDefinitions();

    Definition getAndCheckDefinition(Class<?> type);

}
