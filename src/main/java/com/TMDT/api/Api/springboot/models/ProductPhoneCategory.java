package com.TMDT.api.Api.springboot.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product_phone_category")
public class ProductPhoneCategory {

    @EmbeddedId
    @JsonIgnore
    private ProductPhoneCategoryId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Product product;

    @ManyToOne
    @MapsId("phoneCategoryId")
    @JoinColumn(name = "phone_category_id")
    private PhoneCategory phoneCategory;
}

