package com.TMDT.api.Api.springboot.mapper;

import com.TMDT.api.Api.springboot.dto.ProductPhoneCategoryDTO;
import com.TMDT.api.Api.springboot.models.ProductPhoneCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PhoneCategoryMapper.class})
public interface ProductPhoneCategoryMapper {
    ProductPhoneCategoryMapper INSTANCE = Mappers.getMapper(ProductPhoneCategoryMapper.class);

    ProductPhoneCategoryDTO toDto(ProductPhoneCategory productPhoneCategory);

    ProductPhoneCategory toEntity(ProductPhoneCategoryDTO productPhoneCategoryDTO);

    List<ProductPhoneCategory> toListEntity(List<ProductPhoneCategoryDTO> productPhoneCategoryDTOList);

    List<ProductPhoneCategoryDTO> toListDto(List<ProductPhoneCategory> productPhoneCategoryList);

}
