package ru.zavoyko.framework.di.source.data.impl;

import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.source.data.Logger;

@Slf4j
public class TestLogger implements Logger {

    @Override
    public void log(String msg) {
        log.info("Logged: " + msg);
    }

}
