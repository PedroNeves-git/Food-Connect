package br.com.food_connect.Food_Connect.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import br.com.food_connect.Food_Connect.model.TypeUser;

public record UserRequestDTO(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotBlank @NotNull String login,
        @NotBlank  String password,
        @NotNull TypeUser typeUser
) {}
