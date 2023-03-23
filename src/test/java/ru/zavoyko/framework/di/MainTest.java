package ru.zavoyko.framework.di;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.dataset.*;

public class MainTest {

    private final static Logger logger = LoggerFactory.getLogger(MainTest.class);

    @org.junit.jupiter.api.Test
    void testRun() {
        final var components = DIFramework.createForJavaComponents("ru.zavoyko.framework.di");
        final var starter = components.getComponent(Starter.class);
        testOutput();
        starter.start();
    }

    private void testOutput() {
        logger.info("Hello world!");
    }

}