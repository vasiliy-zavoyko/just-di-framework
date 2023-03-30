package ru.zavoyko.framework.di.dataset.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.dataset.Runner;
import ru.zavoyko.framework.di.dataset.Validator;
import ru.zavoyko.framework.di.inject.InjectProperty;
import ru.zavoyko.framework.di.inject.java.TypeToInject;


@TypeToInject
public class TestRunnerImpl implements Runner {

    private final static Logger logger = LoggerFactory.getLogger(TestRunnerImpl.class);

    public TestRunnerImpl() {
        logger.debug("Test runner is created");
    }

    @InjectProperty
    private String name;

    @Override
    public void run() {
        logger.info("Test runner is running, property: " + name);
    }

}
