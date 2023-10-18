package ru.zavoyko.framework.di.source.data.impl;

import lombok.extern.slf4j.Slf4j;
import ru.zavoyko.framework.di.Autowired;
import ru.zavoyko.framework.di.Value;
import ru.zavoyko.framework.di.source.data.Starter;
import ru.zavoyko.framework.di.source.data.Writer;

@Slf4j
public class StarterImpl implements Starter {

    @Value(name = "terminator")
    private String name;
    @Autowired
    private Writer writer;

    @Override
    public void start() {
        writer.log("I am " + name);
        writer.log("Started...");
    }

}
