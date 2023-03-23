package ru.zavoyko.framework.di.source;


import lombok.SneakyThrows;

import java.util.Set;

public interface ComponentSource {

    Set<Definition> getDefinitions();

    @SneakyThrows
    default Object getInstanceByDefinition(Definition definition) {
        return definition.getType().getDeclaredConstructor().newInstance();
    }

}
