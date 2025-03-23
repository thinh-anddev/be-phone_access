package com.TMDT.api.Api.springboot.service;

import com.TMDT.api.Api.springboot.models.Address;
import com.TMDT.api.Api.springboot.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    public List<Address> getAddressByCustomerId(int customerId) {
        return addressRepository.findByCustomer_IdAndStatus(customerId, 1);
    }

    public Address getAddressById(int id) {
        return addressRepository.findById(id);
    }

    public Address add(Address address) {
        return addressRepository.save(address);
    }

    public Address update(Address address) {
        return addressRepository.save(address);
    }

    public void delete(int id) {
        Address address = addressRepository.findById(id);
        address.setStatus(0);
        addressRepository.save(address);
    }
}
