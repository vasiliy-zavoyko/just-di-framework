package ru.zavoyko.framework.di.source.data;

import ru.zavoyko.framework.di.Autowired;
import ru.zavoyko.framework.di.source.data.impl.StarterImpl;

public class Executor {

    @Autowired
    private Writer writer;
    @Autowired(value = StarterImpl.class)
    private Starter starter;

    public void exec() {
        writer.log("Im the law!");
        starter.start();
    }

}
