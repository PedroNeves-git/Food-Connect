package br.com.food_connect.Food_Connect.controller;

import br.com.food_connect.Food_Connect.exceptions.AddressNotFoundException;
import br.com.food_connect.Food_Connect.exceptions.EmailAlreadyInUse;
import br.com.food_connect.Food_Connect.exceptions.UserNotFoundException;
import br.com.food_connect.Food_Connect.model.dto.ValidationErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException e)
    {
        var status = HttpStatus.BAD_REQUEST;
        List<String> errors = new ArrayList<String>();
        for (var error : e.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        return ResponseEntity.status(status.value()).body(new ValidationErrorDTO(errors, status.value()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(
            UserNotFoundException e
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND) // 404
                .body(Map.of(
                        "status", 404,
                        "error", "User not found.",
                        "message", e.getMessage()
                ));
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<?> handleAddressNotFound(
            AddressNotFoundException e
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "status", 404,
                        "error", "Address not found.",
                        "message", e.getMessage()
                ));
    }

    @ExceptionHandler(EmailAlreadyInUse.class)
    public ResponseEntity<?> handleAddressNotFound(
            EmailAlreadyInUse e
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "status", 404,
                        "error", "Email already in use.",
                        "message", e.getMessage()
                ));
    }
}
