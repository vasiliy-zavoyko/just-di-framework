package ru.zavoyko.framework.di.processor;

import ru.zavoyko.framework.di.Context;

public interface Processor {

    void process(Object object, Context context);

}
