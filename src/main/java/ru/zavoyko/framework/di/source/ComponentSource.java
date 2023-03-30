package ru.zavoyko.framework.di.source;


import ru.zavoyko.framework.di.processors.actions.ActionsProcessor;
import ru.zavoyko.framework.di.processors.ComponentProcessor;

import java.util.Set;

public interface ComponentSource {

    String getSourcePackage();

    Object getInstanceByDefinition(Definition definition);

    Set<Definition> getComponentDefinitions();

    Set<ComponentProcessor> getComponentProcessors();

    Set<ActionsProcessor> getActionProcessors();

}
