package ru.zavoyko.framework.di.dataset;

import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.dataset.impl.FailedTestRunnerImpl;
import ru.zavoyko.framework.di.dataset.impl.TestRunnerImpl;
import ru.zavoyko.framework.di.inject.InjectByType;
import ru.zavoyko.framework.di.inject.java.TypeToInject;

@TypeToInject
public class Starter {

    Logger logger = LoggerFactory.getLogger(Starter.class);
    public Starter() {
        logger.debug("Starter is created");
    }

    @InjectByType
    private Printer printer;

    @InjectByType
    private TestRunnerImpl testRunner;

    @InjectByType
    private FailedTestRunnerImpl failedTestRunner;

    public void start() {
        printer.log("Start");
        testRunner.run();
        testRunner.run();
        logger.info("Start");
    }

}
