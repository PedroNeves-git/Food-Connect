package br.com.food_connect.Food_Connect.repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import br.com.food_connect.Food_Connect.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);

    boolean existsByEmail(@NotBlank @Email String email);
}
