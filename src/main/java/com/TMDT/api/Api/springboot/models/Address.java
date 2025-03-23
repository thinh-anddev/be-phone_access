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
@ToString
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto generate id
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id",
            foreignKey = @ForeignKey(name = "fk_address_customer"))
    @JsonBackReference(value = "customer-address")
    private Customer customer;
    @Column(name = "province_id")
    private int provinceId;
    @Column(name = "province_value")
    private String provinceValue;
    @Column(name = "district_id")
    private int districtId;
    @Column(name = "district_value")
    private String districtValue;
    @Column(name = "ward_id")
    private int wardId;
    @Column(name = "ward_value")
    private String wardValue;
    @Column(name = "sub_address")
    private String subAddress;
    private int status;

}
