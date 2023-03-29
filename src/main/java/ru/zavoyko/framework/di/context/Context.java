package ru.zavoyko.framework.di.context;

import ru.zavoyko.framework.di.factory.ObjectFactory;

import java.util.HashMap;
import java.util.Map;

public class Context {

    private ObjectFactory objectFactory;

    private Map<Class<?>, Object> singleton = new HashMap<>();

    public <T> T getComponent(Class<T> type) {
        if (singleton.containsKey(type)) {
            return (T) singleton.get(type);
        }
        final var object = objectFactory.createObject(type);
        singleton.put(type, object);
        return (T) object;
    }

    public void setObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

}
