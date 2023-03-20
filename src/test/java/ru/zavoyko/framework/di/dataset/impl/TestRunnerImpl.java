package ru.zavoyko.framework.di.dataset.impl;

import ru.zavoyko.framework.di.dataset.Runner;
import ru.zavoyko.framework.di.dataset.Validator;
import ru.zavoyko.framework.di.inject.InjectProperty;

public class TestRunnerImpl implements Runner {

    @InjectProperty
    private String name;

    @Override
    public void run() {
        System.out.println("Test runner is running -> " + name);
    }

}
