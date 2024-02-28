package ru.zavoyko.framework.di.source.data.impl;

import org.slf4j.Logger;
import ru.zavoyko.framework.di.annotations.TimeToRun;
import ru.zavoyko.framework.di.annotations.TypeToInject;
import ru.zavoyko.framework.di.annotations.Value;
import ru.zavoyko.framework.di.source.data.Writer;

@TypeToInject(isSingleton = false)
public class BadWriterImpl implements Writer {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BadWriterImpl.class);
    @Value(keyToFind = "kool")
    private String cool;

//    @InjectByType(classValue = BadWriterImpl.class)
//    private BadWriterImpl nameToFind;

    @Override
    @TimeToRun
    public void log(String msg) {
        log.info(msg);
        log.info(cool);
    }

    public String getKool() {
        log("getKool");

        return cool;
    }
}
