package com.aydnorcn.food_order.controller;

import com.aydnorcn.food_order.dto.address.AddressResponseDto;
import com.aydnorcn.food_order.dto.address.CreateAddressRequestDto;
import com.aydnorcn.food_order.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto> getAddress(@PathVariable Long addressId) {
        return ResponseEntity.ok(new AddressResponseDto(addressService.getAddressById(addressId)));
    }

    @PostMapping
    public ResponseEntity<AddressResponseDto> createAddress(@Valid @RequestBody CreateAddressRequestDto dto) {
        return ResponseEntity.ok(new AddressResponseDto(addressService.createAddress(dto)));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressResponseDto> updateAddress(@PathVariable Long addressId, @Valid @RequestBody CreateAddressRequestDto dto) {
        return ResponseEntity.ok(new AddressResponseDto(addressService.updateAddress(addressId, dto)));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long addressId) {
        addressService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }
}