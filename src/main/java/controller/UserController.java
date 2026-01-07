package controller;

import jakarta.validation.Valid;
import model.dto.ApiResponse;
import model.dto.UserRequest;
import model.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.UsersService;

import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UsersService usersService;

    @GetMapping
    public Page<UserResponse> listUsers(Pageable pageable) {
        return usersService.findAll(pageable)
                .map(user -> new UserResponse(
                        user.id(),
                        user.name(),
                        user.email()
                ));
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return usersService.findById(id);
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(
            @RequestBody @Valid UserRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usersService.create(request));
    }

    @PutMapping("/{id}")
    public UserResponse update(
            @PathVariable Long id,
            @RequestBody @Valid UserRequest request
    ) {
        return usersService.update(id,request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id) {

        return ResponseEntity.ok(usersService.delete(id));

    }


}
