package ru.zavoyko.framework.di.source.data.impl;

import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.anotations.InjectProperty;
import ru.zavoyko.framework.di.anotations.InjectType;
import ru.zavoyko.framework.di.anotations.TypeToInject;
import ru.zavoyko.framework.di.source.data.Starter;
import ru.zavoyko.framework.di.source.data.Writer;

@Slf4j
@TypeToInject
public class StarterImpl implements Starter {

    @InjectProperty(name = "prop")
    private String prop;
    @InjectProperty
    private String property;
    @InjectType(type = "ru.zavoyko.framework.di.source.data.WriterImpl")
    private Writer writer;
    @InjectType
    private DeadWriterImpl deadWriter;

    @Override
    public void start() {
        writer.log(property);
        deadWriter.log(prop);
    }

}
