package br.com.food_connect.Food_Connect.service;

import br.com.food_connect.Food_Connect.exceptions.EmailAlreadyInUse;
import br.com.food_connect.Food_Connect.exceptions.InvalidPasswordException;
import br.com.food_connect.Food_Connect.exceptions.UserNotFoundException;
import br.com.food_connect.Food_Connect.model.dto.*;
import br.com.food_connect.Food_Connect.model.dto.user.ChangePasswordDTO;
import br.com.food_connect.Food_Connect.model.dto.user.UserPutRequestDTO;
import br.com.food_connect.Food_Connect.model.dto.user.UserRequestDTO;
import br.com.food_connect.Food_Connect.model.dto.user.UserResponseDTO;
import jakarta.transaction.Transactional;
import br.com.food_connect.Food_Connect.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.food_connect.Food_Connect.repository.UserRepository;

import java.util.List;

import static br.com.food_connect.Food_Connect.util.UpdateUtils.updateIfPresent;

@Service
public class UsersService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserResponseDTO> findAll(int page, int size) {
        return userRepository
                .findAll(PageRequest.of(page, size))
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ))
                .getContent();
    }

    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    @Transactional
    public UserResponseDTO create(UserRequestDTO request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyInUse(String.format("Email '%s' is already in use.", request.email()));
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setLogin(request.login());

        user.setPassword(passwordEncoder.encode(request.password()));

        user.setTypeUser(request.typeUser());

        userRepository.save(user);

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    @Transactional
    public UserResponseDTO update(Long id, UserPutRequestDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        updateIfPresent(request.name(), user::setName);
        updateIfPresent(request.login(), user::setLogin);

        if (request.email() != null && !request.email().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.email())) {
                throw new EmailAlreadyInUse(String.format("Email '%s' is already in use.", request.email()));
            }
            user.setEmail(request.email());
        }

        updateIfPresent(request.typeUser(), user::setTypeUser);

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    @Transactional
    public ApiResponse delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }

        userRepository.deleteById(id);

        return new ApiResponse(
                "User with ID " + id + " has been successfully deleted."
        );
    }

    @Transactional
    public void changePassword(String login, ChangePasswordDTO request) {

        User user = userRepository.findByLogin(login);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new InvalidPasswordException("The current password provided is incorrect.");
        }

        if (passwordEncoder.matches(request.newPassword(), user.getPassword())) {
            throw new InvalidPasswordException("The new password cannot be the same as the current password.");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }
    public User findEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<UserResponseDTO> searchByName(String name, int page, int size) {
        if (name == null || name.trim().isBlank()) {
            throw new IllegalArgumentException("Search term cannot be empty.");
        }

        return userRepository
                .findByNameContainingIgnoreCase(name, PageRequest.of(page, size))
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ))
                .getContent();
    }
}
