package br.com.food_connect.Food_Connect.model.dto.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AddressRequestDTO(
        @NotBlank @NotNull(message = "O nome da rua não pode ser nulo.")
        String street,
        String neighborhood,
        String city,
        String state,
        String zipCode,
        String complement,
        @NotNull(message = "O ID do usuário não pode ser nulo.")
        @Positive(message = "O ID do usuário deve ser positivo.")
        Long user_id
) {
}
