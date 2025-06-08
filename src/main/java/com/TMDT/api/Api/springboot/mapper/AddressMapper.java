package com.TMDT.api.Api.springboot.mapper;

import com.TMDT.api.Api.springboot.dto.AddressDTO;
import com.TMDT.api.Api.springboot.models.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    AddressDTO toDto(Address address);

    List<AddressDTO> toListDto(List<Address> addresses);
}
