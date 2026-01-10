package br.com.food_connect.Food_Connect.controller;

import br.com.food_connect.Food_Connect.model.dto.UserPutRequestDTO;
import jakarta.validation.Valid;
import br.com.food_connect.Food_Connect.model.dto.ApiResponse;
import br.com.food_connect.Food_Connect.model.dto.UserRequestDTO;
import br.com.food_connect.Food_Connect.model.dto.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.food_connect.Food_Connect.service.UsersService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UsersService usersService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> listUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(usersService.findAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(usersService.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(
            @RequestBody @Valid UserRequestDTO request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usersService.create(request));
    }

    @PutMapping("/{id}")
    public UserResponseDTO update(
            @PathVariable Long id,
            @RequestBody @Valid UserPutRequestDTO request
    ) {
        return this.usersService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {
        return ResponseEntity.ok(usersService.delete(id));
    }
}
