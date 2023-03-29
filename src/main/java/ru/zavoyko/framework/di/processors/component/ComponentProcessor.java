package ru.zavoyko.framework.di.processors.component;

import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.factory.ObjectFactory;

public interface ComponentProcessor {

    void process(Context context, Object component);

}
