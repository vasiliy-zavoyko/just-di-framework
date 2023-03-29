package ru.zavoyko.framework.di.source;


import lombok.SneakyThrows;
import ru.zavoyko.framework.di.actions.ActionsProcessor;
import ru.zavoyko.framework.di.processors.ComponentProcessor;
import ru.zavoyko.framework.di.source.impl.java.exceptions.JavaObjectSourceProcessorException;
import ru.zavoyko.framework.di.utils.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

public interface ComponentSource {

    String getPackageToScan();

    Object getInstanceByDefinition(Definition definition);

    Set<Definition> getComponentDefinitions();

    Set<ComponentProcessor> getComponentProcessors();

    Set<ActionsProcessor> getActionProcessors();

}
