package ru.zavoyko.framework.di.source.data.impl;

import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.source.data.Writer;

@Slf4j
public class WriterImpl implements Writer {

    @Override
    public void log(String msg) {
        log.info("Logged: " + msg);
    }

}
