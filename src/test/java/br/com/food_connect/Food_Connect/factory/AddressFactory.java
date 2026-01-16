package br.com.food_connect.Food_Connect.factory;

import br.com.food_connect.Food_Connect.model.Address;
import br.com.food_connect.Food_Connect.model.dto.AddressPutRequestDTO;
import br.com.food_connect.Food_Connect.model.dto.AddressRequestDTO;
import br.com.food_connect.Food_Connect.model.dto.AddressResponseDTO;
import br.com.food_connect.Food_Connect.model.dto.UserResponseDTO;

public class AddressFactory {
    public static Address create() {
        return new Address(
                1L,
                "123 Main St",
                "Downtown",
                "Metropolis",
                "State",
                "12345-678",
                "Apt 101",
                UserFactory.createUser()
        );
    }

    public static AddressRequestDTO createRequest() {
        return new AddressRequestDTO(
                "123 Main St",
                "Downtown",
                "Metropolis",
                "State",
                "12345-678",
                "Apt 101",
                1L
        );
    }

    public static AddressPutRequestDTO createPutRequest() {
        return new AddressPutRequestDTO(
                "456 Elm St",
                "Uptown",
                "Gotham",
                "New State",
                "87654-321",
                "Suite 202"
        );
    }

    public static AddressResponseDTO createResponse() {
        return new AddressResponseDTO(
                1L,
                "123 Main St",
                "Downtown",
                "Metropolis",
                "State",
                "12345-678",
                "Apt 101",
                1L
        );
    }

}
