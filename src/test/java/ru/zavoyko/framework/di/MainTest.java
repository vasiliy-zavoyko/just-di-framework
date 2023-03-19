package ru.zavoyko.framework.di;

import ru.zavoyko.framework.di.dataset.Logger;
import ru.zavoyko.framework.di.dataset.Runner;
import ru.zavoyko.framework.di.factory.ComponentFactory;

class MainTest {

    private static final Logger logger = ComponentFactory.getInstance().createComponent(Logger.class);
    private static final Runner runner = ComponentFactory.getInstance().createComponent(Runner.class);

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