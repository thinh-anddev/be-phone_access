package com.TMDT.api.Api.springboot.mapper;

import com.TMDT.api.Api.springboot.dto.ContactDTO;
import com.TMDT.api.Api.springboot.models.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    Contact toEntity(ContactDTO dto);

    ContactDTO toDTO(Contact entity);
}