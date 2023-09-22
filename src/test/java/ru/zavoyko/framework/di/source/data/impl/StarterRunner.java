package ru.zavoyko.framework.di.source.data.impl;

import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.source.data.Starter;

@Slf4j
public class StarterRunner implements Starter {

    @Override
    public void start() {
        log.info("Started...");
    }

}
