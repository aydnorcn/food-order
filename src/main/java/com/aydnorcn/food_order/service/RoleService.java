package com.aydnorcn.food_order.service;

import com.aydnorcn.food_order.dto.role.CreateRoleRequestDto;
import com.aydnorcn.food_order.entity.Role;
import com.aydnorcn.food_order.exception.AlreadyExistsException;
import com.aydnorcn.food_order.exception.NoAuthorityException;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.repository.RoleRepository;
import com.aydnorcn.food_order.service.validation.RoleValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleValidationService roleValidationService;
    private final UserContextService userContextService;

    public Role getRoleById(Long roleId) {
        roleValidationService.validateAuthority(String.format("view role with ID %s", roleId));

        return roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
    }

    public List<Role> getAllRoles() {
        roleValidationService.validateAuthority("view all roles");
        return roleRepository.findAll();
    }

    public Role getRoleByName(String name) {
        String roleName = "ROLE_" + name.toUpperCase(Locale.ENGLISH);
        return roleRepository.findByName(roleName).orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
    }

    public Role createRole(CreateRoleRequestDto dto) {
        roleValidationService.validateRoleName(dto);
        roleValidationService.validateAuthority(String.format("create role with name %s", dto.getName()));

        Role role = new Role();
        String roleName = "ROLE_" + dto.getName().toUpperCase(Locale.ENGLISH);
        role.setName(roleName);

        log.info("Role with name {} created by user with ID {}", dto.getName(), userContextService.getCurrentAuthenticatedUser().getId());

        return roleRepository.save(role);
    }

    public Role updateRole(Long roleId, CreateRoleRequestDto dto) {
        roleValidationService.validateAuthority(String.format("update role with ID %s", roleId));

        Role updateRole = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
        String roleName = "ROLE_" + dto.getName().toUpperCase(Locale.ENGLISH);

        log.info("Role with ID {} updated from name {} to {} by user with ID {}", roleId, updateRole.getName(), dto.getName(), userContextService.getCurrentAuthenticatedUser().getId());

        updateRole.setName(roleName);


        return roleRepository.save(updateRole);
    }

    public void deleteRole(Long roleId) {
        roleValidationService.validateAuthority(String.format("delete role with ID %s", roleId));

        Role role = getRoleById(roleId);
        roleRepository.delete(role);

        log.info("Role with ID {} and name {} deleted by user with ID {}", roleId, role.getName(), userContextService.getCurrentAuthenticatedUser().getId());
    }
}
