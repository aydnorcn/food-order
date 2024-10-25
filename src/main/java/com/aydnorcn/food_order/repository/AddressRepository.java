package com.aydnorcn.food_order.repository;

import com.aydnorcn.food_order.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
