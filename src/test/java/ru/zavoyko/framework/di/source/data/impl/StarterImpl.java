package ru.zavoyko.framework.di.source.data.impl;

import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.anotations.Autowired;
import ru.zavoyko.framework.di.anotations.InjectProperty;
import ru.zavoyko.framework.di.anotations.TimeToRun;
import ru.zavoyko.framework.di.source.data.Starter;
import ru.zavoyko.framework.di.source.data.Writer;

@Slf4j
public class StarterImpl implements Starter {

    public StarterImpl() {
        log.info(StarterImpl.class.getName() + " created");
    }

    @Autowired(value = DeadWriterImpl.class)
    private Writer writer;

    @InjectProperty("prop")
    private String prop;
    @InjectProperty
    private String property;

    @Override
    @TimeToRun
    public void start() {
        writer.log(property);
        writer.log(prop);
    }

}
