package ru.zavoyko.framework.di.source;

import org.reflections.Reflections;
import ru.zavoyko.framework.di.inject.TypeToInject;
import ru.zavoyko.framework.di.processors.component.ComponentProcessor;
import ru.zavoyko.framework.di.processors.functions.FunctionProcessor;
import ru.zavoyko.framework.di.util.ReflectionUtils;

import java.util.Set;
import java.util.stream.Collectors;

public class JavaObjectSource {

    private final Reflections scanner;

    public JavaObjectSource(String basePackage) {
        this.scanner = new Reflections(basePackage);
    }

    public Set <ComponentProcessor> getComponentProcessors() {
        return scanner.getSubTypesOf(ComponentProcessor.class).stream()
                .map(ReflectionUtils::createObject)
                .collect(Collectors.toSet());
    }

    public Set<FunctionProcessor> getFunctionProcessors() {
        return scanner.getSubTypesOf(FunctionProcessor.class).stream()
                .map(ReflectionUtils::createObject)
                .collect(Collectors.toSet());
    }


    public Set <Class<?>> getComponentClasses() {
        return scanner.getTypesAnnotatedWith(TypeToInject.class);
    }



}
