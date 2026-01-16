package br.com.food_connect.Food_Connect.factory;


import br.com.food_connect.Food_Connect.model.dto.login.LoginDTO;

public class LoginFactory {
    public static LoginDTO create() {
        return new LoginDTO("testuser", "password123");
    }
}
