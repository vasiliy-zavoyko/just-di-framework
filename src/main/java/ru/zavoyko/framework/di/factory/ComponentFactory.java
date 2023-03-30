package ru.zavoyko.framework.di.factory;

import lombok.SneakyThrows;
import ru.zavoyko.framework.di.source.Definition;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ComponentFactory {

    Object createComponent(Definition definition);

    Set<Definition> getComponentsDefinitions();

}
