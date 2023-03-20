package ru.zavoyko.framework.di.dataset.impl;

import ru.zavoyko.framework.di.MainTest;
import ru.zavoyko.framework.di.dataset.Logger;
import ru.zavoyko.framework.di.dataset.Validator;
import ru.zavoyko.framework.di.factory.ComponentFactory;

public class TestLoggerImpl implements Logger {

    private final Validator validator = ComponentFactory.getInstance(MainTest.factoryMap).createComponent(Validator.class);

    @Override
    public void log(String message) {
        System.out.println("Test logger: " + message);
        validator.validate();
    }

}
