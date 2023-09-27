package ru.zavoyko.framework.di.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.zavoyko.framework.di.exception.DIFException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DIFObjectUtils {

    <T> T checkNonNullOrThrowException(T t) {
        return checkNonNullOrThrowException(t, "Object is null");
    }

    <T> T checkNonNullOrThrowException(T t, String msg) {
        if (t == null) {
            throw new DIFException(msg, new NullPointerException());
        }
        return t;
    }

}
