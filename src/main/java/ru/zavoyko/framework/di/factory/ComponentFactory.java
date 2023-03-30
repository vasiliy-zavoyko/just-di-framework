package ru.zavoyko.framework.di.factory;

import ru.zavoyko.framework.di.source.Definition;

import java.util.Set;

public interface ComponentFactory {

    Object createComponent(Definition definition);

    Set<Definition> getComponentsDefinitions();

}
