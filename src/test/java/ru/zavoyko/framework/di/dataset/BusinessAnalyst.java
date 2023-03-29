package ru.zavoyko.framework.di.dataset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.Analyst;
import ru.zavoyko.framework.di.inject.TypeToInject;

@TypeToInject
public class BusinessAnalyst implements Analyst {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessAnalyst.class);

    public BusinessAnalyst() {
        LOGGER.debug("Business analyst object created, class: {}", this.getClass().getName());
    }

    @Override
    public void writeReport() {
        LOGGER.info("Business analyst writes report");
    }

}
