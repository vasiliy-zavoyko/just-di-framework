package ru.zavoyko.framework.di;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.dataset.JavaDeveloper;
import ru.zavoyko.framework.di.dataset.JavaScriptDeveloper;
import ru.zavoyko.framework.di.functions.TimeMeter;
import ru.zavoyko.framework.di.inject.InjectByType;
import ru.zavoyko.framework.di.inject.TypeToInject;


@TypeToInject
public class Starter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Starter.class);

    @InjectByType(value = JavaDeveloper.class)
    private Developer javaDeveloper;
    @InjectByType(value = JavaScriptDeveloper.class)
    private Developer javaScriptDeveloper;
    @InjectByType
    private Manager projectManager;

    public Starter() {
        LOGGER.debug("Starter object created, class: {}", this.getClass().getName());
    }

    @TimeMeter
    public void start() {
        LOGGER.info("Start");
        projectManager.talk();
        javaDeveloper.writeCode();
        javaScriptDeveloper.writeCode();
    }

}
