package ru.zavoyko.framework.di.source.data.impl;

import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.annotations.InjectByType;
import ru.zavoyko.framework.di.annotations.TypeToInject;
import ru.zavoyko.framework.di.annotations.Value;
import ru.zavoyko.framework.di.source.data.Starter;
import ru.zavoyko.framework.di.source.data.Writer;

@Slf4j
@TypeToInject
public class StarterImpl implements Starter {

    @Value(keyToFind = "nameToSet")
    private String nameToFind;

    @InjectByType(classValue = WriterImpl.class)
    private Writer writer;

    @Override
    public void start() {
        log.info("Started...");
        writer.log(nameToFind);
    }

}
