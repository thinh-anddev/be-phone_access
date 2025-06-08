package com.TMDT.api.Api.springboot.dto;

import com.TMDT.api.Api.springboot.models.Order;
import com.TMDT.api.Api.springboot.models.PhoneCategory;
import com.TMDT.api.Api.springboot.models.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private int id;
    private ProductDTO product;
    private PhoneCategoryDTO phoneCategory;
    private OrderDTO order;
    private int quantity;
    private int price;
    private int status;
}
