package br.com.food_connect.Food_Connect.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import br.com.food_connect.Food_Connect.model.TypeUser;
import jakarta.validation.constraints.Pattern;

public record UserRequestDTO(
        @NotBlank String name,
        @Schema(example = "email@domain.com")
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "Invalid email format. Use format: user@domain.com")
        String email,
        @NotBlank @NotNull String login,
        @NotBlank  String password,
        @NotNull TypeUser typeUser
) {}
