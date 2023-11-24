package ru.zavoyko.framework.di;

import ru.zavoyko.framework.di.impl.ConfigImpl;
import ru.zavoyko.framework.di.impl.ContextImpl;
import ru.zavoyko.framework.di.impl.ObjectFactoryImpl;

public class DIF {

    public static Context prepareFramework(String pkg) {
        final var config = ConfigImpl.config(pkg);
        final var context = ContextImpl.getContext(config);
        final var factory = ObjectFactoryImpl.getObjectFactory(config, context);
        context.setObjectFactory(factory);
        return context;
    }

}
