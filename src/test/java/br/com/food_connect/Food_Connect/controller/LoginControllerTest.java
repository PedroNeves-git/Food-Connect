package br.com.food_connect.Food_Connect.controller;

import br.com.food_connect.Food_Connect.factory.LoginFactory;
import br.com.food_connect.Food_Connect.factory.UserFactory;
import br.com.food_connect.Food_Connect.model.User;
import br.com.food_connect.Food_Connect.model.dto.LoginDTO;
import br.com.food_connect.Food_Connect.model.dto.LoginResponseDTO;
import br.com.food_connect.Food_Connect.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class LoginControllerTest {
    @InjectMocks
    private LoginController classUnderTest;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @Mock
    private Authentication authentication;

    private MockMvc mockMvc;
    private ObjectMapper mapper;
    private LoginDTO login;
    private User user;

    private static final String BASE_URL = "/auth/login";

    @BeforeEach
    void setup() {
        user = UserFactory.createUser();
        login = LoginFactory.create();
    }


    @Test
    void shouldReturn200AndToken_whenLoginIsValid() throws Exception {
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(tokenService.generateToken(any())).thenReturn("jwt-token");

        var result = classUnderTest.login(login);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(result.getBody() instanceof LoginResponseDTO);
        assertEquals("jwt-token", ( ((LoginResponseDTO) result.getBody()).token()));

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenService).generateToken(user);

        verify(authenticationManager).authenticate(any());
        verify(tokenService).generateToken(user);
    }

}
