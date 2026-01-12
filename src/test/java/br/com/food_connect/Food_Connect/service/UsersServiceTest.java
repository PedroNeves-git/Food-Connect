package br.com.food_connect.Food_Connect.service;

import br.com.food_connect.Food_Connect.exceptions.UserNotFoundException;
import br.com.food_connect.Food_Connect.factory.UserFactory;
import br.com.food_connect.Food_Connect.model.User;
import br.com.food_connect.Food_Connect.repository.UserRepository;

import static java.util.Optional.ofNullable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersServiceTest {

    @InjectMocks
    private UsersService classUnderTest;

    @Mock
    private UserRepository userRepository;

    private User user;
    private int PAGE_SIZE = 10;
    private int PAGE = 1;
    private long ID = 1l;


    @BeforeEach
    void setUp() {
        user = UserFactory.create();
    }

    @Test
    void givenPageAndSize_whenFindAll_thenReturnUserList(){
        var userList = List.of(user);

        when(userRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl(userList));

        var result = classUnderTest.findAll(PAGE, PAGE_SIZE);

        assertFalse(result.isEmpty());

    }

    @Test
    void givenPageAndSize_whenFindAll_thenReturn_EmptyList(){
        when(userRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());

        var result = classUnderTest.findAll(PAGE, PAGE_SIZE);

        assertTrue(result.isEmpty());

    }


    @Test
    void givenId_whenFindById_thenReturnUser(){
        when(userRepository.findById(anyLong())).thenReturn(ofNullable(user));

        assertDoesNotThrow(() -> {
            classUnderTest.findById(ID);
        });

    }

    @Test
    void givenId_whenFindById_thenThrowNotFoundException(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            classUnderTest.findById(ID);
        });

    }

    
}
