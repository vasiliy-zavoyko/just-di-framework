package ru.zavoyko.framework.di.source;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.zavoyko.framework.di.ObjectFactory;
import ru.zavoyko.framework.di.source.data.Starter;
import ru.zavoyko.framework.di.source.data.Writer;
import ru.zavoyko.framework.di.source.data.impl.WriterImpl;

import java.util.Map;

import static ru.zavoyko.framework.di.impl.ObjectFactoryImpl.getObjectFactory;

@Slf4j
class FrameworkTest {

    private static final Map<Class, Class> CLASS_CLASS_MAP = Map.of(Writer.class, WriterImpl.class);
    private static final ObjectFactory OBJECT_FACTORY = getObjectFactory("ru.zavoyko.framework.di", CLASS_CLASS_MAP);
    private static final Writer LOGGER = OBJECT_FACTORY.create(Writer.class);
    private static final Starter STARTER = OBJECT_FACTORY.create(Starter.class);

    @Test
    void testEntryPoint() {
        LOGGER.log("Test msg");
        STARTER.start();
    }

}
