package ru.zavoyko.framework.di.processors;

import ru.zavoyko.framework.di.context.Context;

public interface ComponentProcessor {

    void process(Context context, Object component);

}
