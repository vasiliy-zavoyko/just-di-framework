package ru.zavoyko.framework.di.source.data.impl;

import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.annotations.InjectByType;
import ru.zavoyko.framework.di.annotations.TypeToInject;
import ru.zavoyko.framework.di.annotations.Value;
import ru.zavoyko.framework.di.source.data.Writer;

@Slf4j
@TypeToInject
public class WriterImpl implements Writer {

    @Value
    private String justField;

    @InjectByType(classValue = BadWriterImpl.class)
    private Writer writer;

    @Override
    public void log(String msg) {
        log.info("Logged: " + msg);
//        log.info(justField);
        writer.log(justField);
    }

}
