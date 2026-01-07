package service;

import exceptions.AddressNotFoundException;
import model.Address;
import model.User;
import model.dto.AddressPutRequestDTO;
import model.dto.AddressRequestDTO;
import model.dto.AddressResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import repository.AddressRepository;

import java.util.List;


public class AddressService {

    AddressRepository addressRepository;
    UsersService usersService;

    public AddressService(AddressRepository addressRepository, UsersService usersService) {
        this.addressRepository = addressRepository;
        this.usersService = usersService;
    }

    public Page<Address> findAll(int page, int size) {
        return addressRepository.findAll(
                PageRequest.of(page, size)
        );
    }

    public Address findById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));
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
    public void deleteAddress(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new AddressNotFoundException(id);
        }

        addressRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllAddressesOfUser(Long userId) {
        usersService.findEntityById(userId);
        addressRepository.deleteByUserId(userId);
    }

    @Transactional
    public void updateAddress(AddressPutRequestDTO dto, Long id) {

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));

        address.setStreet(dto.street());
        address.setNeighborhood(dto.neighborhood());
        address.setCity(dto.city());
        address.setState(dto.state());
        address.setZipCode(dto.zipCode());
        address.setComplement(dto.complement());
    }
}
