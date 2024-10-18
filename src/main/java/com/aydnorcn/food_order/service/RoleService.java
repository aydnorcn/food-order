package com.aydnorcn.food_order.service;

import com.aydnorcn.food_order.dto.role.CreateRoleRequestDto;
import com.aydnorcn.food_order.entity.Role;
import com.aydnorcn.food_order.exception.AlreadyExistsException;
import com.aydnorcn.food_order.exception.NoAuthorityException;
import com.aydnorcn.food_order.exception.ResourceNotFoundException;
import com.aydnorcn.food_order.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class RoleService {

    private RoleRepository roleRepository;
    private AuthService authService;

    @Autowired
    public RoleService(RoleRepository roleRepository, @Lazy AuthService authService) {
        this.roleRepository = roleRepository;
        this.authService = authService;
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role createRole(CreateRoleRequestDto dto) {
        if (roleRepository.existsByName("ROLE_" + dto.getName().toUpperCase(Locale.ENGLISH))) {
            throw new AlreadyExistsException("Role already exists!");
        }

        if (!authService.isCurrentAuthenticatedUserAdmin())
            throw new NoAuthorityException("You don't have permission!");

        Role role = new Role();
        String roleName = "ROLE_" + dto.getName().toUpperCase(Locale.ENGLISH);
        role.setName(roleName);
        return roleRepository.save(role);
    }

    public Role getRoleByName(String name) {
        String roleName = "ROLE_" + name.toUpperCase(Locale.ENGLISH);
        return roleRepository.findByName(roleName).orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
    }

    public Role updateRole(Long id, CreateRoleRequestDto dto) {
        if (!authService.isCurrentAuthenticatedUserAdmin())
            throw new NoAuthorityException("You don't have permission!");

        Role updateRole = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role not found!"));
        String roleName = "ROLE_" + dto.getName().toUpperCase(Locale.ENGLISH);
        updateRole.setName(roleName);
        return roleRepository.save(updateRole);
    }

    public String deleteRole(Long id) {
        if (!authService.isCurrentAuthenticatedUserAdmin())
            throw new NoAuthorityException("You don't have permission!");
        roleRepository.deleteById(id);
        return "Role removed!";
    }
}
