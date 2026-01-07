package model.dto;

import jakarta.validation.constraints.NotNull;

public record AddressPutRequestDTO(
        @NotNull(message = "O ID não pode ser nulo")
        Long id,
        @NotNull(message = "O nome da rua não pode ser nulo.")
        String street,
        String neighborhood,
        String city,
        String state,
        String zipCode,
        String complement
) {
}
