package br.com.food_connect.Food_Connect.exceptions;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(Long id) { super("Address with ID '" + id + "' not found."); }
}
