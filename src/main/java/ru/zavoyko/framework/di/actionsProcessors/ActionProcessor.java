package ru.zavoyko.framework.di.actionsProcessors;

import ru.zavoyko.framework.di.context.Context;

public interface ActionProcessor {

    Object process(Object object, Context context);

}
