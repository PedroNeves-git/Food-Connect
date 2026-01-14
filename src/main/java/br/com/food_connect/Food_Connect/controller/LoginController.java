package br.com.food_connect.Food_Connect.controller;

import br.com.food_connect.Food_Connect.model.User;
import br.com.food_connect.Food_Connect.model.dto.LoginDTO;
import br.com.food_connect.Food_Connect.model.dto.LoginResponseDTO;
import br.com.food_connect.Food_Connect.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @Operation(summary = "Authenticate user and return JWT token")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO loginDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginDTO.login(), loginDTO.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
