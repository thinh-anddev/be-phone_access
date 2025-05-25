package com.TMDT.api.Api.springboot.mapper;

import com.TMDT.api.Api.springboot.dto.CartDetailDTO;
import com.TMDT.api.Api.springboot.dto.ImageDTO;
import com.TMDT.api.Api.springboot.dto.ProductDTO;
import com.TMDT.api.Api.springboot.models.CartDetail;
import com.TMDT.api.Api.springboot.models.Image;
import com.TMDT.api.Api.springboot.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface CartDetailMapper {
    CartDetailMapper INSTANCE = Mappers.getMapper(CartDetailMapper.class);

    CartDetailDTO toDto(CartDetail cartDetail);

    List<CartDetailDTO> toDtoList(List<CartDetail> cartDetails);

//    ProductDTO toDtoProduct(Product product);
}
