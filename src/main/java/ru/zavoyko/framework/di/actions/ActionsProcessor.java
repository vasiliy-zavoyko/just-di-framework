package ru.zavoyko.framework.di.actions;

import ru.zavoyko.framework.di.context.Context;
import ru.zavoyko.framework.di.context.impl.BasicContext;

public interface ActionsProcessor {

    Object applyAction(Context context, Object component);

}
