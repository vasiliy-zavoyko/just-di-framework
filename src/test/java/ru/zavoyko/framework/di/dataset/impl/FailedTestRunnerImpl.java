package ru.zavoyko.framework.di.dataset.impl;

import ru.zavoyko.framework.di.dataset.Runner;
import ru.zavoyko.framework.di.inject.java.TypeToInject;

@TypeToInject
public class FailedTestRunnerImpl implements Runner {

    @Override
    public void run() {
        System.out.println("Failed test runner is running");
    }

}
