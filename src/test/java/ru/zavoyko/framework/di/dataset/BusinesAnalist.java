package ru.zavoyko.framework.di.dataset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.Analyst;

public class BusinesAnalist implements Analyst {

    private static final Logger LOGGER = LoggerFactory.getLogger(BusinesAnalist.class);

    public BusinesAnalist() {
        LOGGER.debug("Business analyst object created, class: {}", this.getClass().getName());
    }

    @Override
    public void writeReport() {
        LOGGER.info("Business analyst writes report");
    }

}
