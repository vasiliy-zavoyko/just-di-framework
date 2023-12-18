package ru.zavoyko.framework.di.source.data.impl;

import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.annotations.TypeToInject;
import ru.zavoyko.framework.di.annotations.Value;
import ru.zavoyko.framework.di.source.data.Writer;

@Slf4j
@TypeToInject
public class BadWriterImpl implements Writer {

    @Value(keyToFind = "kool")
    private String cool;

    @Override
    public void log(String msg) {
        log.info(msg);
        log.info(cool);
    }
}
