package br.com.food_connect.Food_Connect.service;

import br.com.food_connect.Food_Connect.factory.UserFactory;
import br.com.food_connect.Food_Connect.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @InjectMocks
    private AuthorizationService classUnderTest;

    @Mock
    private UserRepository userRepository;


    @Test
    void givenUsername_whenLoadUserByUsername_ThenReturnUserDetails(){
        when(userRepository.findByLogin(anyString())).thenReturn(UserFactory.createUser());

        var result = classUnderTest.loadUserByUsername("any");

        assert result instanceof UserDetails;

    }


    @Test
    void givenAnInvalidUsername_whenLoadUserByUsername_ThenThrowsUsernameNotFoundException(){
        when(userRepository.findByLogin(anyString())).thenReturn(null);

        var result = classUnderTest.loadUserByUsername("any");

        assert result == null;

    }

}
