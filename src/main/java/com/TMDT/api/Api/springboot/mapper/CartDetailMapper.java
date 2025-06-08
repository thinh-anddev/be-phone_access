package com.TMDT.api.Api.springboot.mapper;

import com.TMDT.api.Api.springboot.dto.CartDetailDTO;
import com.TMDT.api.Api.springboot.models.CartDetail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class, PhoneCategoryMapper.class})
public interface CartDetailMapper {

    CartDetailDTO toDto(CartDetail cartDetail);

    CartDetail toEntity(CartDetailDTO cartDetailDTO);

    List<CartDetailDTO> toDtoList(List<CartDetail> cartDetails);
}
