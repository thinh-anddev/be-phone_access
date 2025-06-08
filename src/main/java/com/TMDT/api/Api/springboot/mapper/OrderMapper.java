package com.TMDT.api.Api.springboot.mapper;

import com.TMDT.api.Api.springboot.dto.OrderDTO;
import com.TMDT.api.Api.springboot.models.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class, OrderDetailMapper.class, AddressMapper.class})
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO toDto(Order order);

    Order toEntity(OrderDTO orderDTO);

    List<OrderDTO> toListDto(List<Order> orders);
}
