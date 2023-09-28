package ru.zavoyko.framework.di.impl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.Config;
import ru.zavoyko.framework.di.ObjectFactory;
import ru.zavoyko.framework.di.anotations.InjectProperty;
import ru.zavoyko.framework.di.exception.DIFException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static ru.zavoyko.framework.di.utils.DIFObjectUtils.checkNonNullOrThrowException;
import static ru.zavoyko.framework.di.utils.DIFObjectUtils.getDeclaredFields;
import static ru.zavoyko.framework.di.utils.DIFObjectUtils.isBlank;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ObjectFactoryImpl implements ObjectFactory {

    private static final Lock LOCK = new ReentrantLock();
    private static ObjectFactoryImpl OBJECT_FACTORY;

    public static ObjectFactoryImpl getObjectFactory(String pkg, Map<Class, Class> classClassMap) {
        var ref = OBJECT_FACTORY;
        if (ref == null) {
            LOCK.lock();
            try {
                ref = OBJECT_FACTORY;
                if (ref == null) {
                    log.info("Object factory created");
                    OBJECT_FACTORY = new ObjectFactoryImpl(new ConfigImpl(pkg, classClassMap));
                }
            } finally {
                LOCK.unlock();
            }
        }
        return OBJECT_FACTORY;
    }

    private final Config config;

    @Override
    public <T> T create(final Class<T> clazz) {
        checkNonNullOrThrowException(clazz, "Class can't be null");
        try {
            Class<? extends T> implClass = clazz;
            if (clazz.isInterface()) {
                implClass = config.getImplClass(clazz);
            }
            log.info("Requested object of type: " + implClass.getName());
            final T newInstance = implClass.getDeclaredConstructor().newInstance();

            setFields(newInstance);

            return newInstance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("Error during instantiation of object type: " + clazz, e);
            throw new DIFException("Can't create object for class: " + clazz, e);
        }
    }

    @SneakyThrows
    private void setFields(final Object newInstance) {
        final Class<?> newInstanceClass = newInstance.getClass();
        final var declaredFields = getDeclaredFields(newInstanceClass);
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(InjectProperty.class)) {
                final var annotation = field.getAnnotation(InjectProperty.class);
                String propertyName;
                if (isBlank(annotation.value())) {
                    propertyName = field.getName();
                } else {
                    propertyName = annotation.value();
                }
                final var properties = ApplicationPropertiesLoader.loadProperties();
                final var value = properties.get(propertyName);

                field.setAccessible(true);
                field.set(newInstance, value);
            }
        }
    }

}
