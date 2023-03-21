package ru.zavoyko.framework.di;

import ru.zavoyko.framework.di.dataset.*;
import ru.zavoyko.framework.di.dataset.impl.TestRunnerImpl;

public class MainTest {

    @org.junit.jupiter.api.Test
    void testRun() {
        final var components = DIFramework.createForJavaComponents("ru.zavoyko.framework.di");
        final var logger = components.getComponent(Logger.class);
        final var runner = components.getComponent(TestRunnerImpl.class);
        logger.log("test logger");
        testOutput();
        runner.run();
    }


    private void testOutput() {
        System.out.println("Hello world!");
    }

}