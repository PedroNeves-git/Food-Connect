package model.dto;

import java.time.LocalDateTime;

public record ApiResponse(
        String message,
        LocalDateTime timestamp
) {}
