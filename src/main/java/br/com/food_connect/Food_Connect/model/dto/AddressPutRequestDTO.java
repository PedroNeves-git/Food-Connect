package br.com.food_connect.Food_Connect.model.dto;

public record AddressPutRequestDTO(
        String street,
        String neighborhood,
        String city,
        String state,
        String zipCode,
        String complement
) {
}
