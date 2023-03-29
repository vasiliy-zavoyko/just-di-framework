package ru.zavoyko.framework.di.dataset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.Developer;
import ru.zavoyko.framework.di.inject.TypeToInject;

@TypeToInject
public class JavaScriptDeveloper implements Developer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaScriptDeveloper.class);

    private final String name = "JavaScript";

    public JavaScriptDeveloper() {
        LOGGER.debug("JavaScript developer object created, class: {}", this.getClass().getName());
    }

    @Override
    public void writeCode() {
        LOGGER.info("{} developer is not a developer, he changes the background color", name);
    }

}
