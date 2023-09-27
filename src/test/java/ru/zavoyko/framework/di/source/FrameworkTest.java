package ru.zavoyko.framework.di.source;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.zavoyko.framework.di.source.data.Writer;
import ru.zavoyko.framework.di.source.data.Starter;
import ru.zavoyko.framework.di.source.data.impl.StarterImpl;
import ru.zavoyko.framework.di.source.data.impl.WriterImpl;

@Slf4j
class FrameworkTest {

    private static final Writer LOGGER;
    private static final Starter STARTER;

    static {
        LOGGER = new WriterImpl();
        STARTER = new StarterImpl();
    }

    @Test
    void testEntryPoint() {
        LOGGER.log("Test msg");
        STARTER.start();
    }

}
