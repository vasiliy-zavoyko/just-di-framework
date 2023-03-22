package ru.zavoyko.framework.di.dataset.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.dataset.Runner;
import ru.zavoyko.framework.di.inject.java.TypeToInject;

@TypeToInject
public class FailedTestRunnerImpl implements Runner {

    private final static Logger logger = LoggerFactory.getLogger(FailedTestRunnerImpl.class);

    public FailedTestRunnerImpl() {
        logger.debug("Failed test runner is created");
    }

    @Override
    public void run() {
        logger.info("Failed test runner is running");
    }

}
