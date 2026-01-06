package repository;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Page<UserEntity> findAll(Pageable pageable);

    boolean existsByEmail(@NotBlank @Email String email);
    UserEntity findByEmail(String email);
}
