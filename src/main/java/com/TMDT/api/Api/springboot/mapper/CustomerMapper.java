package com.TMDT.api.Api.springboot.mapper;

import com.TMDT.api.Api.springboot.dto.*;
import com.TMDT.api.Api.springboot.models.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
    CustomerDTO toDto(Customer customer);

    AddressDTO toAddressDto(Address address);

    OrderDTO toOrderDto(Order order);

    CartDetailDTO toCartDetailDto(CartDetail cartDetail);
}
