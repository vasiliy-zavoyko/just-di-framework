package ru.zavoyko.framework.di.dataset;

public class TestLogger implements Logger {

    @Override
    public void log(String message) {
        System.out.println("Test logger: " + message);
    }

}
