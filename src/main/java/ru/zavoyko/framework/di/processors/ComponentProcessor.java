package ru.zavoyko.framework.di.processors;

import ru.zavoyko.framework.di.context.impl.BasicContext;

public interface ComponentProcessor {

    void process(BasicContext context, Object component);

}
