package br.com.food_connect.Food_Connect.rest.controller;

import br.com.food_connect.Food_Connect.exception.InvalidLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.LoginService;

import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService service;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData)throws InvalidLoginException {
        service.login(
                loginData.get("email"),
                loginData.get("senha")
        );
        return ResponseEntity.ok().build();
    }

}
