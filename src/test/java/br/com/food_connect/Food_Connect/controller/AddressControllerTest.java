package br.com.food_connect.Food_Connect.controller;


import br.com.food_connect.Food_Connect.factory.AddressFactory;
import br.com.food_connect.Food_Connect.model.Address;
import br.com.food_connect.Food_Connect.model.dto.address.AddressPutRequestDTO;
import br.com.food_connect.Food_Connect.model.dto.address.AddressRequestDTO;
import br.com.food_connect.Food_Connect.model.dto.address.AddressResponseDTO;
import br.com.food_connect.Food_Connect.model.dto.ApiResponse;
import br.com.food_connect.Food_Connect.service.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @InjectMocks
    private AddressController classUnderTest;

    @Mock
    private AddressService addressService;

    private AddressResponseDTO addressResponse;
    private AddressRequestDTO addressRequest;
    private AddressPutRequestDTO addressPutRequest;
    private Address address;

    private int PAGE_SIZE = 10;
    private int PAGE = 1;
    private long ID = 1l;
    @BeforeEach
    void setup() {
        addressResponse = AddressFactory.createResponse();
        addressRequest = AddressFactory.createRequest();
        address = AddressFactory.create();
        addressPutRequest = AddressFactory.createPutRequest();
    }


    @Test
    void givenPageAndSize_whenFindAll_thenReturnAddressList() {
        when(addressService.findAll(anyInt(), anyInt())).thenReturn(List.of(addressResponse));

        var result = classUnderTest.getAddresses(PAGE, PAGE_SIZE);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());

        verify(addressService).findAll(anyInt(), anyInt());
    }


    @Test
    void givenAddressId_whenFindById_thenReturnAddress() {
        when(addressService.findById(anyLong())).thenReturn(addressResponse);

       var result = classUnderTest.findById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(addressResponse, result.getBody());

        verify(addressService).findById(anyLong());
    }

    @Test
    void givenUserId_whenFindAddresses_thenReturnAddressList() {
        when(addressService.findAddressByUserId(anyLong())).thenReturn(List.of(addressResponse));

       var resutl = classUnderTest.findByUserId(10L);

        assertEquals(HttpStatus.OK, resutl.getStatusCode());
        assertEquals(1, resutl.getBody().size());

        verify(addressService).findAddressByUserId(anyLong());
    }

    @Test
    void givenValidAddressRequest_whenSave_thenReturnCreatedAddress() {
        when(addressService.saveAddress(any())).thenReturn(address);

       var result = classUnderTest.save(addressRequest);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());

        verify(addressService).saveAddress(addressRequest);
    }

    @Test
    void givenAddressId_whenDelete_thenReturnSuccessResponse() {
        var apiResponse = new ApiResponse("Deleted", "2024-01-01 10:00:00");

        when(addressService.deleteAddress(anyLong())).thenReturn(apiResponse);

        var result = classUnderTest.delete(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(apiResponse, result.getBody());

        verify(addressService).deleteAddress(anyLong());
    }

    @Test
    void givenUserId_whenDeleteAllAddresses_thenReturnSuccessResponse() {
        var apiResponse = new ApiResponse("Deleted", "2024-01-01 10:00:00");

        when(addressService.deleteAllAddressesOfUser(anyLong())).thenReturn(apiResponse);

        var result = classUnderTest.deleteAllAddressesOfUser(10L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(apiResponse, result.getBody());

        verify(addressService).deleteAllAddressesOfUser(anyLong());
    }

    @Test
    void givenAddressIdAndValidRequest_whenUpdate_thenReturnUpdatedAddress() {
        when(addressService.updateAddress(any(), anyLong())).thenReturn(addressResponse);

       var result = classUnderTest.update(ID, addressPutRequest);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(addressResponse, result.getBody());

        verify(addressService).updateAddress(any(), anyLong());
    }
}

