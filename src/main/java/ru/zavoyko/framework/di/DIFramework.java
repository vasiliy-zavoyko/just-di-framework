package ru.zavoyko.framework.di;

import ru.zavoyko.framework.di.impl.ConfigImpl;
import ru.zavoyko.framework.di.impl.ContextImpl;
import ru.zavoyko.framework.di.impl.ObjectFactoryImpl;

import java.util.List;

public class DIFramework {

    public static Context frameYourWork() {
        final var config = ConfigImpl.getInstance(List.of("ru.zavoyko.framework.di"));
        final var context = ContextImpl.getInstance(config);
        final var objectFactory = ObjectFactoryImpl.getObjectFactory(context);
        context.setObjectFactory(objectFactory);
        return context;
    }

}
