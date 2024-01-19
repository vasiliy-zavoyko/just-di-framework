package ru.zavoyko.framework.di.source;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.zavoyko.framework.di.context.impl.ContextImpl;
import ru.zavoyko.framework.di.source.data.impl.StarterImpl;

import java.io.IOException;
import java.util.List;

@Slf4j
class FrameworkTest {

    @Test
    void testEntryPoint() {
        try (final var context = ContextImpl.createContext(List.of("ru.zavoyko.framework"))) {
            final var bean = context.getBean(StarterImpl.class);
            bean.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
