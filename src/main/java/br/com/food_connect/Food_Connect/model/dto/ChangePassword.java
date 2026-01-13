package br.com.food_connect.Food_Connect.model.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangePassword(
        @NotBlank String email,
        @NotBlank String oldPassword,
        @NotBlank String newPassword
) {}
