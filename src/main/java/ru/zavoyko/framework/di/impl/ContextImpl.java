package ru.zavoyko.framework.di.impl;

import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.BeanProcessor;
import ru.zavoyko.framework.di.Config;
import ru.zavoyko.framework.di.Context;
import ru.zavoyko.framework.di.ObjectFactory;
import ru.zavoyko.framework.di.anotations.TypeToInject;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static ru.zavoyko.framework.di.utils.DIFObjectUtils.*;

@Slf4j
public class ContextImpl implements Context {

    public static ContextImpl getInstance(Config configToSet) {
        return new ContextImpl(checkNonNullOrThrowException(configToSet, "Config can't be null"));
    }

    private Config config;
    private ObjectFactory objectFactory;
    private Map<Class<?>, Object> singletonMap;

    private ContextImpl(Config configToSet) {
        this.config = configToSet;
        this.singletonMap = new ConcurrentHashMap<>();
    }

    public void setObjectFactory(ObjectFactory objectFactoryToSet) {
        this.objectFactory = checkNonNullOrThrowException(objectFactoryToSet, "Object factory can't be null");
    }

    @Override
    public <T> T getBean(final Class<T> clazz) {
        checkNonNullOrThrowException(clazz, "Type can't be null");
        log.debug("Requesting bean of type: {}", clazz.getName());
        Class<? extends T> implClass = clazz;
        if (implClass.isInterface()) {
            implClass = config.getImplClass(clazz);
            log.debug("Implementation class for interface {}: {}", clazz.getName(), implClass.getName());
        }
        if (singletonMap.containsKey(implClass)) {
            log.debug("Singleton instance of type {} found in singletonMap, returning the singleton instance", implClass.getName());
            return castToType(singletonMap.get(implClass), clazz);
        }
        log.info("Creating new instance of type: {}", implClass.getName());
        final var res = objectFactory.create(implClass);
        if (implClass.getAnnotation(TypeToInject.class).singleton()) {
            log.info("Type {} is a singleton, adding to singletonMap", implClass.getName());
            singletonMap.put(implClass, res);
        }
        log.debug("Returning instance of type: {}", implClass.getName());
        return res;
    }


    @Override
    public List<BeanProcessor> getBeanProcessors() {
        return config.getBeanProcessors();
    }

}

