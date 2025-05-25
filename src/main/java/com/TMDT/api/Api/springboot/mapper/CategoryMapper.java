package com.TMDT.api.Api.springboot.mapper;

import com.TMDT.api.Api.springboot.dto.CategoryDTO;
import com.TMDT.api.Api.springboot.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
    CategoryDTO toDto(Category category);
}
