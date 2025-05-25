package com.TMDT.api.Api.springboot.dto;

import com.TMDT.api.Api.springboot.models.ProductPhoneCategory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

public class PhoneCategoryDTO {
    private int id;
    private String name;
    private int status;
    private List<ProductPhoneCategory> productPhoneCategories;

}
