package com.aydnorcn.food_order.service;

import com.aydnorcn.food_order.dto.role.CreateRoleRequestDto;
import com.aydnorcn.food_order.entity.Role;
import com.aydnorcn.food_order.exception.AlreadyExistsException;
import com.aydnorcn.food_order.exception.NoAuthorityException;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.repository.RoleRepository;
import com.aydnorcn.food_order.service.validation.RoleValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleValidationService roleValidationService;

    public Role getRoleById(Long id) {
        roleValidationService.validateAuthority();

        return roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
    }

    public List<Role> getAllRoles() {
        roleValidationService.validateAuthority();
        return roleRepository.findAll();
    }

    public Role getRoleByName(String name) {
        String roleName = "ROLE_" + name.toUpperCase(Locale.ENGLISH);
        return roleRepository.findByName(roleName).orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
    }

    public Role createRole(CreateRoleRequestDto dto) {
        roleValidationService.validateRoleName(dto);
        roleValidationService.validateAuthority();

        Role role = new Role();
        String roleName = "ROLE_" + dto.getName().toUpperCase(Locale.ENGLISH);
        role.setName(roleName);
        return roleRepository.save(role);
    }

    public Role updateRole(Long id, CreateRoleRequestDto dto) {
        roleValidationService.validateAuthority();

        Role updateRole = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
        String roleName = "ROLE_" + dto.getName().toUpperCase(Locale.ENGLISH);
        updateRole.setName(roleName);
        return roleRepository.save(updateRole);
    }

    public String deleteRole(Long id) {
        roleValidationService.validateAuthority();

        roleRepository.deleteById(id);
        return "Role removed!";
    }
}
