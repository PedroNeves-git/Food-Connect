package service;

import jakarta.transaction.Transactional;
import model.dto.ChangePassword;
import model.entity.UserEntity;
import model.dto.ApiResponse;
import model.dto.UserRequest;
import model.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Service
@Transactional
public class UsersService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<UserResponse> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ));
    }

    public UserResponse findById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public UserResponse create(UserRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        UserEntity user = new UserEntity();
        user.setName(request.name());
        user.setEmail(request.email());

        UserEntity saved = userRepository.save(user);

        return new UserResponse(
                saved.getId(),
                saved.getName(),
                saved.getEmail()
        );
    }

    public UserResponse update(Long id, UserRequest request) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.name());
        user.setEmail(request.email());

        UserEntity updated = userRepository.save(user);

        return new UserResponse(
                updated.getId(),
                updated.getName(),
                updated.getEmail()
        );
    }

    public ApiResponse delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);

        return new ApiResponse(
                "User deleted successfully",
                LocalDateTime.now()
        );
    }

    public ApiResponse changePassword(ChangePassword request) {

        UserEntity user = userRepository.findByEmail(request.email());

        if (user == null) {
            throw new RuntimeException("Usuário não encontrado");
        }

        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            throw new RuntimeException("Senha atual inválida");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        user.setModifiedDate(LocalDateTime.now());

        userRepository.save(user);

        return new ApiResponse(
                "Senha alterada com sucesso",
                LocalDateTime.now()
        );
    }
}

