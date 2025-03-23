package com.TMDT.api.Api.springboot.dto;

import com.TMDT.api.Api.springboot.models.Category;
import com.TMDT.api.Api.springboot.models.Image;
import com.TMDT.api.Api.springboot.models.ProductPhoneCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private int id;
    private String name;
    private String description;
    private double price;
    private double discount;
    private int quantity;
    private int sold;
    private LocalDateTime createAt;
    private List<Image> images;
    private Category category;
    private int status;
    private List<ProductPhoneCategory> productPhoneCategories;
}
