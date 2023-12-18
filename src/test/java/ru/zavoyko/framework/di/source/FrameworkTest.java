package ru.zavoyko.framework.di.source;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.zavoyko.framework.di.Configuration;
import ru.zavoyko.framework.di.Context;
import ru.zavoyko.framework.di.ContextImpl;
import ru.zavoyko.framework.di.ObjectFactory;
import ru.zavoyko.framework.di.ObjectFactoryImpl;
import ru.zavoyko.framework.di.source.data.Starter;
import ru.zavoyko.framework.di.source.data.Writer;
import ru.zavoyko.framework.di.source.data.impl.BadStarterImpl;
import ru.zavoyko.framework.di.source.data.impl.StarterImpl;
import ru.zavoyko.framework.di.source.data.impl.WriterImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
class FrameworkTest {

    @Test
    void testEntryPoint() {
        final var context = ContextImpl.createContext(List.of("ru.zavoyko.framework"));
        final var bean = context.getBean(StarterImpl.class);
        bean.get().start();
    }

}
