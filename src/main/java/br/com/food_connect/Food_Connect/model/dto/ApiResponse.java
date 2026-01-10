package br.com.food_connect.Food_Connect.model.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ApiResponse(
        String message,
        String timestamp
) {

    public ApiResponse(String message) {
        this(
                message,
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }
}
