package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.dto.AddressDTO;
import com.TMDT.api.Api.springboot.dto.CustomerDTO;
import com.TMDT.api.Api.springboot.mapper.AddressMapper;
import com.TMDT.api.Api.springboot.mapper.CustomerMapper;
import com.TMDT.api.Api.springboot.models.Address;
import com.TMDT.api.Api.springboot.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/api/v1/address")
public class AddressControllers {
    @Autowired
    AddressService addressService;
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    CustomerMapper customerMapper;

    @GetMapping("/getAddressByCustomerId/{customerId}")
    public ResponseEntity<ResponseObject> getAddressByCustomerId(@PathVariable int customerId) {
        List<Address> addresses = addressService.getAddressByCustomerId(customerId);
        List<AddressDTO> addressDTOs = addressMapper.toListDto(addresses);

        return ResponseEntity.ok(new ResponseObject("ok", "Success", addressDTOs));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getAddressById(@PathVariable int id) {
        Address address = addressService.getAddressById(id);

        if (address == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseObject("failed", "Address not found", null));
        }

        AddressDTO addressDTO = addressMapper.toDto(address);

        return ResponseEntity.ok(new ResponseObject("ok", "Success", addressDTO));
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> insertAddress(@RequestBody AddressDTO addressDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerDTO currentCustomer = (CustomerDTO) authentication.getPrincipal();

        if (currentCustomer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseObject("failed", "Unauthorized", null));
        }

        Address newAddress = addressMapper.toEntity(addressDTO);
        newAddress.setCustomer(customerMapper.toEntity(currentCustomer));
        Address saved = addressService.add(newAddress);
        AddressDTO savedDTO = addressMapper.toDto(saved);

        return ResponseEntity.ok(new ResponseObject("ok", "Success", savedDTO));
    }


    @PutMapping("/update")
    public ResponseEntity<ResponseObject> updateAddress(@RequestBody AddressDTO addressDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerDTO currentCustomer = (CustomerDTO) authentication.getPrincipal();

        if (currentCustomer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseObject("failed", "Unauthorized", null));
        }

        Address toUpdate = addressMapper.toEntity(addressDTO);
        Address existing = addressService.getAddressById(toUpdate.getId());

        if (existing == null || existing.getCustomer().getId() != currentCustomer.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseObject("failed", "You do not have permission to update this address", null));
        }
        toUpdate.setCustomer(customerMapper.toEntity(currentCustomer));
        Address updated = addressService.update(toUpdate);
        AddressDTO updatedDTO = addressMapper.toDto(updated);

        return ResponseEntity.ok(new ResponseObject("ok", "Updated successfully", updatedDTO));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteAddress(@PathVariable int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomerDTO currentCustomer = (CustomerDTO) authentication.getPrincipal();

        if (currentCustomer == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseObject("failed", "Unauthorized", null));
        }

        Address existing = addressService.getAddressById(id);

        if (existing == null || existing.getCustomer().getId() != currentCustomer.getId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ResponseObject("failed", "You do not have permission to delete this address", null));
        }

        addressService.delete(id);
        return ResponseEntity.ok(new ResponseObject("ok", "Deleted successfully", ""));
    }

}
