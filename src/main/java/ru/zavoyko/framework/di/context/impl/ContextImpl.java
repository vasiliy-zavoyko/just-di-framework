package ru.zavoyko.framework.di.context.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.Util;
import ru.zavoyko.framework.di.annotations.TypeToInject;
import ru.zavoyko.framework.di.configuration.Configuration;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.exception.DIException;
import ru.zavoyko.framework.di.factory.ObjectFactory;
import ru.zavoyko.framework.di.factory.impl.ObjectFactoryImpl;
import ru.zavoyko.framework.di.processor.Processor;

import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static com.google.common.io.Resources.getResource;
import static java.lang.reflect.Modifier.isAbstract;


@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ContextImpl implements Context {

    @SneakyThrows
    public static Context createContext(List<String> packagesToScan) {
        final var configurationList = packagesToScan.stream()
                .map(Configuration::getConfiguration)
                .toList();

        var propertiesMap = new BufferedReader(new InputStreamReader(getResource("application.properties").openStream()))
                .lines()
                .map(String::trim)
                .map(str -> str.split("="))
                .collect(Collectors.toMap(pair -> pair[0].trim(), pair -> pair[1].trim()));

        var componentList = configurationList.stream().map(Configuration::getComponentClasses)
                .flatMap(Set::stream)
                .toList();

        final var factory = new ObjectFactoryImpl();
        final var context = new ContextImpl(factory);
        factory.setContext(context);
        context.setProcessors(configurationList);
        context.setPropertyMap(propertiesMap);
        context.setConfigurations(configurationList);
        context.setImplementations(componentList);
        log.info("calling init");
        context.init();
        return context;
    }

    private final Map<Class, Object> singletonMap = new ConcurrentHashMap<>();
    private final List<Configuration> configurations = new CopyOnWriteArrayList<>();
    private final Map<String, String> propertyMap = new ConcurrentHashMap<>();
    private final List<Processor> processorList = new CopyOnWriteArrayList<>();
    private final List<Class<?>> implementations = new CopyOnWriteArrayList<>();
    private final ObjectFactory objectFactory;

    public void init() {
        implementations.stream()
                .filter(type -> {
                    if (type.isAnnotationPresent(TypeToInject.class)) {
                        return type.getAnnotation(TypeToInject.class).isSingleton();
                    }
                    return false;
                })
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

    public void setImplementations(List<Class<?>> classes) {
        implementations.addAll(classes);
    }

    @Override
    public <T> T getBean(final Class<T> beanClassToGet) { //TODO: make thread safe
        Class<?> classToGet = beanClassToGet;
        if (beanClassToGet.isInterface()) {
            classToGet = getImplementation(beanClassToGet);
        }
        if (singletonMap.containsKey(classToGet)) {
            return beanClassToGet.cast(singletonMap.get(classToGet));
        }
        final var newImpl = objectFactory.getComponent(classToGet);

        final var result = beanClassToGet.cast(newImpl);
        if (beanClassToGet.isAnnotationPresent(TypeToInject.class) && beanClassToGet.getAnnotation(TypeToInject.class).isSingleton()) {
            singletonMap.put(classToGet, result);
        }
        return result;
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

        var classList = implementations.stream()
                .filter(impl -> impl.isInstance(clazz))
                .map(impl -> {
                    return (Class<? extends T>) impl;
                })
                .toList();

        if (classList.size() != 1) {
            throw new DIException("0 or more than one implementations found", new IllegalArgumentException(clazz.getName()));
        }

        return classList.get(0);
    }

    @Override
    public void close() {
        log.info("im out this  *****!!!");

        singletonMap.values()
                .forEach(object -> {
                    Util.getMethods(object)
                            .stream()
                            .filter(method -> method.isAnnotationPresent(PreDestroy.class))
                            .findFirst()
                            .ifPresent(method -> {
                                try {
                                    method.invoke(object, null);
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                } catch (InvocationTargetException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                });
    }

}
