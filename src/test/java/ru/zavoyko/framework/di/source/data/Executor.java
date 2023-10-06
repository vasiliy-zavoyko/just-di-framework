package ru.zavoyko.framework.di.source.data;

import ru.zavoyko.framework.di.anotations.InjectType;
import ru.zavoyko.framework.di.anotations.TypeToInject;
import ru.zavoyko.framework.di.source.data.impl.WriterImpl;

@TypeToInject(singleton = false)
public class Executor {

    @InjectType
    private Starter starter;
    @InjectType(type ="ru.zavoyko.framework.di.source.data.WriterImpl")
    private Writer writer;

    public void run(String str) {
        writer.log("Executor it running: " + str);
        starter.start();
    }

}
