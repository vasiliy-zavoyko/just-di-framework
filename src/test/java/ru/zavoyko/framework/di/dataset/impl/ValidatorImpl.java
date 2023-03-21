package ru.zavoyko.framework.di.dataset.impl;

import ru.zavoyko.framework.di.dataset.Validator;
import ru.zavoyko.framework.di.inject.InjectProperty;
import ru.zavoyko.framework.di.inject.java.TypeToInject;

@TypeToInject
public class ValidatorImpl implements Validator {

    @InjectProperty("message")
    private String value;

    @Override
    public void validate() {
        System.out.println("Validator is validating: " + value);
    }

}
