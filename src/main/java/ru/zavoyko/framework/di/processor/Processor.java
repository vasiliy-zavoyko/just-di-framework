package ru.zavoyko.framework.di.processor;

import ru.zavoyko.framework.di.context.Context;

public interface Processor {

    void process(Object object, Context context);

}
