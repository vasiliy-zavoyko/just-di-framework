package ru.zavoyko.framework.di.dataset.impl;

import ru.zavoyko.framework.di.dataset.Logger;
import ru.zavoyko.framework.di.dataset.Validator;
import ru.zavoyko.framework.di.inject.InjectByType;
import ru.zavoyko.framework.di.inject.java.TypeToInject;

@TypeToInject(isLazy = false, isSingleton = true)
public class TestLoggerImpl implements Logger {

    @InjectByType
    private Validator validator;

    @Override
    public void log(String message) {
        System.out.println("Test logger: " + message);
        validator.validate();
    }

}
