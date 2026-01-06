package service.impl;

import br.com.food_connect.Food_Connect.exception.InvalidLoginException;
import model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import repository.UserRepository;
import service.LoginService;
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void login(String email, String password) throws InvalidLoginException {
        UserEntity user = repository.findByEmail(email);
        if (user == null) {
            throw new InvalidLoginException();
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidLoginException();
        }
    }
}
