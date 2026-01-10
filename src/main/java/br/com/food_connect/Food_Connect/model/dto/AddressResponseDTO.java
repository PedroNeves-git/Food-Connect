package br.com.food_connect.Food_Connect.model.dto;

import br.com.food_connect.Food_Connect.model.Address;

public record AddressResponseDTO(
        Long id,
        String street,
        String neighborhood,
        String city,
        String state,
        String zipCode,
        String complement,
        Long user_id
) {
    public AddressResponseDTO(Address address) {
        this(
                address.getId(),
                address.getStreet(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getZipCode(),
                address.getComplement(),
                address.getUser().getId()
        );
    }
}

