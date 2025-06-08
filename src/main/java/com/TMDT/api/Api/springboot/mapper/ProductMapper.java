package com.TMDT.api.Api.springboot.mapper;

import com.TMDT.api.Api.springboot.dto.CategoryDTO;
import com.TMDT.api.Api.springboot.dto.ImageDTO;
import com.TMDT.api.Api.springboot.dto.ProductDTO;
import com.TMDT.api.Api.springboot.models.Category;
import com.TMDT.api.Api.springboot.models.Image;
import com.TMDT.api.Api.springboot.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductPhoneCategoryMapper.class})
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO toDTO(Product product);

    List<ProductDTO> toListDTO(List<Product> products);

    Product toProduct(ProductDTO product);

    ImageDTO toImageDTO(Image image);

    CategoryDTO toCategoryDTO(Category category);
}
