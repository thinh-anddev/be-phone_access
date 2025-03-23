package com.TMDT.api.Api.springboot.repositories;

import com.TMDT.api.Api.springboot.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Customer findByUsername(String username);

    Customer findByEmail(String email);
}
