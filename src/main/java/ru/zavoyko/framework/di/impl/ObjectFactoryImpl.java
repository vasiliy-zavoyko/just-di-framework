package ru.zavoyko.framework.di.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.*;
import ru.zavoyko.framework.di.anotations.Autowired;
import ru.zavoyko.framework.di.utils.DIFObjectUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static ru.zavoyko.framework.di.utils.DIFObjectUtils.checkNonNullOrThrowException;
import static ru.zavoyko.framework.di.utils.DIFObjectUtils.instantiate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ObjectFactoryImpl implements ObjectFactory {

    private static final Lock LOCK = new ReentrantLock();
    private static ObjectFactoryImpl OBJECT_FACTORY;

    public static ObjectFactoryImpl getObjectFactory(final Config config, final Context context) {
        var ref = OBJECT_FACTORY;
        if (ref == null) {
            LOCK.lock();
            try {
                ref = OBJECT_FACTORY;
                if (ref == null) {
                    log.info("Object factory created");
                    OBJECT_FACTORY = new ObjectFactoryImpl(config, context);
                }
            } finally {
                LOCK.unlock();
            }
        }
        return OBJECT_FACTORY;
    }

    private final Config config;
    private final Context context;

    @Override
    public <T> T create(final Class<T> clazz) {
        checkNonNullOrThrowException(clazz, "Class can't be null");
        Class<? extends T> implClass = clazz;
        if (clazz.isInterface()) {
            implClass = config.getImplClass(clazz);
        }
        log.info("Requested object of type: " + implClass.getName());
        final T newInstance = instantiate(implClass);

        setFields(newInstance);
        final var proxy = setActions(newInstance);

        return clazz.cast(proxy);
    }

    private Object setActions(final Object newInstance) {
        Object proxy = newInstance;
        for (final var actionProcessor : config.getActionProcessors()) {
            proxy = actionProcessor.process(context, proxy);
        }
        return proxy;
    }

    @SneakyThrows
    private void setFields(final Object newInstance) {
        for (BeanProcessor beanProcessor : config.getBeanProcessors()) {
            beanProcessor.process(context, newInstance);
        }
    }

}
