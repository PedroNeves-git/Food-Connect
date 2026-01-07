package model.dto;

import model.Address;

public record AddressResponseDTO(
        Long id,
        String street,
        String neighborhood,
        String city,
        String state,
        String zipCode,
        String complement
) {
    public AddressResponseDTO(Address address) {
        this(
                address.getId(),
                address.getStreet(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getZipCode(),
                address.getComplement()
        );
    }
}

