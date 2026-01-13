package br.com.food_connect.Food_Connect.exceptions;

public class EmailAlreadyInUse extends IllegalArgumentException {
    public EmailAlreadyInUse(String email) { super("Email '" + email + "' is already in use."); }
}