package br.com.food_connect.Food_Connect.service;

import br.com.food_connect.Food_Connect.exceptions.AddressNotFoundException;
import br.com.food_connect.Food_Connect.model.Address;
import br.com.food_connect.Food_Connect.model.User;
import br.com.food_connect.Food_Connect.model.dto.address.AddressPutRequestDTO;
import br.com.food_connect.Food_Connect.model.dto.address.AddressRequestDTO;
import br.com.food_connect.Food_Connect.model.dto.address.AddressResponseDTO;
import br.com.food_connect.Food_Connect.model.dto.ApiResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.food_connect.Food_Connect.repository.AddressRepository;

import java.util.List;

import static br.com.food_connect.Food_Connect.util.UpdateUtils.updateIfPresent;

@Service
public class AddressService {

    AddressRepository addressRepository;
    UsersService usersService;

    public AddressService(AddressRepository addressRepository, UsersService usersService) {
        this.addressRepository = addressRepository;
        this.usersService = usersService;
    }

    public List<AddressResponseDTO> findAll(int page, int size) {
        return addressRepository
                .findAll(PageRequest.of(page, size))
                .map(address -> new AddressResponseDTO(
                        address.getId(),
                        address.getStreet(),
                        address.getNeighborhood(),
                        address.getCity(),
                        address.getState(),
                        address.getZipCode(),
                        address.getComplement(),
                        address.getUser().getId()
                )).getContent();
    }

    public AddressResponseDTO findById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));

        return new AddressResponseDTO(address);
    }

    public List<AddressResponseDTO> findAddressByUserId(Long userId) {

        usersService.findEntityById(userId);
        List<Address> addresses = addressRepository.findByUserId(userId);

        return addresses.stream()
                .map(AddressResponseDTO::new)
                .toList();
    }

    @Transactional
    public Address saveAddress(AddressRequestDTO dto) {

        User user = usersService.findEntityById(dto.user_id());

        Address address = new Address();
        address.setStreet(dto.street());
        address.setNeighborhood(dto.neighborhood());
        address.setCity(dto.city());
        address.setState(dto.state());
        address.setZipCode(dto.zipCode());
        address.setComplement(dto.complement());
        address.setUser(user);

        return addressRepository.save(address);
    }

    @Transactional
    public ApiResponse deleteAddress(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new AddressNotFoundException(id);
        }

        addressRepository.deleteById(id);

        return new ApiResponse(
                "Address with ID " + id + " has been successfully deleted."
        );
    }

    @Transactional
    public ApiResponse deleteAllAddressesOfUser(Long userId) {
        usersService.findEntityById(userId);
        addressRepository.deleteByUserId(userId);

        return new ApiResponse(
                "All addresses linked to user with ID " + userId + " have been successfully deleted."
        );
    }

    @Transactional
    public AddressResponseDTO updateAddress(AddressPutRequestDTO dto, Long id) {

        Address address = addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(id));

        updateIfPresent(dto.street(), address::setStreet);
        updateIfPresent(dto.neighborhood(), address::setNeighborhood);
        updateIfPresent(dto.city(), address::setCity);
        updateIfPresent(dto.state(), address::setState);
        updateIfPresent(dto.zipCode(), address::setZipCode);
        updateIfPresent(dto.complement(), address::setComplement);

        Address updatedAddress = addressRepository.save(address);

        return new AddressResponseDTO(updatedAddress);
    }
}
