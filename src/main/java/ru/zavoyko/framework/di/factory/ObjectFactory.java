package ru.zavoyko.framework.di.factory;


import lombok.SneakyThrows;
import org.checkerframework.checker.units.qual.C;
import org.reflections.Reflections;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.exceptions.DIFrameworkInstansiationException;
import ru.zavoyko.framework.di.processors.component.ComponentProcessor;
import ru.zavoyko.framework.di.processors.functions.FunctionProcessor;
import ru.zavoyko.framework.di.source.JavaObjectSource;
import ru.zavoyko.framework.di.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.io.Resources.getResource;

public class ObjectFactory {

    private static final String monitor = "monitor";

    private final JavaObjectSource objectSource;
    private final Set<ComponentProcessor> componentProcessorSet;
    private final Set<FunctionProcessor> functionProcessorSet;
    private final Set<Class<?>> classes;

    private final Context context;

    public ObjectFactory(Context context, JavaObjectSource objectSource) {
        this.objectSource = objectSource;
        this.componentProcessorSet = objectSource.getComponentProcessors();
        this.classes = objectSource.getComponentClasses();
        this.functionProcessorSet = objectSource.getFunctionProcessors();
        this.context = context;
    }


    @SneakyThrows
    public <T> T createObject(Class<T> type) {
        T instance;
        if (type.isInterface()) {
            instance = (T) createObjectForInterface(type);
        } else {
            instance = (T) type.getDeclaredConstructor().newInstance();
        }

        final var value = instance;
        componentProcessorSet.stream()
                .forEach(componentProcessor -> componentProcessor.process(context, value));

        for (FunctionProcessor functionProcessor : functionProcessorSet) {
            instance = (T) functionProcessor.process(context, instance);
        }

        final var insstForRun = instance;
        ReflectionUtils.getMethodAnnotatedBy(instance, PostConstruct.class).stream()
                .findFirst()
                .ifPresent(item -> {
                    ReflectionUtils.invokeMethod(insstForRun, item, null);
                });


        return instance;
    }

    @SneakyThrows
    private <T> T createObjectForInterface(Class<T> type) {
        final var classSet = classes.stream()
                .filter(aClass -> type.isAssignableFrom(aClass))
                .collect(Collectors.toSet());
        if (classSet.size() != 1) {
            throw new DIFrameworkInstansiationException("0 or more than 1 impl found for type " + type);
        }
        return (T) classSet.iterator().next().getDeclaredConstructor().newInstance();
    }

}
