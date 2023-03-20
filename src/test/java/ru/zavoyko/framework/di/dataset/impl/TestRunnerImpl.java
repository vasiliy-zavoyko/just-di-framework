package ru.zavoyko.framework.di.dataset.impl;

import ru.zavoyko.framework.di.dataset.Runner;
import ru.zavoyko.framework.di.dataset.Validator;

public class TestRunnerImpl implements Runner {

    private Validator validator;

    @Override
    public void run() {
        System.out.println("Test runner is running");
    }

}
