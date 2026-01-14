package br.com.food_connect.Food_Connect.controller;

import br.com.food_connect.Food_Connect.model.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import br.com.food_connect.Food_Connect.service.UsersService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UsersService usersService;

    @Operation(summary = "List all users with pagination")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(usersService.findAll(page, size));
    }

    @Operation(summary = "Find user by ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(usersService.findById(id));
    }

    @Operation(summary = "Register a new user")
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(
            @RequestBody @Valid UserRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usersService.create(request));
    }

    @Operation(summary = "Update user data")
    @PutMapping("/{id}")
    public UserResponseDTO update(
            @PathVariable Long id,
            @RequestBody @Valid UserPutRequestDTO request
    ) {
        return this.usersService.update(id, request);
    }

    @Operation(summary = "Change user password")
    @PutMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            @RequestBody @Valid ChangePasswordDTO request,
            Authentication authentication
    ) {
        String login = authentication.getName(); // vem do JWT (subject)
        usersService.changePassword(login, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete a user")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return ResponseEntity.ok(usersService.delete(id));
    }
}
