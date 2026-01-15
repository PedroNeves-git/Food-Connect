package br.com.food_connect.Food_Connect.model.dto.user;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordDTO(@NotBlank(message = "Current password is required")
                                String currentPassword,
                                @NotBlank(message = "New password is required")
                                String newPassword)
{}
