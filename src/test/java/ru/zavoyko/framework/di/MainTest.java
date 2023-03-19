package ru.zavoyko.framework.di;

import ru.zavoyko.framework.di.dataset.*;
import ru.zavoyko.framework.di.factory.ComponentFactory;

import java.util.HashMap;
import java.util.Map;

class MainTest {

    private static final Map<Class, Class> factoryMap = new HashMap<>(Map.of(
            Logger.class, TestLogger.class,
            Runner.class, FailedTestRunner.class
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