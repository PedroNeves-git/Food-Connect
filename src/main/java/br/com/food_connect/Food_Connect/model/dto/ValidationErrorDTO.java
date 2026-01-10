package br.com.food_connect.Food_Connect.model.dto;

import java.util.List;

public record ValidationErrorDTO(List<String> errors, int status) {
}
