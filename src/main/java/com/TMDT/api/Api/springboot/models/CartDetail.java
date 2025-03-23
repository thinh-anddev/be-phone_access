package com.TMDT.api.Api.springboot.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cart_details")
public class CartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto generate id
    private int id;
    @JoinColumn(name = "product_id",
            foreignKey = @ForeignKey(name = "fk_cart_detail_product"))
    @ManyToOne
    private Product product;
    @ManyToOne
    private PhoneCategory phoneCategory;

    @JoinColumn(name = "customer_id",
            foreignKey = @ForeignKey(name = "fk_cart_detail_customer"))
    @ManyToOne
    @JsonBackReference
    private Customer customer;
    private int quantity;
    private int status;
}
