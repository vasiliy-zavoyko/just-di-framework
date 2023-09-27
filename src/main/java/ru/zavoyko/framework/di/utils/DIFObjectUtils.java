package ru.zavoyko.framework.di.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.zavoyko.framework.di.exception.DIFException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DIFObjectUtils {

    public static <T> T checkNonNullOrThrowException(T t) {
        return checkNonNullOrThrowException(t, "Object is null");
    }

    public static <T> T checkNonNullOrThrowException(T t, String msg) {
        if (t == null) {
            throw new DIFException(msg, new NullPointerException());
        }
        return t;
    }

}
