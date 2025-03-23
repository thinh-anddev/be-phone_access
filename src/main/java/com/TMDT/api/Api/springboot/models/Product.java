package com.TMDT.api.Api.springboot.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto generate id
    private int id;
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    private double price;
    private double discount;
    private int quantity;
    private int sold;
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @JsonManagedReference(value = "product-image")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Image> images;
    @ManyToOne
    @JoinColumn(name = "category_id",
            foreignKey = @ForeignKey(name = "fk_product_category"))
    private Category category;
    private int status;

    @OneToMany(mappedBy = "product")
    private List<ProductPhoneCategory> productPhoneCategories;
}
