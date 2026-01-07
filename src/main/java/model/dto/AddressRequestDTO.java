package model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressRequestDTO(
        @NotBlank @NotNull(message = "O nome da rua não pode ser nulo.")
        String street,
        String neighborhood,
        String city,
        String state,
        String zipCode,
        String complement,
        @NotBlank @NotNull(message = "O ID do usuário não pode ser nulo.")
        Long user_id
) {
}
