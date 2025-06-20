package com.TMDT.api.Api.springboot.repositories;


import com.TMDT.api.Api.springboot.models.Contact;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByOrderByCreatedAtDesc();
    List<Contact> findAll(Sort sort);
}
