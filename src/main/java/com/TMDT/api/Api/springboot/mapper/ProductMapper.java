package com.TMDT.api.Api.springboot.mapper;

import com.TMDT.api.Api.springboot.dto.ProductDTO;
import com.TMDT.api.Api.springboot.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO productToProductDTO(Product productDTO);

    Product productDTOtoProduct(ProductDTO product);
}
