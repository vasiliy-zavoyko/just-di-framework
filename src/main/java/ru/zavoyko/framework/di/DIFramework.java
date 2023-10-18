package ru.zavoyko.framework.di;

import ru.zavoyko.framework.di.impl.ContextImpl;
import ru.zavoyko.framework.di.impl.JavaConfig;
import ru.zavoyko.framework.di.impl.ObjectFactoryImpl;

public class DIFramework {

    public static JavaConfig getConfig(String packageToSet) {
        return new JavaConfig(packageToSet);
    }

    public static ContextImpl getContext(Config config, ObjectFactory objectFactory) {
        return new ContextImpl(config, objectFactory);
    }

    public static ObjectFactoryImpl geObjectFactory() {
        return new ObjectFactoryImpl();
    }

    public static Context getDIFramework(String packageToScan) {
        final var config = getConfig(packageToScan);
        final var objectFactory = geObjectFactory();
        final var context = getContext(config, objectFactory);
        objectFactory.setContext(context);
        return context;
    }

}