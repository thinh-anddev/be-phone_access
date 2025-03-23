package com.TMDT.api.Api.springboot.repositories;

import com.TMDT.api.Api.springboot.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByCustomer_IdAndStatus(int customerId, int status);

    Address findById(int id);
}
