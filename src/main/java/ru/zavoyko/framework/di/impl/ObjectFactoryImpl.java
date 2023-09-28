package ru.zavoyko.framework.di.impl;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.Config;
import ru.zavoyko.framework.di.ObjectFactory;
import ru.zavoyko.framework.di.exception.DIFException;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static ru.zavoyko.framework.di.utils.DIFObjectUtils.checkNonNullOrThrowException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ObjectFactoryImpl implements ObjectFactory {

    private static final Lock LOCK = new ReentrantLock();
    private static final Config CONFIG = new ConfigImpl("ru.zavoyko.framework.di");
    private static ObjectFactoryImpl OBJECT_FACTORY;

    public static ObjectFactoryImpl getObjectFactory() {
        var ref = OBJECT_FACTORY;
        if (ref == null) {
            LOCK.lock();
            try {
                ref = OBJECT_FACTORY;
                if (ref == null) {
                    log.info("Object factory created");
                    OBJECT_FACTORY = new ObjectFactoryImpl();
                }
            } finally {
                LOCK.unlock();
            }
        }
        return OBJECT_FACTORY;
    }

    @Override
    public <T> T create(final Class<T> clazz) {
        checkNonNullOrThrowException(clazz, "Class can't be null");
        try {
            Class<? extends T> implClass = clazz;
            if (clazz.isInterface()) {
                implClass = CONFIG.getImplClass(clazz);
            }
            log.info("Requested object of type: " + implClass.getName());
            return implClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("Error during instantiation of object type: " + clazz, e);
            throw new DIFException("Can't create object for class: " + clazz, e);
        }
    }

}
