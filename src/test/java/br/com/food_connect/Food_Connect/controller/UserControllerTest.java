package br.com.food_connect.Food_Connect.controller;




import br.com.food_connect.Food_Connect.factory.UserFactory;
import br.com.food_connect.Food_Connect.model.dto.*;
import br.com.food_connect.Food_Connect.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    private UserController classUnderTest;

    @Mock
    private UsersService usersService;

    @Mock
    private Authentication authentication;

    private UserResponseDTO userResponse;
    private UserRequestDTO userRequest;
    private UserPutRequestDTO userPutRequest;


    @BeforeEach
    void setup() {
        userResponse = UserFactory.createUserResponse();
        userRequest = UserFactory.createUserRequest();
        userPutRequest = UserFactory.createUserPutRequest("email");
    }



    @Test
    void givenPageAndSize_whenListUsers_thenReturnUserList() {
        when(usersService.findAll(anyInt(), anyInt())).thenReturn(List.of(userResponse));

        var result = classUnderTest.listUsers(0, 10);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());

        verify(usersService).findAll(anyInt(), anyInt());
    }


    @Test
    void givenUserId_whenFindById_thenReturnUser() {
        when(usersService.findById(anyLong())).thenReturn(userResponse);

       var result = classUnderTest.findById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(userResponse, result.getBody());

        verify(usersService).findById(anyLong());
    }


    @Test
    void givenValidUserRequest_whenCreate_thenReturnCreatedUser() {
        when(usersService.create(any())).thenReturn(userResponse);

        var result = classUnderTest.create(userRequest);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(userResponse, result.getBody());

        verify(usersService).create(any());
    }


    @Test
    void givenUserIdAndValidRequest_whenUpdate_thenReturnUpdatedUser() {
        when(usersService.update(anyLong(), any())).thenReturn(userResponse);

        var result = classUnderTest.update(1L, userPutRequest);

        assertEquals(userResponse, result);

        verify(usersService).update(anyLong(), any());
    }


    @Test
    void givenAuthenticatedUser_whenChangePassword_thenReturnNoContent() {
        var request = new ChangePasswordDTO("oldPass123", "newPass456");

        var result = classUnderTest.changePassword(request, authentication);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());

        verify(usersService).changePassword(any(), any());
    }


    @Test
    void givenUserId_whenDelete_thenReturnSuccessResponse() {
        var apiResponse = new ApiResponse("Deleted", "2024-01-01 10:00:00");

        when(usersService.delete(anyLong())).thenReturn(apiResponse);

        var result = classUnderTest.delete(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(apiResponse, result.getBody());
        verify(usersService).delete(1L);
    }

}
