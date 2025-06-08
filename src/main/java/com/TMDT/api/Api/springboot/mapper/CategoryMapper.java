package com.TMDT.api.Api.springboot.mapper;

import com.TMDT.api.Api.springboot.dto.CategoryDTO;
import com.TMDT.api.Api.springboot.models.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO toDto(Category category);

    List<CategoryDTO> toListDto(List<Category> categoryList);
}
