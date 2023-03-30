package ru.zavoyko.framework.di.source.impl.java;

import org.reflections.Reflections;
import ru.zavoyko.framework.di.processors.actions.ActionsProcessor;
import ru.zavoyko.framework.di.exceptions.DIFrameworkInstantiationException;
import ru.zavoyko.framework.di.inject.java.TypeToInject;
import ru.zavoyko.framework.di.processors.ComponentProcessor;
import ru.zavoyko.framework.di.source.Definition;
import ru.zavoyko.framework.di.source.impl.AbstractComponentSource;
import ru.zavoyko.framework.di.source.impl.java.exceptions.JavaObjectSourceProcessorException;
import ru.zavoyko.framework.di.utils.ReflectionUtils;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The Java configuration for the DI container.
 */
public class JavaObjectComponentSource extends AbstractComponentSource {

    private final String sourcePackage;
    private final Reflections scanner;

    public JavaObjectComponentSource(String packageToScan) {
        this.sourcePackage = packageToScan;
        this.scanner = new Reflections(packageToScan);
    }

    @Override
    public Set<Definition> getComponentDefinitions() {
        return scanner.getTypesAnnotatedWith(TypeToInject.class, true).stream()
                .filter(clazz -> !clazz.isInterface())
                .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                .map(this::getComponentDefinition)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ComponentProcessor> getComponentProcessors() {
        return scanner.getSubTypesOf(ComponentProcessor.class).stream()
                .filter(clazz -> !clazz.isInterface())
                .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                .map(clazz -> {
                    try {
                        return ReflectionUtils.createInstance(clazz);
                    } catch (DIFrameworkInstantiationException e) {
                        throw new JavaObjectSourceProcessorException("Can't create instance of the class: " + clazz.getCanonicalName(), e);
                    }
                })
                .collect(Collectors.toSet());
    }

    @Override
    public Set<ActionsProcessor> getActionProcessors() {
        return scanner.getSubTypesOf(ActionsProcessor.class).stream()
                .filter(clazz -> !clazz.isInterface())
                .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                .map(clazz -> {
                    try {
                        return ReflectionUtils.createInstance(clazz);
                    } catch (DIFrameworkInstantiationException e) {
                        throw new JavaObjectSourceProcessorException("Can't create instance of the class: " + clazz.getCanonicalName(), e);
                    }
                })
                .collect(Collectors.toSet());
    }

    @Override
    public String getSourcePackage() {
        return sourcePackage;
    }

}
