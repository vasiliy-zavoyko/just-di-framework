package ru.zavoyko.framework.di.dataset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.Developer;

public class JavaScriptDeveloper implements Developer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JavaScriptDeveloper.class);

    public JavaScriptDeveloper() {
        LOGGER.debug("JavaScript developer object created, class: {}", this.getClass().getName());
    }

    @Override
    public void writeCode() {
        LOGGER.info("JavaScript developer is not a developer, he changes the background color");
    }

}
