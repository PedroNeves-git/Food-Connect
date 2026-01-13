package br.com.food_connect.Food_Connect.service;

import br.com.food_connect.Food_Connect.exceptions.EmailAlreadyInUse;
import br.com.food_connect.Food_Connect.exceptions.UserNotFoundException;
import br.com.food_connect.Food_Connect.model.dto.UserPutRequestDTO;
import jakarta.transaction.Transactional;
import br.com.food_connect.Food_Connect.model.User;
import br.com.food_connect.Food_Connect.model.dto.ApiResponse;
import br.com.food_connect.Food_Connect.model.dto.UserRequestDTO;
import br.com.food_connect.Food_Connect.model.dto.UserResponseDTO;
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
            throw new EmailAlreadyInUse(request.email());
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

        if (!user.getEmail().equals(request.email()) && userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyInUse(request.email());
        }

        updateIfPresent(request.name(), user::setName);
        updateIfPresent(request.login(), user::setLogin);

        updateIfPresent(request.email(), email -> {
            if (!user.getEmail().equals(email) && userRepository.existsByEmail(email)) {
                throw new EmailAlreadyInUse(email);
            }
            user.setEmail(email);
        });

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

    public User findEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
}
