package ru.zavoyko.framework.di.dataset.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.zavoyko.framework.di.inject.InjectProperty;
import ru.zavoyko.framework.di.inject.java.TypeToInject;

@TypeToInject
public class ExtValidatorImpl extends ValidatorImpl {

    private final static Logger logger = LoggerFactory.getLogger(ExtValidatorImpl.class);

    @InjectProperty
    private String property;

    public ExtValidatorImpl() {
        super();
        logger.debug("ExtValidator is created");
    }

    @Override
    public void validate() {
        logger.info("ExtValidator is validating, property: " + property);
        super.validate();
    }

}
