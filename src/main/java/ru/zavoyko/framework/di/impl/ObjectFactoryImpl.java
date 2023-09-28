package ru.zavoyko.framework.di.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.BeanProcessor;
import ru.zavoyko.framework.di.Context;
import ru.zavoyko.framework.di.ObjectFactory;
import ru.zavoyko.framework.di.exception.DIFException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static ru.zavoyko.framework.di.utils.DIFObjectUtils.checkNonNullOrThrowException;
import static ru.zavoyko.framework.di.utils.DIFObjectUtils.instantiate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ObjectFactoryImpl implements ObjectFactory {

    private static final Lock LOCK = new ReentrantLock();
    private static ObjectFactoryImpl OBJECT_FACTORY;

    public static ObjectFactoryImpl getObjectFactory(Context context) {
        var ref = OBJECT_FACTORY;
        if (ref == null) {
            LOCK.lock();
            try {
                ref = OBJECT_FACTORY;
                if (ref == null) {
                    log.info("Object factory created");
                    OBJECT_FACTORY = new ObjectFactoryImpl(context);
                }
            } finally {
                LOCK.unlock();
            }
        }
        return OBJECT_FACTORY;
    }

    private final Context context;

    @Override
    public <T> T create(final Class<T> clazz) {
        checkNonNullOrThrowException(clazz, "Class can't be null");
        log.debug("Creating instance of type: {}", clazz.getName());
        final T newInstance;
        try {
            newInstance = instantiate(clazz);
            log.debug("Instance of type {} successfully instantiated", clazz.getName());
        } catch (DIFException e) {
            log.error("Error instantiating object of type: {}", clazz.getName(), e);
            throw e;
        }
        try {
            log.debug("Setting fields for instance of type: {}", clazz.getName());
            setFields(newInstance);
            log.debug("Fields successfully set for instance of type: {}", clazz.getName());
        } catch (DIFException e) {
            log.error("Error setting fields for instance of type: {}", clazz.getName(), e);
            throw e;
        }
        log.info("Instance of type {} successfully created and returned", clazz.getName());
        return newInstance;
    }


    @SneakyThrows
    private void setFields(final Object newInstance) {
        log.debug("Setting fields for instance of class: {}", newInstance.getClass());
        List<BeanProcessor> beanProcessors = context.getBeanProcessors();
        log.debug("Total BeanProcessors to be applied: {}", beanProcessors.size());
        for (BeanProcessor beanProcessor : beanProcessors) {
            log.debug("Applying BeanProcessor: {} to instance of class: {}", beanProcessor.getClass(), newInstance.getClass());
            beanProcessor.process(context, newInstance);
            log.debug("BeanProcessor: {} applied successfully", beanProcessor.getClass());
        }
        log.debug("Field setting completed for instance of class: {}", newInstance.getClass());
    }

}
