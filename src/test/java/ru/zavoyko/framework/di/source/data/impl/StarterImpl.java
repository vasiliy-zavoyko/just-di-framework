package ru.zavoyko.framework.di.source.data.impl;

import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.anotations.InjectProperty;
import ru.zavoyko.framework.di.source.data.Starter;

@Slf4j
public class StarterImpl implements Starter {

    @InjectProperty("prop")
    private String prop;
    @InjectProperty
    private String property;

    @Override
    public void start() {
        log.info(property);
        log.info(prop);
    }

}
