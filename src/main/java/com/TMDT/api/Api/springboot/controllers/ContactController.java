package com.TMDT.api.Api.springboot.controllers;

import com.TMDT.api.Api.springboot.dto.ContactDTO;
import com.TMDT.api.Api.springboot.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @GetMapping("/getAll")
    public ResponseEntity<ResponseObject> getAllContacts(@RequestParam(defaultValue = "desc") String sort) {
        List<ContactDTO> contacts = contactService.getAllContacts(sort);
        return ResponseEntity.ok(new ResponseObject("ok", "Query contacts successfully", contacts));
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createContact(@RequestBody ContactDTO contactDTO) {
        ContactDTO savedContact = contactService.saveContact(contactDTO);
        return ResponseEntity.ok(new ResponseObject("ok", "Add contact successfully", savedContact));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
        return ResponseEntity.ok(new ResponseObject("ok", "Delete contact successfully", null));
    }
}
