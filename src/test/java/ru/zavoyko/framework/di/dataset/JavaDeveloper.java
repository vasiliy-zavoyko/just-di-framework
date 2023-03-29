package ru.zavoyko.framework.di.dataset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.Developer;
import ru.zavoyko.framework.di.inject.TypeToInject;
import ru.zavoyko.framework.di.popertis.InjectFromProperty;

@TypeToInject
public class JavaDeveloper implements Developer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaDeveloper.class);

    @InjectFromProperty
    private String name;

    public JavaDeveloper() {
        LOGGER.debug("Java developer object created, class: {}", this.getClass().getName());
    }

    @Override
    public void writeCode() {
        LOGGER.info("{} developer writes code", name);
    }

}
