package ru.zavoyko.framework.di.factory;

import lombok.SneakyThrows;
import ru.zavoyko.framework.di.source.Definition;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ComponentFactory {
    @SneakyThrows
//    <T> T createComponent(Class<T> componentClass);

    Object createComponent(Definition definition);

//    List<Class> getClassesByAliasName(String name);

    Set<Definition> getComponentsDefinitions();

//    Definition getAndCheckDefinition(Class<?> type);

}
