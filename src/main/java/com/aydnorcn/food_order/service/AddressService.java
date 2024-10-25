package com.aydnorcn.food_order.service;

import com.aydnorcn.food_order.dto.address.CreateAddressRequestDto;
import com.aydnorcn.food_order.entity.Address;
import com.aydnorcn.food_order.entity.User;
import com.aydnorcn.food_order.exception.NoAuthorityException;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserContextService userContextService;

    public Address getAddressById(Long addressId){
        return addressRepository.findById(addressId).orElseThrow(() -> new ResourceNotFoundException("Address not found"));
    }

    public Address createAddress(CreateAddressRequestDto dto){
        User user = userContextService.getCurrentAuthenticatedUser();

        Address address = new Address(dto, user);
        return addressRepository.save(address);
    }

    public Address updateAddress(Long addressId, CreateAddressRequestDto dto){
        Address address = getAddressById(addressId);

        validateAuthority(address);

        address.setFullName(dto.getFullName());
        address.setAddress(dto.getAddress());
        address.setCity(dto.getCity());
        address.setPhone(dto.getPhone());
        address.setZipCode(dto.getZipCode());
        return addressRepository.save(address);
    }

    public void deleteAddress(Long addressId){
        Address address = getAddressById(addressId);

        validateAuthority(address);

        addressRepository.delete(address);
    }

    private void validateAuthority(Address address){
        User user = userContextService.getCurrentAuthenticatedUser();

        if(!address.getUser().equals(user) && !userContextService.isCurrentAuthenticatedUserAdmin() && !userContextService.isCurrentAuthenticatedUserStaff()){
            throw new NoAuthorityException("You do not have authority to perform this operation!");
        }
    }
}