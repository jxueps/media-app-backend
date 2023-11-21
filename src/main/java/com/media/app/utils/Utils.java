package com.media.app.utils;

import org.springframework.stereotype.Component;

@Component
public class Utils {
    public static <T> T requireNonNullOrThrow(T value, String errorMessage) {
        if (value != null) {
            return value;
        } else {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static <T> T getValueOrNull(T value) {
        if (value != null) {
            return value;
        } else {
            return null;
        }
    }
}
