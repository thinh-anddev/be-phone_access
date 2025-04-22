package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.models.Address;
import com.TMDT.api.Api.springboot.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/api/v1/address")
public class AddressControllers {
    @Autowired
    AddressService addressService;

    @GetMapping("/getAddressByCustomerId/{customerId}")
    public ResponseEntity<ResponseObject> getAddressByCustomerId(@PathVariable int customerId) {
        return ResponseEntity.ok(new ResponseObject("ok", "Success", addressService.getAddressByCustomerId(customerId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getAddressById(@PathVariable int id) {
        return ResponseEntity.ok(new ResponseObject("ok", "Success", addressService.getAddressById(id)));
    }

    @PostMapping("/insert")
    public ResponseEntity<ResponseObject> insertAddress(@RequestBody Address newAddress) {
        return ResponseEntity.ok(new ResponseObject("ok", "Success", addressService.add(newAddress)));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseObject> updateAddress(@RequestBody Address address) {
        return ResponseEntity.ok(new ResponseObject("ok", "Success", addressService.update(address)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteAddress(@PathVariable int id) {
        addressService.delete(id);
        return ResponseEntity.ok(new ResponseObject("ok", "Success", ""));
    }
}
