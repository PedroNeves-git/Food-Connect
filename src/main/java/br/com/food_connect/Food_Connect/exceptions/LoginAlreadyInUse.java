package br.com.food_connect.Food_Connect.exceptions;

public class LoginAlreadyInUse extends RuntimeException {
    public LoginAlreadyInUse(String message) {
        super(message);
    }
}
