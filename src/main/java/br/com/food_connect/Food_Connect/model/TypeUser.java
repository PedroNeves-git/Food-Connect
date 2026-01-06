package br.com.food_connect.Food_Connect.model;

public enum TypeUser {

    RESTAURANT_OWNER  (1),
    CLIENT (2);

    private String role;

    TypeUser(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }

    TypeUser(int id) {
    }
}