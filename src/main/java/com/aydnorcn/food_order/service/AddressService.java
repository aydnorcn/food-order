package com.aydnorcn.food_order.service;

import com.aydnorcn.food_order.dto.address.CreateAddressRequestDto;
import com.aydnorcn.food_order.entity.Address;
import com.aydnorcn.food_order.entity.User;
import com.aydnorcn.food_order.exception.NoAuthorityException;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserContextService userContextService;

    public Address getAddressById(Long addressId){
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        validateAuthority(address);

        return address;
    }

    public Address createAddress(CreateAddressRequestDto dto){
        User user = userContextService.getCurrentAuthenticatedUser();

        Address address = new Address(dto, user);

        Address savedAddress = addressRepository.save(address);

        log.info("Address created | User {} created a new address with id {}", user.getId(), savedAddress.getId());

        return savedAddress;
    }

    public Address updateAddress(Long addressId, CreateAddressRequestDto dto){
        Address address = getAddressById(addressId);

        validateAuthority(address);

        address.setFullName(dto.getFullName());
        address.setAddress(dto.getAddress());
        address.setCity(dto.getCity());
        address.setPhone(dto.getPhone());
        address.setZipCode(dto.getZipCode());

        log.info("Address updated | User {} updated address with id {}", address.getUser().getId(), address.getId());

        return addressRepository.save(address);
    }

    public void deleteAddress(Long addressId){
        Address address = getAddressById(addressId);

        validateAuthority(address);

        log.info("Address deleted | User {} deleted address:  {}", address.getUser().getId(), address);

        addressRepository.delete(address);
    }

    private void validateAuthority(Address address){
        User user = userContextService.getCurrentAuthenticatedUser();

        if(!address.getUser().equals(user) && !userContextService.isCurrentAuthenticatedUserAdmin() && !userContextService.isCurrentAuthenticatedUserStaff()){
            log.error("User {} is not authorized to perform this action", user.getId());
            throw new NoAuthorityException("You do not have authority to perform this operation!");
        }
    }
}