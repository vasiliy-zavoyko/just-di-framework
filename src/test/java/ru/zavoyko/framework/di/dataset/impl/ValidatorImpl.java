package ru.zavoyko.framework.di.dataset.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.dataset.Validator;
import ru.zavoyko.framework.di.inject.InjectProperty;
import ru.zavoyko.framework.di.inject.java.TypeToInject;

@TypeToInject
public class ValidatorImpl implements Validator {

    private final static Logger logger = LoggerFactory.getLogger(ValidatorImpl.class);

    public ValidatorImpl() {
        logger.debug("Validator is created");
    }

    @InjectProperty("message")
    private String value;

    @Override
    public void validate() {
        logger.info("Validator is validating, property: " + value);
    }

}
