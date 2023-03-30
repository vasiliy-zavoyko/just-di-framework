package ru.zavoyko.framework.di.processors.actions;

import ru.zavoyko.framework.di.context.Context;

public interface ActionsProcessor {

    Object applyAction(Context context, Object component);

}
