package com.TMDT.api.Api.springboot.dto;

import com.TMDT.api.Api.springboot.models.ProductPhoneCategoryId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductPhoneCategoryDTO {
    private ProductPhoneCategoryId id;
    private PhoneCategoryDTO phoneCategory;
}
