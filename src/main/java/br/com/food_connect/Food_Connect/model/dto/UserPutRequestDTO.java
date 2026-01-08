package br.com.food_connect.Food_Connect.model.dto;

import br.com.food_connect.Food_Connect.model.TypeUser;
import jakarta.validation.constraints.Email;

public record UserPutRequestDTO(
        String name,
        @Email String email,
        String login,
        TypeUser typeUser
) {
}


