package com.TMDT.api.Api.springboot.mapper;

import com.TMDT.api.Api.springboot.dto.OrderDetailDTO;
import com.TMDT.api.Api.springboot.models.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AddressMapper.class, PhoneCategoryMapper.class})
public interface OrderDetailMapper {
    OrderDetailMapper INSTANCE = Mappers.getMapper(OrderDetailMapper.class);

    @Mapping(target = "order", ignore = true)
    OrderDetailDTO toDto(OrderDetail orderDetail);

    OrderDetail toEntity(OrderDetailDTO orderDetailDTO);

    List<OrderDetailDTO> toListDto(List<OrderDetail> orderDetails);

    List<OrderDetail> toListEntity(List<OrderDetailDTO> orderDetailDTOList);
}
