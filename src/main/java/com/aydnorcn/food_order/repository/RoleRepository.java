package com.aydnorcn.food_order.repository;

import com.aydnorcn.food_order.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByName(String name);
    Optional<Role> findByName(String name);
}
