package service;

import br.com.food_connect.Food_Connect.exception.InvalidLoginException;

public interface LoginService {
    void login(String email, String senha) throws InvalidLoginException;
}
