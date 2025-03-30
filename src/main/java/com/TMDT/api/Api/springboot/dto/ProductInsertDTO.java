package com.TMDT.api.Api.springboot.dto;

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
public class ProductInsertDTO {
    private int id;
    private String name;
    private String description;
    private double price;
    private double discount;
    private int quantity;
    private int sold;
    private LocalDateTime createAt;
    private List<String> images;
    private int categoryId;
    private List<Integer> phoneCategoryIds;
}
