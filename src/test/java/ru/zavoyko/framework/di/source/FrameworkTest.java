package ru.zavoyko.framework.di.source;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.zavoyko.framework.di.source.data.Logger;
import ru.zavoyko.framework.di.source.data.Starter;
import ru.zavoyko.framework.di.source.data.impl.StarterRunner;
import ru.zavoyko.framework.di.source.data.impl.TestLogger;

@Slf4j
public class FrameworkTest {

    private static final Logger LOGGER = new TestLogger();
    private static final Starter STARTER = new StarterRunner();

    @Test
    void testEntryPoint() {
        LOGGER.log("Test msg");
        STARTER.start();
    }

}
