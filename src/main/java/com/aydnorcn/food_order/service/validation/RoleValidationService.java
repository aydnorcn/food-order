package com.aydnorcn.food_order.service.validation;

import com.aydnorcn.food_order.dto.role.CreateRoleRequestDto;
import com.aydnorcn.food_order.exception.AlreadyExistsException;
import com.aydnorcn.food_order.exception.NoAuthorityException;
import com.aydnorcn.food_order.repository.RoleRepository;
import com.aydnorcn.food_order.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class RoleValidationService {

    private final RoleRepository roleRepository;
    private final AuthService authService;

    public void validateRoleName(CreateRoleRequestDto dto){
        if (roleRepository.existsByName("ROLE_" + dto.getName().toUpperCase(Locale.ENGLISH))) {
            throw new AlreadyExistsException("Role already exists!");
        }
    }

    public void validateAuthority(){
        if (!authService.isCurrentAuthenticatedUserAdmin())
            throw new NoAuthorityException("You don't have authority to do this operation!");
    }
}