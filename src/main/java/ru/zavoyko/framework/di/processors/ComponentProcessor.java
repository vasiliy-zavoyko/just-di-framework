package ru.zavoyko.framework.di.processors;

import lombok.SneakyThrows;
import ru.zavoyko.framework.di.context.impl.BasicContext;
import ru.zavoyko.framework.di.source.Definition;

public interface ComponentProcessor {

    void process(BasicContext context, Object component);

}
