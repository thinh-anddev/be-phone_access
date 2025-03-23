package com.TMDT.api.Api.springboot.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPhoneCategoryId implements Serializable {
    private int productId;
    private int phoneCategoryId;
}
