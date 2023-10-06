package ru.zavoyko.framework.di.impl;

import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.*;
import ru.zavoyko.framework.di.anotations.TypeToInject;
import ru.zavoyko.framework.di.exception.DIFException;
import ru.zavoyko.framework.di.utils.DIFObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static ru.zavoyko.framework.di.utils.DIFObjectUtils.*;

@Slf4j
public class ContextImpl implements Context {

    public static ContextImpl getInstance(Config configToSet) {
        return new ContextImpl(checkNonNullOrThrowException(configToSet, "Config can't be null"));
    }

    private Config config;
    private ObjectFactory objectFactory;
    private Map<String, Object> singletonMap;

    private ContextImpl(Config configToSet) {
        this.config = configToSet;
        this.singletonMap = new ConcurrentHashMap<>();
    }

    public void setObjectFactory(ObjectFactory objectFactoryToSet) {
        this.objectFactory = checkNonNullOrThrowException(objectFactoryToSet, "Object factory can't be null");
    }

    @Override
    public Object getBean(final String clazz) {
        {
            log.debug("Requesting bean of type: {}", clazz);
            if (isBlank(clazz)) {
                throw new DIFException("Type can't be null");
            }
            final var singleton = checkSingleton(clazz);
            if (singleton.isPresent()) {
                log.debug("Returning instance of type: {}", clazz);
                return singleton.get();
            }
        }

        final var definition = config.getDefinition(clazz);

        {
            final var singleton = checkSingleton(definition.name());
            if (singleton.isPresent()) {
                log.debug("Returning instance of type: {}", clazz);
                return singleton.get();
            }
        }

        final Class<?> implClass = loadClassByName(definition.name());
        log.info("Creating new instance of type: {}", implClass.getName());
        final var res = objectFactory.create(implClass);
        if (implClass.getAnnotation(TypeToInject.class).singleton()) {
            log.info("Type {} is a singleton, adding to singletonMap", definition.name());
            singletonMap.put(definition.name(), res);
        }

        log.debug("Returning instance of type: {}", definition.name());
        return res;
    }


    @Override
    public List<BeanProcessor> getBeanProcessors() {
        return config.getBeanProcessors();
    }

    private Optional<Object> checkSingleton(String name) {
        if (singletonMap.containsKey(name)) {
            log.info("Singleton " + name + " found");
            return Optional.of(singletonMap.get(name));
        }
        return Optional.empty();
    }

}
