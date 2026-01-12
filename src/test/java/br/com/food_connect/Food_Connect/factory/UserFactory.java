package br.com.food_connect.Food_Connect.factory;

import br.com.food_connect.Food_Connect.model.TypeUser;
import br.com.food_connect.Food_Connect.model.User;

import static java.time.LocalDateTime.now;

public class UserFactory {

    public static User create(){
        return new User(1l,
                "name",
                "email",
                "login",
                "password",
                now(),
                TypeUser.CLIENT);
    }
}
