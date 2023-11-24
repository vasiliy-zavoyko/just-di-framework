package ru.zavoyko.framework.di.source;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.zavoyko.framework.di.DIF;
import ru.zavoyko.framework.di.source.data.Starter;
import ru.zavoyko.framework.di.source.data.impl.WriterImpl;

@Slf4j
class FrameworkTest {

    @Test
    void testEntryPoint() {
        final var context = DIF.prepareFramework("ru.zavoyko.framework.di");
        var logger = context.getBean(WriterImpl.class);
        var starter = context.getBean(Starter.class);
        var starter2 = context.getBean(Starter.class);
        logger.log("Test msg");
        starter.start();
        starter2.start();
        var logger2 = context.getBean(WriterImpl.class);
        logger2.log("I am writer");
        logger2.log(starter2.getClass().getName());
    }

}
