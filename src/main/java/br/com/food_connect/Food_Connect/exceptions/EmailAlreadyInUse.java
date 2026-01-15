package br.com.food_connect.Food_Connect.exceptions;

public class EmailAlreadyInUse extends IllegalArgumentException {
    public EmailAlreadyInUse(String message) {
        super(message);
    }
}
