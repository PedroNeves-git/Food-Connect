package model.dto;

public record UserResponse(
        Long id,
        String name,
        String email
) {
}
