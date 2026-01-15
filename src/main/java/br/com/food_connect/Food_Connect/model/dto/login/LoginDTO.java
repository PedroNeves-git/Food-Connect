package br.com.food_connect.Food_Connect.model.dto.login;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(
        @NotBlank(message = "Login cannot be blank")
        String login,
        @NotBlank(message = "Password cannot be blank")
        String password
) {
}
