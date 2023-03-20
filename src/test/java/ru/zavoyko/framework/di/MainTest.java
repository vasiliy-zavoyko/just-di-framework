package ru.zavoyko.framework.di;

import ru.zavoyko.framework.di.dataset.*;
import ru.zavoyko.framework.di.dataset.impl.FailedTestRunnerImpl;
import ru.zavoyko.framework.di.dataset.impl.TestLoggerImpl;
import ru.zavoyko.framework.di.dataset.impl.TestRunnerImpl;
import ru.zavoyko.framework.di.dataset.impl.ValidatorImpl;
import ru.zavoyko.framework.di.factory.ComponentFactory;

import java.util.HashMap;
import java.util.Map;

public class MainTest {

    public static final Map<Class, Class> factoryMap = new HashMap<>(Map.of(
            Logger.class, TestLoggerImpl.class,
            Runner.class, TestRunnerImpl.class,
            Validator.class, ValidatorImpl.class
    ));
    private static final Logger logger = ComponentFactory.getInstance(factoryMap).createComponent(Logger.class);
    private static final Runner runner = ComponentFactory.getInstance(factoryMap).createComponent(Runner.class);

    @org.junit.jupiter.api.Test
    void testRun() {
        logger.log("test logger");
        testOutput();
        runner.run();
    }


    private void testOutput() {
        System.out.println("Hello world!");
    }

}