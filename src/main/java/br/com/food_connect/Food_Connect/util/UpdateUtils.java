package br.com.food_connect.Food_Connect.util;

import java.util.function.Consumer;

public class UpdateUtils {

    public static void updateIfPresent(String value, Consumer<String> setter) {
        if (value != null && !value.isBlank()) {
            setter.accept(value.trim());
        }
    }

    public static <T> void updateIfPresent(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
