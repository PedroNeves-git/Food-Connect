package controller;

import jakarta.validation.Valid;
import model.Address;
import model.dto.AddressPutRequestDTO;
import model.dto.AddressRequestDTO;
import model.dto.AddressResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.AddressService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<Page<Address>> getAddresses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok(addressService.findAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> findById(
            @PathVariable Long id
    )
    {
        Address address = addressService.findById(id);
        return ResponseEntity.ok(address);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponseDTO>> findByUserId(
            @PathVariable Long userId
    )
    {
        var address = this.addressService.findAddressByUserId(userId);
        return ResponseEntity.ok(address);
    }

    @PostMapping
    public ResponseEntity<Void> save(
            @Valid @RequestBody AddressRequestDTO addressRequestDTO
    ) {
        Address saved = addressService.saveAddress(addressRequestDTO);

        URI location = URI.create("/addresses/" + saved.getId());

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id
    )
    {
        this.addressService.deleteAddress(id);
    }

    @DeleteMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllAddressesOfUser(
            @PathVariable Long userId
    )
    {
        this.addressService.deleteAllAddressesOfUser(userId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @Valid @RequestBody AddressPutRequestDTO addressPutRequestDTO
    )
    {
        this.addressService.updateAddress(addressPutRequestDTO, id);
        return ResponseEntity.noContent().build();
    }
}
