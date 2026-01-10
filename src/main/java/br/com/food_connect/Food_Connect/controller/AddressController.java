package br.com.food_connect.Food_Connect.controller;

import br.com.food_connect.Food_Connect.model.dto.ApiResponse;
import jakarta.validation.Valid;
import br.com.food_connect.Food_Connect.model.Address;
import br.com.food_connect.Food_Connect.model.dto.AddressPutRequestDTO;
import br.com.food_connect.Food_Connect.model.dto.AddressRequestDTO;
import br.com.food_connect.Food_Connect.model.dto.AddressResponseDTO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.com.food_connect.Food_Connect.service.AddressService;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<List<AddressResponseDTO>> getAddresses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok(addressService.findAll(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> findById(
            @PathVariable Long id
    )
    {
        return ResponseEntity.ok(
                addressService.findById(id)
        );
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
    public ResponseEntity<AddressResponseDTO> save(
            @Valid @RequestBody AddressRequestDTO addressRequestDTO
    ) {
        Address saved = addressService.saveAddress(addressRequestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AddressResponseDTO(saved));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiResponse> delete(
            @PathVariable Long id
    )
    {
        return ResponseEntity.ok(this.addressService.deleteAddress(id));
    }

    @DeleteMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<ApiResponse> deleteAllAddressesOfUser(
            @PathVariable Long userId
    )
    {
        return ResponseEntity.ok(this.addressService.deleteAllAddressesOfUser(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AddressPutRequestDTO addressPutRequestDTO
    )
    {
        AddressResponseDTO updatedAddress = this.addressService.updateAddress(addressPutRequestDTO, id);
        return ResponseEntity.ok(updatedAddress);
    }
}
