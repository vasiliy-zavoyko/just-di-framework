package ru.zavoyko.framework.di.dataset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.Analyst;
import ru.zavoyko.framework.di.Manager;
import ru.zavoyko.framework.di.inject.InjectByType;
import ru.zavoyko.framework.di.inject.TypeToInject;

@TypeToInject
public class ProjectManager implements Manager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectManager.class);

    @InjectByType
    private Analyst businessAnalyst;

    public ProjectManager() {
        LOGGER.debug("Project manager object created, class: {}", this.getClass().getName());
    }


    @Override
    public void talk() {
        LOGGER.info("Project manager talks");
        businessAnalyst.writeReport();
    }

}
