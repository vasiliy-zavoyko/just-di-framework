package ru.zavoyko.framework.di.context.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.annotations.TypeToInject;
import ru.zavoyko.framework.di.configuration.Configuration;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.exception.DIException;
import ru.zavoyko.framework.di.factory.ObjectFactory;
import ru.zavoyko.framework.di.processor.Processor;

import javax.annotation.PreDestroy;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.lang.reflect.Modifier.isAbstract;
import static ru.zavoyko.framework.di.Util.castAndReturn;
import static ru.zavoyko.framework.di.Util.getMethods;


@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Slf4j
public class ContextImpl implements Context {

    private final Map<Class, Object> singletonMap = new ConcurrentHashMap<>();
    private final List<Configuration> configurations = new CopyOnWriteArrayList<>();
    private final Map<String, String> propertyMap = new ConcurrentHashMap<>();
    private final List<Processor> processorList = new CopyOnWriteArrayList<>();

    private final ObjectFactory objectFactory;

    public void init() {
        log.debug("Initiating context");
        configurations.stream()
                .map(Configuration::getComponentClasses)
                .flatMap(Set::stream)
                .filter(type -> type.isAnnotationPresent(TypeToInject.class) && type.getAnnotation(TypeToInject.class).isSingleton())
                .forEach(this::getBean);
    }

    public void setConfigurations(List<Configuration> configurationList) {
        this.configurations.addAll(configurationList);
    }

    public void setProcessors(List<Configuration> configurationList) {
        configurationList.forEach(configuration -> processorList.addAll(configuration.getProcessors()));
    }

    public void setPropertyMap(Map<String, String> propertiesMap) {
        this.propertyMap.putAll(propertiesMap);
    }

    @Override
    public <T> T getBean(final Class<T> beanClassToGet) {
        log.debug("Getting bean of class {}", beanClassToGet.getName());
        final Class<?> classToGet = beanClassToGet.isInterface() ? getImplementation(beanClassToGet) : beanClassToGet;

        if (singletonMap.containsKey(classToGet)) {
            return castAndReturn(beanClassToGet, singletonMap.get(classToGet));
        } else if (beanClassToGet.isAnnotationPresent(TypeToInject.class) && !beanClassToGet.getAnnotation(TypeToInject.class).isSingleton()) {
            return castAndReturn(beanClassToGet, objectFactory.getComponent(classToGet));
        }

        return castAndReturn(beanClassToGet, singletonMap.computeIfAbsent(classToGet, objectFactory::getComponent));
    }

    @Override
    public String getProperty(String key) {
        return propertyMap.get(key);
    }

    @Override
    public List<? extends Processor> getAllProcessor() {
        return processorList;
    }

    @Override
    public <T> Class<? extends T> getImplementation(Class<T> clazz) {
        if (!clazz.isInterface() && !isAbstract(clazz.getModifiers())) {
            return clazz;
        }

        final var classList = configurations.stream()
                .map(Configuration::getComponentClasses)
                .flatMap(Set::stream)
                .filter(clazz::isAssignableFrom)
                .toList();

        if (classList.size() != 1) {
            throw new DIException("0 or more than one implementations found", new IllegalArgumentException(clazz.getName()));
        }

        Class<?> implClass = classList.get(0);

        if (!clazz.isAssignableFrom(implClass)) {
            throw new DIException("Found implementation does not match the required type", new IllegalArgumentException(clazz.getName()));
        }

        @SuppressWarnings("unchecked")
        Class<? extends T> castedClass = (Class<? extends T>) implClass;
        return castedClass;
    }

    @Override
    public void close() {
        log.debug("Calling preDestroy");
        singletonMap.values()
                .forEach(object ->
                        getMethods(object)
                                .stream()
                                .filter(method -> method.isAnnotationPresent(PreDestroy.class))
                                .forEach(method -> {
                                    try {
                                        method.invoke(object);
                                    } catch (IllegalAccessException | InvocationTargetException e) {
                                        log.error("Error during preDestroy: ", e);
                                    }
                                })
        );
    }

}
