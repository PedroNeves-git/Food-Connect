package br.com.food_connect.Food_Connect.service;


import br.com.food_connect.Food_Connect.exceptions.AddressNotFoundException;
import br.com.food_connect.Food_Connect.factory.AddressFactory;
import br.com.food_connect.Food_Connect.factory.UserFactory;
import br.com.food_connect.Food_Connect.model.Address;
import br.com.food_connect.Food_Connect.model.dto.address.AddressPutRequestDTO;
import br.com.food_connect.Food_Connect.model.dto.address.AddressRequestDTO;
import br.com.food_connect.Food_Connect.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @InjectMocks
    private AddressService classUnderTest;

    @Mock
    private AddressRepository addressRepository;
    @Mock
    private UsersService usersService;

    private int PAGE_SIZE = 10;
    private int PAGE = 1;
    private long ID = 1l;

    private Address address;
    private AddressRequestDTO addressRequest;
    private AddressPutRequestDTO addressPutRequest;

    @BeforeEach
    void setUp() {
        address = AddressFactory.create();
        addressRequest = AddressFactory.createRequest();
        addressPutRequest = AddressFactory.createPutRequest();

    }


    @Test
    void givenPageAndSize_whenFindAll_thenReturnListOfAddress() {
        when(addressRepository.findAll(PageRequest.of(PAGE, PAGE_SIZE))).thenReturn(new PageImpl<>(List.of(address)));

        var result  = classUnderTest.findAll(PAGE, PAGE_SIZE);

        assertThat(result).isNotEmpty();
    }

    @Test
    void givenPageAndSize_whenFindAll_thenReturnEmptyPage() {
        when(addressRepository.findAll(PageRequest.of(PAGE, PAGE_SIZE))).thenReturn(Page.empty());

        var result  = classUnderTest.findAll(PAGE, PAGE_SIZE);

        assertThat(result).isEmpty();

    }


    @Test
    void givenAnId_whenFindById_thenReturnAnAddress() {
        when(addressRepository.findById(address.getId())).thenReturn(ofNullable(address));

        var result = classUnderTest.findById(address.getId());

        assertNotNull(result);
    }

    @Test
    void givenAnNonExistentId_whenFindById_thenAddressThrowNotFoundException() {
        when(addressRepository.findById(address.getId())).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> {
            classUnderTest.findById(ID);
        });

    }


    @Test
    void givenAnUserId_whenFindAddressByUserId_thenReturnAnAddressList() {
        when(usersService.findEntityById(anyLong())).thenReturn(UserFactory.createUser());
        when(addressRepository.findByUserId(anyLong())).thenReturn(List.of(address));

        var result = classUnderTest.findAddressByUserId(ID);

        assertThat(result).isNotEmpty();
        verify(usersService).findEntityById(ID);
        verify(addressRepository).findByUserId(ID);
    }


    @Test
    void givenAnAdressRequest_whenSaveAddress_thenReturnAnAddress() {
        when(usersService.findEntityById(anyLong())).thenReturn(UserFactory.createUser());
        when(addressRepository.save(any())).thenReturn(address);

        var result = classUnderTest.saveAddress(addressRequest);

        assertNotNull(result);

        verify(usersService).findEntityById(addressRequest.user_id());
        verify(addressRepository).save(any(Address.class));
    }


    @Test
    void givenAnId_whenDeleteAddress_thenReturnAnApiResponse() {
        when(addressRepository.existsById(anyLong())).thenReturn(true);

        classUnderTest.deleteAddress(ID);

        verify(addressRepository).deleteById(anyLong());
    }


    @Test
    void givenAnNonExistentId_whenDeleteAddress_thenThrowAddressNotFoundException() {
        when(addressRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(AddressNotFoundException.class, () -> {
            classUnderTest.deleteAddress(ID);
        });

    }

    @Test
    void givenAnUserId_whenDeleteAllAddressesOfUser_thenReturnAnApiResponse() {
        when(usersService.findEntityById(anyLong())).thenReturn(UserFactory.createUser());

        classUnderTest.deleteAllAddressesOfUser(ID);

        verify(addressRepository).deleteByUserId(anyLong());
    }


    @Test
    void givenAnAdressRequestAndAnId_whenUpdateAddress_thenReturnAnAdressResponse() {
        when(addressRepository.findById(anyLong())).thenReturn(ofNullable(address));
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        assertDoesNotThrow(() -> {
            classUnderTest.updateAddress(addressPutRequest,ID);
        });

        verify(addressRepository).findById(anyLong());
        verify(addressRepository).save(any(Address.class));
    }

    @Test
    void givenAnInvalidId_whenUpdateAddress_thenThrowAddressNotFoundException() {
        when(addressRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class, () -> {
            classUnderTest.updateAddress(addressPutRequest,ID);
        });
    }

}
