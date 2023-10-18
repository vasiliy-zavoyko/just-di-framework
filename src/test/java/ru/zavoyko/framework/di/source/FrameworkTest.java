package ru.zavoyko.framework.di.source;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.zavoyko.framework.di.Context;
import ru.zavoyko.framework.di.DIFramework;
import ru.zavoyko.framework.di.ObjectFactory;
import ru.zavoyko.framework.di.impl.ObjectFactoryImpl;
import ru.zavoyko.framework.di.source.data.Executor;
import ru.zavoyko.framework.di.source.data.Writer;
import ru.zavoyko.framework.di.source.data.Starter;
import ru.zavoyko.framework.di.source.data.impl.StarterImpl;

import java.util.Map;

@Slf4j
class FrameworkTest {

    @Test
    void testEntryPoint() {
        final var framework = DIFramework.getDIFramework("ru.zavoyko.framework.di");
        final var executor = framework.getBean(Executor.class);
        executor.exec();
    }

}
