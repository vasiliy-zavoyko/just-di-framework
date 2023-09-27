package ru.zavoyko.framework.di.source.data.impl;

import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.source.data.Starter;

@Slf4j
public class StarterImpl implements Starter {

    private final WriterImpl logger;

    public StarterImpl(WriterImpl loggerToSet) {
        this.logger = loggerToSet;
    }

    @Override
    public void start() {
        logger.log("Started...");
    }

}
