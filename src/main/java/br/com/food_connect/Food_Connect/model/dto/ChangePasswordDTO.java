package br.com.food_connect.Food_Connect.model.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordDTO(@NotBlank String currentPassword,
                                @NotBlank String newPassword)
{}
