package com.TMDT.api.Api.springboot.dto;

import com.TMDT.api.Api.springboot.models.PhoneCategory;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartDetailDTO {
    private Integer id;
    private int productId;
    private int phoneCategoryId;
    private int customerId;
    private int quantity;
    private ProductDTO product;
    private PhoneCategoryDTO phoneCategory;
}

