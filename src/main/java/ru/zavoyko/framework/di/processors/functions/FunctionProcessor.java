package ru.zavoyko.framework.di.processors.functions;

import ru.zavoyko.framework.di.context.Context;

public interface FunctionProcessor {

    Object process(Context context, Object component);

}
