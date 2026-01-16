package br.com.food_connect.Food_Connect.service;

import br.com.food_connect.Food_Connect.exceptions.EmailAlreadyInUse;
import br.com.food_connect.Food_Connect.exceptions.UserNotFoundException;
import br.com.food_connect.Food_Connect.factory.ChangePasswordFactory;
import br.com.food_connect.Food_Connect.factory.UserFactory;
import br.com.food_connect.Food_Connect.model.User;
import br.com.food_connect.Food_Connect.repository.UserRepository;

import static java.util.Optional.ofNullable;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Mock
    private PasswordEncoder passwordEncoder;

    private User user;
    private int PAGE_SIZE = 10;
    private int PAGE = 1;
    private long ID = 1l;
    private String SAME_EMAIL= "email";
    private String DIFFERENT_EMAIL= "different_email";


    @BeforeEach
    void setUp() {
        user = UserFactory.createUser();
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


    @Test
    void givenRequest_whenCreate_thenCreateUser(){
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        classUnderTest.create(UserFactory.createUserRequest());

    }


    @Test
    void givenRequest_whenCreate_thenThrowEmailAlreadyInUse(){
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(EmailAlreadyInUse.class, () -> {
            classUnderTest.create(UserFactory.createUserRequest());
        });
    }

    @Test
    void givenRequestAndUserId_whenUpdate_thenUpdateUser(){
        when(userRepository.findById(anyLong())).thenReturn(ofNullable(user));
        lenient().when(userRepository.existsByEmail(anyString())).thenReturn(false);


        assertDoesNotThrow(() -> {
            classUnderTest.update(ID, UserFactory.createUserPutRequest(SAME_EMAIL));
        });

    }

    @Test
    void givenInvalidId_whenUpdate_thenThowUserNotFoundException(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            classUnderTest.update(ID, UserFactory.createUserPutRequest(SAME_EMAIL));
        });
    }

    @Test
    void givenAnExistentEmail_whenUpdate_thenThowEmailAlreadyInUse(){
        when(userRepository.findById(anyLong())).thenReturn(ofNullable(user));
        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(EmailAlreadyInUse.class, () -> {
            classUnderTest.update(ID, UserFactory.createUserPutRequest(DIFFERENT_EMAIL));
        });
    }


    @Test
    void givenAnId_whenDelete_thenDeleteUser(){
        when(userRepository.existsById(anyLong())).thenReturn(true);

        assertDoesNotThrow(() -> {
            classUnderTest.delete(ID);
        });

        verify(userRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void givenAnInvalidId_whenDelete_thenThowUserNotFoundException(){
        when(userRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> {
            classUnderTest.delete(ID);
        });
    }


    @Test
    void givenAnLoginAndChangePasswordRequest_whenChangePassword_thenUpdatePassword() {
        var request = ChangePasswordFactory.create();
        user.setPassword("encodedOldPassword");

        when(userRepository.findByLogin(anyString())).thenReturn(user);
        when(passwordEncoder.matches(eq(request.currentPassword()), eq(user.getPassword()))).thenReturn(true);
        when(passwordEncoder.matches(eq(request.newPassword()), eq(user.getPassword()))).thenReturn(false);
        when(passwordEncoder.encode(request.newPassword())).thenReturn("encodedNewPassword");
        when(userRepository.save(any())).thenReturn(user);

        assertDoesNotThrow(() -> {
            classUnderTest.changePassword(user.getLogin(), request);
        });
    }


    @Test
    void givenAnLoginAndChangePasswordRequestWithInvalidLogin_whenChangePassword_theThrowUserNotFoundException() {
        when(userRepository.findByLogin(anyString())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> {
            classUnderTest.changePassword(user.getLogin(), ChangePasswordFactory.create());
        });

    }

    @Test
    void givenAnLoginAndSamePassword_whenChangePassword_theThrowRuntimeException() {
        when(userRepository.findByLogin(anyString())).thenReturn(user);
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            classUnderTest.changePassword(user.getLogin(), ChangePasswordFactory.create());
        });

    }



    @Test
    void givenAnId_whenFindEntityById_thenreturnUser(){
        when(userRepository.findById(anyLong())).thenReturn(ofNullable(user));

        assertDoesNotThrow(() -> {
            classUnderTest.findEntityById(ID);
        });
    }

    @Test
    void givenAnInvalidId_whenFindEntityById_thenThrowUserNotFoundException(){
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            classUnderTest.findEntityById(ID);
        });

    }
}
