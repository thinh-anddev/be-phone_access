package com.TMDT.api.Api.springboot.mapper;

import com.TMDT.api.Api.springboot.dto.CategoryDTO;
import com.TMDT.api.Api.springboot.dto.ImageDTO;
import com.TMDT.api.Api.springboot.dto.ProductDTO;
import com.TMDT.api.Api.springboot.models.Category;
import com.TMDT.api.Api.springboot.models.Image;
import com.TMDT.api.Api.springboot.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    public ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO toDTO(Product product);

    Product toProduct(ProductDTO product);

    ImageDTO toImageDTO(Image image);

    CategoryDTO toCategoryDTO(Category category);
}
