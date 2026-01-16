package br.com.food_connect.Food_Connect.service;

import br.com.food_connect.Food_Connect.factory.UserFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @InjectMocks
    private TokenService classUndderTest;

    private Field secretField;

    @BeforeEach
    void setUp() throws Exception {
        secretField = TokenService.class.getDeclaredField("secret");
        secretField.setAccessible(true);
        secretField.set(classUndderTest, "test-secret");
    }


    @Test
    void givenValidUser_whenGenerateToken_thenReturnToken() {
        var token = classUndderTest.generateToken(UserFactory.createUser());

        assertThat(token).isNotBlank();
    }


    @Test
    void givenAValidToken_whenValidateToken_thenReturnSubject() {
        var user = UserFactory.createUser();

        var token = classUndderTest.generateToken(user);
        var result = classUndderTest.validateToken(token);

        assertThat(result).isEqualTo(user.getLogin());
    }

}
