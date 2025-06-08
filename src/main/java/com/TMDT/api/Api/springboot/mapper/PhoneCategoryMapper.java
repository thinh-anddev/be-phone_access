package com.TMDT.api.Api.springboot.mapper;

import com.TMDT.api.Api.springboot.dto.PhoneCategoryDTO;
import com.TMDT.api.Api.springboot.models.PhoneCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PhoneCategoryMapper {
    PhoneCategoryMapper INSTANCE = Mappers.getMapper(PhoneCategoryMapper.class);

    PhoneCategoryDTO toDto(PhoneCategory phoneCategory);

    PhoneCategory toEntity(PhoneCategoryDTO phoneCategoryDTO);

    List<PhoneCategoryDTO> toListDto(List<PhoneCategory> phoneCategoryList);

}
