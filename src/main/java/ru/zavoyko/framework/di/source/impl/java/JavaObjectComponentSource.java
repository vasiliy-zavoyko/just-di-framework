package ru.zavoyko.framework.di.source.impl.java;

import org.reflections.Reflections;
import ru.zavoyko.framework.di.actions.ActionsProcessor;
import ru.zavoyko.framework.di.inject.java.TypeToInject;
import ru.zavoyko.framework.di.processors.ComponentProcessor;
import ru.zavoyko.framework.di.source.impl.AbstractComponentSource;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The Java configuration for the DI container.
 */
public class JavaObjectComponentSource extends AbstractComponentSource {

    private String packageToScan;

    public JavaObjectComponentSource(String packageToScan) {
        this.packageToScan = packageToScan;
    }

    @Override
    protected Set<Class<?>> getTypeToInjectClasses() {
        final var scanner = new Reflections(packageToScan);
        return scanner.getTypesAnnotatedWith(TypeToInject.class, true).stream()
                .filter(clazz -> !clazz.isInterface())
                .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                .collect(Collectors.toSet());
    }

    @Override
    protected Set<Class<? extends ComponentProcessor>> getComponentProcessorClasses() {
        final var scanner = new Reflections(packageToScan);
        return scanner.getSubTypesOf(ComponentProcessor.class).stream()
                .filter(clazz -> !clazz.isInterface())
                .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                .collect(Collectors.toSet());
    }

    @Override
    protected Set<Class<? extends ActionsProcessor>> getActionsProcessorClasses() {
        final var scanner = new Reflections(packageToScan);
        return scanner.getSubTypesOf(ActionsProcessor.class).stream()
                .filter(clazz -> !clazz.isInterface())
                .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                .collect(Collectors.toSet());
    }

    @Override
    protected String getPackageToScan() {
        return packageToScan;
    }

}
