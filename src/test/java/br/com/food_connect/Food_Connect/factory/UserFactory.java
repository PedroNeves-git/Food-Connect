package br.com.food_connect.Food_Connect.factory;

import br.com.food_connect.Food_Connect.model.TypeUser;
import br.com.food_connect.Food_Connect.model.User;
import br.com.food_connect.Food_Connect.model.dto.user.UserPutRequestDTO;
import br.com.food_connect.Food_Connect.model.dto.user.UserRequestDTO;
import br.com.food_connect.Food_Connect.model.dto.user.UserResponseDTO;

import static java.time.LocalDateTime.now;

public class UserFactory {

    public static User createUser(){
        return new User(1l,
                "name",
                "email",
                "login",
                "password",
                now(),
                TypeUser.CLIENT);
    }

    public static UserRequestDTO createUserRequest(){
        return new UserRequestDTO ("name",
                "email",
                "login",
                "password",
                TypeUser.CLIENT);
    }

    public static UserPutRequestDTO createUserPutRequest(String email){
        return new UserPutRequestDTO ("name",
                email,
                "login",
                TypeUser.CLIENT);
    }

    public static UserResponseDTO createUserResponse() {
        return new UserResponseDTO(1l,
                "name",
                "email");

    }
}
