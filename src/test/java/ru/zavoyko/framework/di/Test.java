package ru.zavoyko.framework.di;

import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);

    Starter starter = null;

    @BeforeEach
    void before() {
        LOGGER.info("Before test");
        starter = new Starter();
    }

    @org.junit.jupiter.api.Test
    void test() {
        LOGGER.info("Test started");
        helloWorld();
        starter.start();
        LOGGER.info("Test finished");
    }

    private void helloWorld() {
        LOGGER.info("Hello world");
    }

}
