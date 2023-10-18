package ru.zavoyko.framework.di.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.Config;
import ru.zavoyko.framework.di.Context;
import ru.zavoyko.framework.di.ObjectFactory;
import ru.zavoyko.framework.di.Processor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Slf4j
public class ContextImpl implements Context {

    private final Config config;
    private final ObjectFactory objectFactory;

    private final Map<Class, Object> singletonMap = new ConcurrentHashMap<>();


    @Override
    public <T> T getBean(final Class<T> aClass) {
        var implClass = config.getImplClass(aClass);

        if (singletonMap.containsKey(aClass)) {
            final var bean = singletonMap.get(aClass);
            log.debug("Singleton found: " + aClass);
            return aClass.cast(bean);
        } else {
            final var bean = objectFactory.getBean(implClass);
            singletonMap.put(aClass, bean);
            return aClass.cast(bean);
        }
    }

    @Override
    public List<Processor> getProcessorList() {
        return config.getProcessors();
    }

}
