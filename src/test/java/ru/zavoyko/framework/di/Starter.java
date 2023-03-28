package ru.zavoyko.framework.di;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.dataset.JavaDeveloper;
import ru.zavoyko.framework.di.dataset.JavaScriptDeveloper;
import ru.zavoyko.framework.di.dataset.ProjectManager;

public class Starter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Starter.class);

    private final Developer javaDeveloper;
    private final Developer javaScriptDeveloper;
    private final Manager projectManager;

    public Starter() {
        LOGGER.debug("Starter object created, class: {}", this.getClass().getName());
        javaDeveloper = new JavaDeveloper();
        javaScriptDeveloper = new JavaScriptDeveloper();
        projectManager = new ProjectManager();
    }

    public void start() {
        LOGGER.info("Start");
        projectManager.talk();
        javaDeveloper.writeCode();
        javaScriptDeveloper.writeCode();
    }

}
