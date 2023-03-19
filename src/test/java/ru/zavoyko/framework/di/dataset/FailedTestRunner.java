package ru.zavoyko.framework.di.dataset;

public class FailedTestRunner implements Runner {

    @Override
    public void run() {
        System.out.println("Failed test runner is running");
    }

}
