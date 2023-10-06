package ru.zavoyko.framework.di.source;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.zavoyko.framework.di.DIFramework;
import ru.zavoyko.framework.di.source.data.Executor;
import ru.zavoyko.framework.di.utils.DIFObjectUtils;

import static ru.zavoyko.framework.di.utils.DIFObjectUtils.castToType;

@Slf4j
class FrameworkTest {

    @Test
    void testEntryPoint() {
        final var context = DIFramework.frameYourWork();
        var executor = castToType(context.getBean("ru.zavoyko.framework.di.source.data.Executor"), Executor.class);
        executor.run("first");
        executor = castToType(context.getBean("ru.zavoyko.framework.di.source.data.Executor"), Executor.class);
        executor.run("second");
    }

}
