package ru.zavoyko.framework.di.dataset.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.zavoyko.framework.di.dataset.Printer;
import ru.zavoyko.framework.di.dataset.Validator;
import ru.zavoyko.framework.di.inject.InjectByType;
import ru.zavoyko.framework.di.inject.java.TypeToInject;

@TypeToInject(isLazy = false, isSingleton = true)
public class TestPrinterImpl implements Printer {

    private final static Logger logger = LogManager.getLogger(TestPrinterImpl.class);

    public TestPrinterImpl() {
        logger.debug("Test printer is created");
    }

    @InjectByType
    private Validator validator;

    @Override
    public void log(String message) {
        logger.info("Test logger: " + message);
        validator.validate();
    }

}
