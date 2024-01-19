package ru.zavoyko.framework.di.source.data.impl;

import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.annotations.InjectByType;
import ru.zavoyko.framework.di.annotations.TypeToInject;
import ru.zavoyko.framework.di.annotations.Value;
import ru.zavoyko.framework.di.source.data.Starter;
import ru.zavoyko.framework.di.source.data.Writer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@TypeToInject
public class StarterImpl implements Starter {

    @Value(keyToFind = "nameToSet")
    private String nameToFind;
    private String initString;
    @InjectByType(classValue = WriterImpl.class)
    private Writer writer;
    @InjectByType(classValue = BadWriterImpl.class)
    private Writer badWriter;

    @PostConstruct
    public void init() {
        initString = " Im the first, Yah man!!";
    }

    @PreDestroy
    public void byebye() {
        log.info("am out, HA ha");
    }

    @Override
    public void start() {
        log.info(initString);
        log.info("Started...");
        badWriter.log("i'm baddddd");
        writer.log(nameToFind);
    }

}
