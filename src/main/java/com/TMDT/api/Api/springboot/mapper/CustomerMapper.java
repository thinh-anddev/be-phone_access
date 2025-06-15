package com.TMDT.api.Api.springboot.mapper;

import com.TMDT.api.Api.springboot.dto.*;
import com.TMDT.api.Api.springboot.models.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "cartDetails", ignore = true)
    CustomerDTO toDto(Customer customer);

    Customer toEntity(CustomerDTO customerDTO);
    List<CustomerDTO> toListDto(List<Customer> customers);
    List<Customer> toListEntity(List<CustomerDTO> dtos);
}