package ru.zavoyko.framework.di.dataset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.Manager;

public class ProjectManager implements Manager {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectManager.class);

    private final BusinesAnalist businesAnalist;

    public ProjectManager() {
        LOGGER.debug("Project manager object created, class: {}", this.getClass().getName());
        businesAnalist = new BusinesAnalist();
    }


    @Override
    public void talk() {
        LOGGER.info("Project manager talks");
        businesAnalist.writeReport();
    }

}
