package br.com.food_connect.Food_Connect.service;


import br.com.food_connect.Food_Connect.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @InjectMocks
    private AddressService classUnderTest;

    @Mock
    private AddressRepository addressRepository;
    @Mock
    private UsersService usersService;



    @Test
    void givenPageAndSize_whenFindAll_thenReturnListOfAddress() {

    }

    @Test
    void givenPageAndSize_whenFindAll_thenReturnEmptyPage() {

    }


    @Test
    void givenAnId_whenFindById_thenReturnAnAddress() {

    }

    @Test
    void givenAnNonExistentId_whenFindById_thenAddressThrowNotFoundException() {

    }


    @Test
    void givenAnUserId_whenFindAddressByUserId_thenReturnAnAddressList() {

    }


    @Test
    void givenAnAdressRequest_whenSaveAddress_thenReturnAnAddress() {

    }


    @Test
    void givenAnId_whenDeleteAddress_thenReturnAnApiResponse() {

    }


    @Test
    void givenAnNonExistentId_whenDeleteAddress_thenThrowAddressNotFoundException() {

    }

    @Test
    void givenAnUserId_whenDeleteAllAddressesOfUser_thenReturnAnApiResponse() {

    }


    @Test
    void givenAnAdressRequestAndAnId_whenUpdateAddress_thenReturnAnAdressResponse() {

    }

    @Test
    void givenAnInvalidId_whenUpdateAddress_thenThrowAddressNotFoundException() {

    }

}
