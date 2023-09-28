package ru.zavoyko.framework.di.source;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.zavoyko.framework.di.DIFramework;
import ru.zavoyko.framework.di.source.data.Executor;

@Slf4j
class FrameworkTest {

    @Test
    void testEntryPoint() {
        final var context = DIFramework.frameYourWork();
        var executor = context.getBean(Executor.class);
        executor.run("first");
        executor = context.getBean(Executor.class);
        executor.run("second");
    }

}
