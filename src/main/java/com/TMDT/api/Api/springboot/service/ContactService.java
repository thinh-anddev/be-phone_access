package com.TMDT.api.Api.springboot.service;

import com.TMDT.api.Api.springboot.dto.ContactDTO;
import com.TMDT.api.Api.springboot.mapper.ContactMapper;
import com.TMDT.api.Api.springboot.models.Contact;
import com.TMDT.api.Api.springboot.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ContactMapper contactMapper;

    public ContactDTO saveContact(ContactDTO contactDTO) {
        Contact contact = contactMapper.toEntity(contactDTO);
        Contact savedContact = contactRepository.save(contact);
        return contactMapper.toDTO(savedContact);
    }

    public List<ContactDTO> getAllContacts(String sort) {
        Sort sortOrder = sort.equalsIgnoreCase("asc")
                ? Sort.by(Sort.Direction.ASC, "createdAt")
                : Sort.by(Sort.Direction.DESC, "createdAt");
        List<Contact> contacts = contactRepository.findAll(sortOrder);
        return contacts.stream()
                .map(contactMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteContact(Long id) {
        if (!contactRepository.existsById(id)) {
            throw new RuntimeException("Contact not found with id: " + id);
        }
        contactRepository.deleteById(id);
    }
}
