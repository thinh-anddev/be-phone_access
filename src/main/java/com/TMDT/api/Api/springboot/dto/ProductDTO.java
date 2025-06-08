package com.TMDT.api.Api.springboot.dto;

import com.TMDT.api.Api.springboot.models.Category;
import com.TMDT.api.Api.springboot.models.Image;
import com.TMDT.api.Api.springboot.models.ProductPhoneCategory;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDTO {
    private int id;
    private String name;
    private String description;
    private double price;
    private double discount;
    private int quantity;
    private int sold;
    private LocalDateTime createAt;
    private List<ImageDTO> images;
    private Category category;
    private int status;
    private List<ProductPhoneCategoryDTO> productPhoneCategories;
}
