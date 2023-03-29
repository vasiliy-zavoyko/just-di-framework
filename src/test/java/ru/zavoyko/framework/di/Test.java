package ru.zavoyko.framework.di;

import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.dataset.JavaDeveloper;
import ru.zavoyko.framework.di.factory.ObjectFactory;
import ru.zavoyko.framework.di.source.JavaObjectSource;

import java.util.Map;

public class Test {

    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);

    private Context context;
    Starter starter = null;

    @BeforeEach
    void before() {
        LOGGER.info("Before test");
        this.context = new Context();

        final var javaObjectSource = new JavaObjectSource("ru.zavoyko.framework.di");
        final var objectFactory = new ObjectFactory(context, javaObjectSource);
        context.setObjectFactory(objectFactory);
    }

    @org.junit.jupiter.api.Test
    void test() {
        LOGGER.info("Test started");
        starter = context.getComponent(Starter.class);
        final var javaDeveloper = context.getComponent(JavaDeveloper.class);
        javaDeveloper.writeCode();
        helloWorld();
        starter.start();
        LOGGER.info("Test finished");
    }

    private void helloWorld() {
        LOGGER.info("Hello world");
    }

}
