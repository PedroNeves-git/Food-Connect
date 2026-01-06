package model.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangePassword(
        @NotBlank String email,
        @NotBlank String oldPassword,
        @NotBlank String newPassword
) {}
