package br.com.food_connect.Food_Connect.model.dto.user;

import br.com.food_connect.Food_Connect.model.TypeUser;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

public record UserPutRequestDTO(
        String name,
        @Schema(example = "email@domain.com")
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "Invalid email format. Use format: user@domain.com"
        )
        String email,
        String login,
        TypeUser typeUser
) {
}

