package br.com.food_connect.Food_Connect.factory;

import br.com.food_connect.Food_Connect.model.dto.ChangePasswordDTO;

public class ChangePasswordFactory {
    public static ChangePasswordDTO create() {
        return new ChangePasswordDTO("currPassword", "newPassword");
    }
}
