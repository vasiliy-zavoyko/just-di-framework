package ru.zavoyko.framework.di.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import ru.zavoyko.framework.di.Config;
import ru.zavoyko.framework.di.Context;
import ru.zavoyko.framework.di.ObjectFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ContextImpl implements Context {

    public static ContextImpl getContext(Config config) {
        return new ContextImpl(config);
    }


    private final Config config;
    private ObjectFactory objectFactory;

    private Map<Class, Object> classObjectMap = new ConcurrentHashMap<>();

    public void setObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }


    @Override
    public <T> T getBean(final Class<T> clazz) {
        var implClass = clazz;
        if (implClass.isInterface()) {
            config.getImplClass(implClass);
        }

        Object result;

        if (classObjectMap.containsKey(implClass)) {
            result = classObjectMap.get(implClass);
        } else {
            result = objectFactory.create(implClass);
            classObjectMap.put(implClass, result);
        }

        return clazz.cast(result);
    }

}
