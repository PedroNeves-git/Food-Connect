package br.com.food_connect.Food_Connect.exception;

public class InvalidLoginException extends Exception{
    public InvalidLoginException() {
        super("Login inválido");
    }
}
